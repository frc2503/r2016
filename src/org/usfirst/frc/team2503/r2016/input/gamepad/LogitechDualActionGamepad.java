package org.usfirst.frc.team2503.r2016.input.gamepad;

public class LogitechDualActionGamepad extends Gamepad {
	
	public ControllerButton button1 = new ControllerButton(1);
	public ControllerButton button2 = new ControllerButton(2);
	public ControllerButton button3 = new ControllerButton(3);
	public ControllerButton button4 = new ControllerButton(4);
	public ControllerButton button5 = new ControllerButton(5);
	public ControllerButton button6 = new ControllerButton(6);
	public ControllerButton button7 = new ControllerButton(7);
	public ControllerButton button8 = new ControllerButton(8);
	public ControllerButton leftStick = new ControllerButton(9);
	public ControllerButton rightStick = new ControllerButton(10);
	
	public ControllerPOV pov = new ControllerPOV();
	
	public ControllerAxis leftX = new ControllerAxis(0, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis leftY = new ControllerAxis(1, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis rightX = new ControllerAxis(2, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	public ControllerAxis rightY = new ControllerAxis(3, ControllerAxisType.NEGATIVE_TO_POSITIVE);
	
	public String getButtonDebugString() {
		return (button1.get() ? "1" : " ") +
				(button2.get() ? "2" : " ") +
				(button3.get() ? "3" : " ") +
				(button4.get() ? "4" : " ") +
				(button5.get() ? "5" : " ") +
				(button6.get() ? "6" : " ") +
				(button7.get() ? "7" : " ") +
				(button8.get() ? "8" : " ") +
				(leftStick.get() ? "L" : " ") + 
				(rightStick.get() ? "R" : " ");
	}
	
	public LogitechDualActionGamepad(int port) {
		super(port);
	}

}
