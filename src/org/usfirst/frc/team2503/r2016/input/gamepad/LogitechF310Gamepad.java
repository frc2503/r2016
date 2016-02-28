package org.usfirst.frc.team2503.r2016.input.gamepad;

public class LogitechF310Gamepad extends Gamepad {

	public ControllerButton a = new ControllerButton(1);
	public ControllerButton b = new ControllerButton(2);
	public ControllerButton x = new ControllerButton(3);
	public ControllerButton y = new ControllerButton(4);
	public ControllerButton leftBumper = new ControllerButton(5);
	public ControllerButton rightBumper = new ControllerButton(6);
	public ControllerButton back = new ControllerButton(7);
	public ControllerButton start = new ControllerButton(8);
	public ControllerButton leftStick = new ControllerButton(9);
	public ControllerButton rightStick = new ControllerButton(10);

	public ControllerPOV pov = new ControllerPOV();

	public ControllerAxis leftX = new ControllerAxis(0, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis leftY = new ControllerAxis(1, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis leftTrigger = new ControllerAxis(2, ControllerAxisType.ZERO_TO_POSITIVE);
	public ControllerAxis rightTrigger = new ControllerAxis(3, ControllerAxisType.ZERO_TO_POSITIVE);
	public ControllerAxis rightX = new ControllerAxis(4, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis rightY = new ControllerAxis(5, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	
	public String getButtonDebugString() {
		return (a.get() ? "a" : " ") +
				(b.get() ? "b" : " ") +
				(x.get() ? "x" : " ") +
				(y.get() ? "y" : " ") +
				(leftBumper.get() ? "l" : " ") +
				(rightBumper.get() ? "r" : " ") +
				(back.get() ? "<" : " ") +
				(start.get() ? ">" : " ") +
				(leftStick.get() ? "L" : " ") + 
				(rightStick.get() ? "R" : " ");
	}

	public LogitechF310Gamepad(int port) {
		super(port);
	}

}
