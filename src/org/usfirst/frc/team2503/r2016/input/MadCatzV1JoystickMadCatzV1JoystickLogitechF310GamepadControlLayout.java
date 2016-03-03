package org.usfirst.frc.team2503.r2016.input;

import org.usfirst.frc.team2503.r2016.input.gamepad.Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.Joystick;

public class MadCatzV1JoystickMadCatzV1JoystickLogitechF310GamepadControlLayout extends JoystickJoystickGamepadControlLayout {

	public Data getData() {
		Data data = super.getData();

		// Any modifications to be made should be done here.

		return data;
	}

	public MadCatzV1JoystickMadCatzV1JoystickLogitechF310GamepadControlLayout(Joystick left, Joystick right, Gamepad gamepad) {
		super(left, right, gamepad);
	}

}
