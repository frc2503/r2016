package org.usfirst.frc.team2503.lib.websocket.drafts;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidDataException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidFrameException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidHandshakeException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.LimitExceededException;
import org.usfirst.frc.team2503.lib.websocket.exceptions.NotSendableException;
import org.usfirst.frc.team2503.lib.websocket.framing.CloseFrame;
import org.usfirst.frc.team2503.lib.websocket.framing.FrameBuilder;
import org.usfirst.frc.team2503.lib.websocket.framing.FrameData;
import org.usfirst.frc.team2503.lib.websocket.framing.FramedataImpl1;
import org.usfirst.frc.team2503.lib.websocket.framing.FrameData.Opcode;
import org.usfirst.frc.team2503.lib.websocket.handshake.ClientHandshake;
import org.usfirst.frc.team2503.lib.websocket.handshake.ClientHandshakeBuilder;
import org.usfirst.frc.team2503.lib.websocket.handshake.HandshakeBuilder;
import org.usfirst.frc.team2503.lib.websocket.handshake.ServerHandshake;
import org.usfirst.frc.team2503.lib.websocket.handshake.ServerHandshakeBuilder;
import org.usfirst.frc.team2503.lib.websocket.util.Charsetfunctions;

public class Draft_75 extends Draft {

	public static final byte CR = (byte) 0x0D;
	public static final byte LF = (byte) 0x0A;
	public static final byte START_OF_FRAME = (byte) 0x00;
	public static final byte END_OF_FRAME = (byte) 0xFF;

	protected boolean readingState = false;

	protected List<FrameData> readyframes = new LinkedList<FrameData>();
	protected ByteBuffer currentFrame;

	private final Random reuseableRandom = new Random();

	@Override
	public HandshakeState acceptHandshakeAsClient(ClientHandshake request, ServerHandshake response) {
		return request.getFieldValue("WebSocket-Origin").equals(response.getFieldValue("Origin")) && basicAccept(response) ? HandshakeState.MATCHED : HandshakeState.NOT_MATCHED;
	}

	@Override
	public HandshakeState acceptHandshakeAsServer(ClientHandshake handshakedata) {
		if(handshakedata.hasFieldValue("Origin") && basicAccept(handshakedata)) {
			return HandshakeState.MATCHED;
		}
		return HandshakeState.NOT_MATCHED;
	}

	@Override
	public ByteBuffer createBinaryFrame(FrameData framedata) {
		if(framedata.getOpcode() != Opcode.TEXT) {
			throw new RuntimeException("only text frames supported");
		}

		ByteBuffer pay = framedata.getPayloadData();
		ByteBuffer b = ByteBuffer.allocate(pay.remaining() + 2);
		b.put(START_OF_FRAME);
		pay.mark();
		b.put(pay);
		pay.reset();
		b.put(END_OF_FRAME);
		b.flip();
		return b;
	}

	@Override
	public List<FrameData> createFrames(ByteBuffer binary, boolean mask) {
		throw new RuntimeException("not yet implemented");
	}

	@Override
	public List<FrameData> createFrames(String text, boolean mask) {
		FrameBuilder frame = new FramedataImpl1();
		try {
			frame.setPayload(ByteBuffer.wrap(Charsetfunctions.utf8Bytes(text)));
		} catch (InvalidDataException e) {
			throw new NotSendableException(e);
		}
		frame.setFin(true);
		frame.setOptcode(Opcode.TEXT);
		frame.setTransferemasked(mask);
		return Collections.singletonList((FrameData) frame);
	}

	@Override
	public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder request) throws InvalidHandshakeException {
		request.put("Upgrade", "WebSocket");
		request.put("Connection", "Upgrade");
		if(!request.hasFieldValue("Origin")) {
			request.put("Origin", "random" + reuseableRandom.nextInt());
		}

		return request;
	}

	@Override
	public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake request, ServerHandshakeBuilder response) throws InvalidHandshakeException {
		response.setHttpStatusMessage("Web Socket Protocol Handshake");
		response.put("Upgrade", "WebSocket");
		response.put("Connection", request.getFieldValue("Connection")); // to respond to a Connection keep alive
		response.put("WebSocket-Origin", request.getFieldValue("Origin"));
		String location = "ws://" + request.getFieldValue("Host") + request.getResourceDescriptor();
		response.put("WebSocket-Location", location);
		// TODO handle Sec-WebSocket-Protocol and Set-Cookie
		return response;
	}

	protected List<FrameData> translateRegularFrame(ByteBuffer buffer) throws InvalidDataException {

		while (buffer.hasRemaining()) {
			byte newestByte = buffer.get();
			if(newestByte == START_OF_FRAME) { // Beginning of Frame
				if(readingState)
					throw new InvalidFrameException("unexpected START_OF_FRAME");
				readingState = true;
			} else if(newestByte == END_OF_FRAME) { // End of Frame
				if(!readingState)
					throw new InvalidFrameException("unexpected END_OF_FRAME");
				if(this.currentFrame != null) {
					currentFrame.flip();
					FramedataImpl1 curframe = new FramedataImpl1();
					curframe.setPayload(currentFrame);
					curframe.setFin(true);
					curframe.setOptcode(Opcode.TEXT);
					readyframes.add(curframe);
					this.currentFrame = null;
					buffer.mark();
				}
				readingState = false;
			} else if(readingState) { // Regular frame data, add to current frame buffer //TODO This code is very expensive and slow
				if(currentFrame == null) {
					currentFrame = createBuffer();
				} else if(!currentFrame.hasRemaining()) {
					currentFrame = increaseBuffer(currentFrame);
				}
				currentFrame.put(newestByte);
			} else {
				return null;
			}
		}

		List<FrameData> frames = readyframes;
		readyframes = new LinkedList<FrameData>();
		return frames;
	}

	@Override
	public List<FrameData> translateFrame(ByteBuffer buffer) throws InvalidDataException {
		List<FrameData> frames = translateRegularFrame(buffer);
		if(frames == null) {
			throw new InvalidDataException(CloseFrame.PROTOCOL_ERROR);
		}
		return frames;
	}

	@Override
	public void reset() {
		readingState = false;
		this.currentFrame = null;
	}

	@Override
	public CloseHandshakeType getCloseHandshakeType() {
		return CloseHandshakeType.NONE;
	}

	public ByteBuffer createBuffer() {
		return ByteBuffer.allocate(INITIAL_FAMESIZE);
	}

	public ByteBuffer increaseBuffer(ByteBuffer full) throws LimitExceededException, InvalidDataException {
		full.flip();
		ByteBuffer newbuffer = ByteBuffer.allocate(checkAlloc(full.capacity() * 2));
		newbuffer.put(full);
		return newbuffer;
	}

	@Override
	public Draft copyInstance() {
		return new Draft_75();
	}
}
