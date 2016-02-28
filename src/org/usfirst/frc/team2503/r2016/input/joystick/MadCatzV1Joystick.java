package org.usfirst.frc.team2503.r2016.input.joystick;

public class MadCatzV1Joystick extends Joystick {

	public ControllerButton trigger = new ControllerButton(1);
	public ControllerButton button2 = new ControllerButton(2);
	public ControllerButton button3 = new ControllerButton(3);
	public ControllerButton button4 = new ControllerButton(4);
	public ControllerButton button5 = new ControllerButton(5);
	public ControllerButton button6 = new ControllerButton(6);
	public ControllerButton gripButton = new ControllerButton(7);

	public ControllerPOV pov = new ControllerPOV();

	public ControllerAxis x = new ControllerAxis(0, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis y = new ControllerAxis(1, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis throttle = new ControllerAxis(2, ControllerAxisType.ZERO_TO_POSITIVE);
	public ControllerAxis rotation = new ControllerAxis(3, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	
	public String getButtonDebugString() {
		return (trigger.get() ? "t" : " ") +
				(button2.get() ? "2" : " ") +
				(button3.get() ? "3" : " ") +
				(button4.get() ? "4" : " ") +
				(button5.get() ? "5" : " ") +
				(button6.get() ? "6" : " ") +
				(gripButton.get() ? "G" : " ");
	}

	public MadCatzV1Joystick(int port) {
		super(port);
	}

}
