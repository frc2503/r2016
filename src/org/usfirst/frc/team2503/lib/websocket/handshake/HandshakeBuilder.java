package org.usfirst.frc.team2503.lib.websocket.handshake;

public interface HandshakeBuilder extends HandshakeData {
	public abstract void setContent( byte[] content );
	public abstract void put( String name, String value );
}
