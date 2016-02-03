package org.usfirst.frc.team2503.websocket;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public class SSLSocketChannel implements ByteChannel, WrappedByteChannel {
	protected static ByteBuffer emptybuffer = ByteBuffer.allocate(0);

	protected ExecutorService exec;

	protected List<Future<?>> tasks;

	protected ByteBuffer inData;
	protected ByteBuffer outCrypt;
	protected ByteBuffer inCrypt;

	protected SocketChannel socketChannel;
	protected SelectionKey selectionKey;

	protected SSLEngine sslEngine;
	protected SSLEngineResult readEngineResult;
	protected SSLEngineResult writeEngineResult;

	protected int bufferallocations = 0;

	public SSLSocketChannel(SocketChannel channel, SSLEngine sslEngine, ExecutorService exec, SelectionKey key) throws IOException {
		if(channel == null || sslEngine == null || exec == null)
			throw new IllegalArgumentException("parameter must not be null");

		this.socketChannel = channel;
		this.sslEngine = sslEngine;
		this.exec = exec;

		readEngineResult = writeEngineResult = new SSLEngineResult(Status.BUFFER_UNDERFLOW, sslEngine.getHandshakeStatus(), 0, 0); // init to prevent NPEs

		tasks = new ArrayList<Future<?>>(3);
		if(key != null) {
			key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
			this.selectionKey = key;
		}
		createBuffers(sslEngine.getSession());
		socketChannel.write(wrap(emptybuffer));
		processHandshake();
	}

	private void consumeFutureUninterruptible(Future<?> f) {
		try {
			boolean interrupted = false;
			while (true) {
				try {
					f.get();
					break;
				} catch (InterruptedException e) {
					interrupted = true;
				}
			}
			if(interrupted)
				Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized void processHandshake() throws IOException {
		if(sslEngine.getHandshakeStatus() == HandshakeStatus.NOT_HANDSHAKING)
			return;
		if(!tasks.isEmpty()) {
			Iterator<Future<?>> it = tasks.iterator();
			while (it.hasNext()) {
				Future<?> f = it.next();
				if(f.isDone()) {
					it.remove();
				} else {
					if(isBlocking())
						consumeFutureUninterruptible(f);
					return;
				}
			}
		}

		if(sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
			if(!isBlocking() || readEngineResult.getStatus() == Status.BUFFER_UNDERFLOW) {
				inCrypt.compact();
				int read = socketChannel.read(inCrypt);
				if(read == -1) {
					throw new IOException("connection closed unexpectedly by peer");
				}
				inCrypt.flip();
			}
			inData.compact();
			unwrap();
			if(readEngineResult.getHandshakeStatus() == HandshakeStatus.FINISHED) {
				createBuffers(sslEngine.getSession());
				return;
			}
		}
		consumeDelegatedTasks();
		if(tasks.isEmpty() || sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
			socketChannel.write(wrap(emptybuffer));
			if(writeEngineResult.getHandshakeStatus() == HandshakeStatus.FINISHED) {
				createBuffers(sslEngine.getSession());
				return;
			}
		}
		assert (sslEngine.getHandshakeStatus() != HandshakeStatus.NOT_HANDSHAKING);

		bufferallocations = 1;
	}
	private synchronized ByteBuffer wrap(ByteBuffer b) throws SSLException {
		outCrypt.compact();
		writeEngineResult = sslEngine.wrap(b, outCrypt);
		outCrypt.flip();
		return outCrypt;
	}

	private synchronized ByteBuffer unwrap() throws SSLException {
		int rem;
		do {
			rem = inData.remaining();
			readEngineResult = sslEngine.unwrap(inCrypt, inData);
		} while (readEngineResult.getStatus() == SSLEngineResult.Status.OK && (rem != inData.remaining() || sslEngine.getHandshakeStatus() == HandshakeStatus.NEED_UNWRAP));
		inData.flip();
		return inData;
	}

	protected void consumeDelegatedTasks() {
		Runnable task;
		while ((task = sslEngine.getDelegatedTask()) != null) {
			tasks.add(exec.submit(task));
			// task.run();
		}
	}

	protected void createBuffers(SSLSession session) {
		int netBufferMax = session.getPacketBufferSize();
		int appBufferMax = Math.max(session.getApplicationBufferSize(), netBufferMax);

		if(inData == null) {
			inData = ByteBuffer.allocate(appBufferMax);
			outCrypt = ByteBuffer.allocate(netBufferMax);
			inCrypt = ByteBuffer.allocate(netBufferMax);
		} else {
			if(inData.capacity() != appBufferMax)
				inData = ByteBuffer.allocate(appBufferMax);
			if(outCrypt.capacity() != netBufferMax)
				outCrypt = ByteBuffer.allocate(netBufferMax);
			if(inCrypt.capacity() != netBufferMax)
				inCrypt = ByteBuffer.allocate(netBufferMax);
		}
		inData.rewind();
		inData.flip();
		inCrypt.rewind();
		inCrypt.flip();
		outCrypt.rewind();
		outCrypt.flip();
		bufferallocations++;
	}

	public int write(ByteBuffer src) throws IOException {
		if(!isHandShakeComplete()) {
			processHandshake();
			return 0;
		}

		int num = socketChannel.write(wrap(src));
        if (writeEngineResult.getStatus() == SSLEngineResult.Status.CLOSED) {
            throw new EOFException("Connection is closed");
        }
		return num;

	}

	public int read(ByteBuffer dst) throws IOException {
		if(!dst.hasRemaining())
			return 0;
		if(!isHandShakeComplete()) {
			if(isBlocking()) {
				while (!isHandShakeComplete()) {
					processHandshake();
				}
			} else {
				processHandshake();
				if(!isHandShakeComplete()) {
					return 0;
				}
			}
		}
		
		int purged = readRemaining(dst);
		if(purged != 0)
			return purged;

		assert (inData.position() == 0);
		inData.clear();

		if(!inCrypt.hasRemaining())
			inCrypt.clear();
		else
			inCrypt.compact();

		if(isBlocking() || readEngineResult.getStatus() == Status.BUFFER_UNDERFLOW)
			if(socketChannel.read(inCrypt) == -1) {
				return -1;
			}
		inCrypt.flip();
		unwrap();

		int transfered = transfereTo(inData, dst);
		if(transfered == 0 && isBlocking()) {
			return read(dst);
		}
		return transfered;
	}

	private int readRemaining(ByteBuffer dst) throws SSLException {
		if(inData.hasRemaining()) {
			return transfereTo(inData, dst);
		}
		
		if(!inData.hasRemaining())
			inData.clear();
		
		if(inCrypt.hasRemaining()) {
			unwrap();
			int amount = transfereTo(inData, dst);
            if (readEngineResult.getStatus() == SSLEngineResult.Status.CLOSED) {
                return -1;
            }
			if(amount > 0)
				return amount;
		}
		return 0;
	}

	public boolean isConnected() {
		return socketChannel.isConnected();
	}

	public void close() throws IOException {
		sslEngine.closeOutbound();
		sslEngine.getSession().invalidate();
		if(socketChannel.isOpen())
			socketChannel.write(wrap(emptybuffer));// FIXME what if not all bytes can be written
		socketChannel.close();
		exec.shutdownNow();
	}

	private boolean isHandShakeComplete() {
		HandshakeStatus status = sslEngine.getHandshakeStatus();
		return status == SSLEngineResult.HandshakeStatus.FINISHED || status == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
	}

	public SelectableChannel configureBlocking(boolean b) throws IOException {
		return socketChannel.configureBlocking(b);
	}

	public boolean connect(SocketAddress remote) throws IOException {
		return socketChannel.connect(remote);
	}

	public boolean finishConnect() throws IOException {
		return socketChannel.finishConnect();
	}

	public Socket socket() {
		return socketChannel.socket();
	}

	public boolean isInboundDone() {
		return sslEngine.isInboundDone();
	}

	@Override
	public boolean isOpen() {
		return socketChannel.isOpen();
	}

	@Override
	public boolean isNeedWrite() {
		return outCrypt.hasRemaining() || !isHandShakeComplete(); // FIXME this condition can cause high cpu load during handshaking when network is slow
	}

	@Override
	public void writeMore() throws IOException {
		write(outCrypt);
	}

	@Override
	public boolean isNeedRead() {
		return inData.hasRemaining() || (inCrypt.hasRemaining() && readEngineResult.getStatus() != Status.BUFFER_UNDERFLOW && readEngineResult.getStatus() != Status.CLOSED);
	}

	@Override
	public int readMore(ByteBuffer dst) throws SSLException {
		return readRemaining(dst);
	}

	private int transfereTo(ByteBuffer from, ByteBuffer to) {
		int fremain = from.remaining();
		int toremain = to.remaining();
		if(fremain > toremain) {
			// FIXME there should be a more efficient transfer method
			int limit = Math.min(fremain, toremain);
			for(int i = 0 ; i < limit ; i++) {
				to.put(from.get());
			}
			return limit;
		} else {
			to.put(from);
			return fremain;
		}

	}

	@Override
	public boolean isBlocking() {
		return socketChannel.isBlocking();
	}

}