package org.usfirst.frc.team2503.r2016.input.gamepad;

import org.usfirst.frc.team2503.r2016.input.joystick.Joystick;
import org.usfirst.frc.team2503.r2016.input.joystick.Joystick.Axis;

public class Gamepad extends Joystick {
	
	public Axis leftX;
	public Axis leftY;
	public Axis leftTrigger;
	
	public Axis rightX;
	public Axis rightY;
	public Axis rightTrigger;
	
	public Gamepad(int port) {
		super(port);
	}

}
