package org.usfirst.frc.team2503.lib.websocket.framing;

import java.nio.ByteBuffer;

import org.usfirst.frc.team2503.lib.websocket.exceptions.InvalidDataException;

public interface FrameBuilder extends FrameData {

	public abstract void setFin( boolean fin );

	public abstract void setOptcode( Opcode optcode );

	public abstract void setPayload( ByteBuffer payload ) throws InvalidDataException;

	public abstract void setTransferemasked( boolean transferemasked );

}