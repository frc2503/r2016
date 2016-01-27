package org.usfirst.frc.team2503.r2016.input.gamepad;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class LogitechF310Gamepad extends Gamepad {
	
	public Axis leftX = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 0);
	public Axis leftY = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 1);
	public Axis leftTrigger = new Axis(AxisType.ZERO_TO_POSITIVE, 2);
	
	public Axis rightTrigger = new Axis(AxisType.ZERO_TO_POSITIVE, 3);
	public Axis rightX = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 4);
	public Axis rightY = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 5);
	
	public JoystickButton A = new JoystickButton(this, 1);
	public JoystickButton B = new JoystickButton(this, 2);
	public JoystickButton X = new JoystickButton(this, 3);
	public JoystickButton Y = new JoystickButton(this, 4);
	public JoystickButton Back = new JoystickButton(this, 7);
	public JoystickButton Start = new JoystickButton(this, 8);
	public JoystickButton LeftBumper = new JoystickButton(this, 5);
	public JoystickButton LeftStick = new JoystickButton(this, 9);
	public JoystickButton RightBumper = new JoystickButton(this, 6);
	public JoystickButton RightStick = new JoystickButton(this, 10);
	
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
	}
	
}
