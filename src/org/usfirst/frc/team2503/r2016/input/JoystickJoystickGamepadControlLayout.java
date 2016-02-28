package org.usfirst.frc.team2503.r2016.input;

import org.usfirst.frc.team2503.r2016.input.gamepad.Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.Joystick;

public abstract class JoystickJoystickGamepadControlLayout implements ControlLayout {
	
	private Joystick left;
	private Joystick right;
	private Gamepad gamepad;
	
	public JoystickJoystickGamepadControlLayout(Joystick left, Joystick right, Gamepad gamepad) {
		this.left = left;
		this.right = right;
		this.gamepad = gamepad;
	}

	public Data getData() {
		Data leftData = this.left.getData();
		Data rightData = this.right.getData();
		Data gamepadData = this.gamepad.getData();
		
		Data data = new Data();
		
		data.put("left", leftData);
		data.put("right", rightData);
		data.put("gamepad", gamepadData);
		
		return data;
	}
	
}
