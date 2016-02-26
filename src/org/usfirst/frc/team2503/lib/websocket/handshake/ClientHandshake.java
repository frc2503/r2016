package org.usfirst.frc.team2503.lib.websocket.handshake;

public interface ClientHandshake extends HandshakeData {
	/**returns the HTTP Request-URI as defined by http://tools.ietf.org/html/rfc2616#section-5.1.2*/
	public String getResourceDescriptor();
}
