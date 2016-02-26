package org.usfirst.frc.team2503.lib.websocket.handshake;

public class HandshakeImplClient extends HandshakeDataImpl implements ClientHandshakeBuilder {
	private String resourceDescriptor = "*";

	public HandshakeImplClient() {
	}

	public void setResourceDescriptor( String resourceDescriptor ) throws IllegalArgumentException {
		if(resourceDescriptor==null)
			throw new IllegalArgumentException( "http resource descriptor must not be null" );
		this.resourceDescriptor = resourceDescriptor;
	}

	public String getResourceDescriptor() {
		return resourceDescriptor;
	}
}
