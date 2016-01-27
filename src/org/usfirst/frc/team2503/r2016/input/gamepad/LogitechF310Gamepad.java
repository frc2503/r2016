package org.usfirst.frc.team2503.r2016.input.gamepad;

public class LogitechF310Gamepad extends Gamepad {
	
	public Axis leftX = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 0);
	public Axis leftY = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 1);
	public Axis leftTrigger = new Axis(AxisType.ZERO_TO_POSITIVE, 2);
	
	public Axis rightTrigger = new Axis(AxisType.ZERO_TO_POSITIVE, 3);
	public Axis rightX = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 4);
	public Axis rightY = new Axis(AxisType.NEGATIVE_TO_POSITIVE, 5);
	
	public boolean getAButton() { return this.getRawButton(1); }
	public boolean getBButton() { return this.getRawButton(2); }
	public boolean getXButton() { return this.getRawButton(3); }
	public boolean getYButton() { return this.getRawButton(4); }
	public boolean getBackButton() { return this.getRawButton(7); }
	public boolean getStartButton() { return this.getRawButton(8); }
	public boolean getLeftBumperButton() { return this.getRawButton(5); }
	public boolean getLeftStickButton() { return this.getRawButton(9); }
	public boolean getRightBumperButton() { return this.getRawButton(6); }
	public boolean getRightStickButton() { return this.getRawButton(10); }

	public boolean isAButtonPressed() { return this.getAButton(); }
	public boolean isAButtonUnpressed() { return !this.isAButtonPressed(); }
	public boolean isBButtonPressed() { return this.getBButton(); }
	public boolean isBButtonUnpressed() { return !this.isBButtonPressed(); }
	public boolean isXButtonPressed() { return this.getXButton(); }
	public boolean isXButtonUnpressed() { return !this.isXButtonPressed(); }
	public boolean isYButtonPressed() { return this.getYButton(); }
	public boolean isYButtonUnpressed() { return !this.isYButtonPressed(); }
	public boolean isBackButtonPressed() { return this.getBackButton(); }
	public boolean isBackButtonUnpressed() { return !this.isBackButtonPressed(); }
	public boolean isStartButtonPressed() { return this.getStartButton(); }
	public boolean isStartButtonUnpressed() { return !this.isStartButtonPressed(); }
	public boolean isLeftBumperButtonPressed() { return this.getLeftBumperButton(); }
	public boolean isLeftBumperButtonUnpressed() { return !this.isLeftBumperButtonPressed(); }
	public boolean isLeftStickButtonPressed() { return this.getLeftStickButton(); }
	public boolean isLeftStickButtonUnpressed() { return !this.isLeftStickButtonPressed(); }
	public boolean isRightBumperButtonPressed() { return this.getRightBumperButton(); }
	public boolean isRightBumperButtonUnpressed() { return !this.isRightBumperButtonPressed(); }
	public boolean isRightStickButtonPressed() { return this.getRightStickButton(); }
	public boolean isRightStickButtonUnpressed() { return !this.isRightStickButtonPressed(); }

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
