package org.usfirst.frc.team2503.websocket.handshake;

public class HandshakeImpl1Server extends HandshakeDataImpl1 implements ServerHandshakeBuilder {
	private short httpstatus;
	private String httpstatusmessage;

	public HandshakeImpl1Server() {
	}

	@Override
	public String getHttpStatusMessage() {
		return httpstatusmessage;
	}

	@Override
	public short getHttpStatus() {
		return httpstatus;
	}

	public void setHttpStatusMessage( String message ) {
		this.httpstatusmessage = message;
	}

	public void setHttpStatus( short status ) {
		httpstatus = status;
	}


}
