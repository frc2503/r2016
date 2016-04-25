package org.usfirst.frc.team2503.r2016.control.hid;

public class MadCatzV1Joystick extends Joystick {

	// These are interpreted names for some of the buttons.
	public final Button triggerButton = this.button_1;
	public final Button gripButton = this.button_7;

	// These are the purposes of some of the buttons.
	public final Button liftRaiseModeButton = this.button_2;
	public final Button liftLowerModeButton = this.triggerButton;

	// These are the named POV's.
	public final POV POV = this.pov_0;
	
	// These are the interpreted names for some of the axes.
	public final Axis xAxis = this.axis_0;
	public final Axis yAxis = this.axis_1;
	public final Axis throttleAxis = this.axis_2;
	public final Axis rotationAxis = this.axis_3;
	
	{
		this.xAxis.setAxisType(AxisType.NEGATIVE_TO_POSITIVE);
		this.yAxis.setAxisType(AxisType.NEGATIVE_TO_POSITIVE);
		this.throttleAxis.setAxisType(AxisType.ZERO_TO_POSITIVE);
		this.rotationAxis.setAxisType(AxisType.NEGATIVE_TO_POSITIVE);
	}
	
	public MadCatzV1Joystick(int port, int numAxisTypes, int numButtonTypes) {
		super(port, numAxisTypes, numButtonTypes);
	}

	public MadCatzV1Joystick(int port) {
		super(port);
	}

}
