package org.usfirst.frc.team2503.lib.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

import org.usfirst.frc.team2503.lib.websocket.WebSocket.Role;

public class SocketChannelIOHelper {

	public static boolean read(final ByteBuffer buf, WebSocketImplementation ws, ByteChannel channel) throws IOException {
		buf.clear();
		int read = channel.read(buf);
		buf.flip();

		if(read == -1) {
			ws.eot();
			return false;
		}
		return read != 0;
	}

	public static boolean readMore(final ByteBuffer buf, WebSocketImplementation ws, WrappedByteChannel channel) throws IOException {
		buf.clear();
		int read = channel.readMore(buf);
		buf.flip();

		if(read == -1) {
			ws.eot();
			return false;
		}
		return channel.isNeedRead();
	}

	public static boolean batch(WebSocketImplementation ws, ByteChannel sockchannel) throws IOException {
		ByteBuffer buffer = ws.outQueue.peek();
		WrappedByteChannel c = null;

		if(buffer == null) {
			if(sockchannel instanceof WrappedByteChannel) {
				c = (WrappedByteChannel) sockchannel;
				if(c.isNeedWrite()) {
					c.writeMore();
				}
			}
		} else {
			do {
				sockchannel.write(buffer);
				if(buffer.remaining() > 0) {
					return false;
				} else {
					ws.outQueue.poll();
					buffer = ws.outQueue.peek();
				}
			} while (buffer != null);
		}

		if(ws != null && ws.outQueue.isEmpty() && ws.isFlushAndClose() && ws.getDraft() != null && ws.getDraft().getRole() != null && ws.getDraft().getRole() == Role.SERVER) {//
			synchronized (ws) {
				ws.closeConnection();
			}
		}
		return c != null ? !((WrappedByteChannel) sockchannel).isNeedWrite() : true;
	}
}
