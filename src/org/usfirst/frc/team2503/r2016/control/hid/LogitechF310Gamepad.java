package org.usfirst.frc.team2503.r2016.control.hid;

public class LogitechF310Gamepad extends Joystick {
	
	// These are interpreted names for some of the buttons.
	public final Button aButton = this.button_1;
	public final Button bButton = this.button_2;
	public final Button xButton = this.button_3;
	public final Button yButton = this.button_4;
	public final Button leftBumperButton = this.button_5;
	public final Button rightBumperButton = this.button_6;
	public final Button backButton = this.button_7;
	public final Button startButton = this.button_8;
	public final Button leftStickButton = this.button_9;
	public final Button rightStickButton = this.button_10;

	// These are the purposes of some of the buttons.
	public final Button intakeIntakeModeButton = this.aButton;
	public final Button intakeOutputModeButton = this.bButton;
	public final Button intakeFireModeButton = this.leftBumperButton;

	// These are the named POV's.
	public final POV POV = this.pov_0;
	
	// These are the interpreted names for some of the axes.
	public final Axis leftXAxis = this.axis_0;
	public final Axis leftYAxis = this.axis_1;
	public final Axis leftTriggerAxis = this.axis_2;
	public final Axis rightTriggerAxis = this.axis_3;
	public final Axis rightXAxis = this.axis_4;
	public final Axis rightYAxis = this.axis_5;
	
	public final Axis winchAxis = this.rightYAxis;
	public final Axis hookerAxis = this.leftYAxis;
	public final Axis shooterAxis = this.rightTriggerAxis;
	
	{
		this.leftXAxis.setAxisType(AxisType.NEGATIVE_TO_POSITIVE);
		this.leftYAxis.setAxisType(AxisType.NEGATIVE_TO_POSITIVE);
		this.leftTriggerAxis.setAxisType(AxisType.ZERO_TO_POSITIVE);
		this.rightTriggerAxis.setAxisType(AxisType.ZERO_TO_POSITIVE);
		this.rightXAxis.setAxisType(AxisType.NEGATIVE_TO_POSITIVE);
		this.rightYAxis.setAxisType(AxisType.NEGATIVE_TO_POSITIVE);
	}
	
	public LogitechF310Gamepad(int port, int numAxisTypes, int numButtonTypes) {
		super(port, numAxisTypes, numButtonTypes);
	}

	public LogitechF310Gamepad(int port) {
		super(port);
	}
	
}
