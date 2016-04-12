package org.usfirst.frc.team2503.lib.websocket.handshake;

public class ServerHandshakeImplementation extends HandshakeDataImplementation implements ServerHandshakeBuilder {
	private short httpstatus;
	private String httpstatusmessage;

	public ServerHandshakeImplementation() {
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
