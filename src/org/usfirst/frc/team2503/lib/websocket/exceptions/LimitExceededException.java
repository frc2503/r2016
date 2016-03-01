package org.usfirst.frc.team2503.lib.websocket.exceptions;

import org.usfirst.frc.team2503.lib.websocket.framing.CloseFrame;

public class LimitExceededException extends InvalidDataException {

	private static final long serialVersionUID = 6908339749836826785L;

	public LimitExceededException() {
		super( CloseFrame.TOOBIG );
	}

	public LimitExceededException( String s ) {
		super( CloseFrame.TOOBIG, s );
	}

}
