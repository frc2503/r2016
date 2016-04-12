package org.usfirst.frc.team2503.lib.websocket.handshake;

public class ClientHandshakeImplementation extends HandshakeDataImplementation implements ClientHandshakeBuilder {
	private String resourceDescriptor = "*";

	public ClientHandshakeImplementation() {
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
