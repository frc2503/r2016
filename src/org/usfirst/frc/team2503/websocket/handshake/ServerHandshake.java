package org.usfirst.frc.team2503.websocket.handshake;

public interface ServerHandshake extends HandshakeData {
	public short getHttpStatus();
	public String getHttpStatusMessage();
}
