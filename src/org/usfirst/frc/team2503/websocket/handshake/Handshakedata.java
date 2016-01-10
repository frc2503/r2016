package org.usfirst.frc.team2503.websocket.handshake;

import java.util.Iterator;

public interface HandshakeData {
	public Iterator<String> iterateHttpFields();
	public String getFieldValue( String name );
	public boolean hasFieldValue( String name );
	public byte[] getContent();
}
