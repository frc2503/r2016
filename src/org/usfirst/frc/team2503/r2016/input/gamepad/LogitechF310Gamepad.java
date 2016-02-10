package org.usfirst.frc.team2503.r2016.input.gamepad;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class LogitechF310Gamepad extends Gamepad {
	
	public Axis leftX;
	public Axis leftY;
	public Axis leftTrigger;
	
	public Axis rightTrigger;
	public Axis rightX;
	public Axis rightY;
	
	public JoystickButton A;
	public JoystickButton B;
	public JoystickButton X;
	public JoystickButton Y;
	public JoystickButton Back;
	public JoystickButton Start;
	public JoystickButton LeftBumper;
	public JoystickButton LeftStick;
	public JoystickButton RightBumper;
	public JoystickButton RightStick;
	
	/**
	 * Get the value of the POV switch on this Gamepad.
	 *
	 * Note that getPOV (and hence this method) returns an integer value, where
	 * a value of 0 means that the POV is pointed NORTH, not EAST.
	 *
	 * If you're looking for a double value, use the below getPovDegrees() method.
	 *
	 * If you're looking for a double value, which could be passed to trigonometric
	 * functions (say Math.sin(double)), use the below getStandardPovDegrees()
	 * method.
	 */
	public int getPov() { return this.getPOV(0); }

	public double getPovDegrees() { return (double)this.getPov(); }
	public double getPovRadians() { return (Math.PI * this.getPovDegrees()) / 180.0d; }
	public double getStandardPovDegrees() { return 90.0d - this.getPovDegrees(); }
	public double getStandardPovRadians() { return (Math.PI * this.getStandardPovDegrees()) / 180.0d; }
	
	public LogitechF310Gamepad(int port) {
		super(port);
		
		leftX = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 0);
		leftY = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 1);
		leftTrigger = new Axis(AxisType.ZERO_TO_POSITIVE, 2);
		
		rightX = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 3);
		rightY = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 4);
		rightTrigger = new Axis(AxisType.ZERO_TO_POSITIVE, 5);
		
		A = new JoystickButton(this, 1);
		B = new JoystickButton(this, 2);
		X = new JoystickButton(this, 3);
		Y = new JoystickButton(this, 4);
		Back = new JoystickButton(this, 7);
		Start = new JoystickButton(this, 8);
		LeftBumper = new JoystickButton(this, 5);
		LeftStick = new JoystickButton(this, 9);
		RightBumper = new JoystickButton(this, 6);
		RightStick = new JoystickButton(this, 10);
	}
	
}
