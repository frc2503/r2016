package org.usfirst.frc.team2503.r2016.input.gamepad;

import org.usfirst.frc.team2503.r2016.input.joystick.Joystick;

public abstract class Gamepad extends Joystick {

	public ControllerAxis leftX;
	public ControllerAxis leftY;
	public ControllerAxis rightX;
	public ControllerAxis rightY;
	
	public ControllerPOV pov;
	
	public Gamepad(int port) {
		super(port);
	}
	
}
