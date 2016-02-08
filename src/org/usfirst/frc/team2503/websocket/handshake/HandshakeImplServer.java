package org.usfirst.frc.team2503.websocket.handshake;

public class HandshakeImplServer extends HandshakeDataImpl implements ServerHandshakeBuilder {
	private short httpstatus;
	private String httpstatusmessage;

	public HandshakeImplServer() {
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
