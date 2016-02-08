package org.usfirst.frc.team2503.websocket.exceptions;

import org.usfirst.frc.team2503.websocket.framing.CloseFrame;

public class InvalidFrameException extends InvalidDataException {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -9016496369828887591L;

	public InvalidFrameException() {
		super( CloseFrame.PROTOCOL_ERROR );
	}

	public InvalidFrameException( String arg0 ) {
		super( CloseFrame.PROTOCOL_ERROR, arg0 );
	}

	public InvalidFrameException( Throwable arg0 ) {
		super( CloseFrame.PROTOCOL_ERROR, arg0 );
	}

	public InvalidFrameException( String arg0 , Throwable arg1 ) {
		super( CloseFrame.PROTOCOL_ERROR, arg0, arg1 );
	}
}
