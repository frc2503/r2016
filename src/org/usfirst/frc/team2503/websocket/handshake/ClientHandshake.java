package org.usfirst.frc.team2503.websocket.handshake;

public interface ClientHandshake extends Handshakedata {
	/**returns the HTTP Request-URI as defined by http://tools.ietf.org/html/rfc2616#section-5.1.2*/
	public String getResourceDescriptor();
}
