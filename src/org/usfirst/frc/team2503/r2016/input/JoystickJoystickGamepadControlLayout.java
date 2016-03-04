package org.usfirst.frc.team2503.r2016.input;

import org.usfirst.frc.team2503.r2016.input.gamepad.Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.Joystick;

public abstract class JoystickJoystickGamepadControlLayout implements ControlLayout {

	public class DataReporter {
		private Data data = new Data();
		private Data leftJoystickData = null;
		private Data rightJoystickData = null;
		private Data gamepadData = null;

		public void update() {
			this.leftJoystickData = left.getData();
			this.rightJoystickData = right.getData();
			this.gamepadData = gamepad.getData();
		}

		public Data report() {
			return this.data;
		}

		public void compile() {
			this.data.put("leftJoystick", this.leftJoystickData);
			this.data.put("rightJoystick", this.rightJoystickData);
			this.data.put("gamepad", this.gamepadData);
		}
	}
	
	private DataReporter DataReporter;
	
	private Joystick left;
	private Joystick right;
	private Gamepad gamepad;

	public JoystickJoystickGamepadControlLayout(Joystick left, Joystick right, Gamepad gamepad) {
		this.left = left;
		this.right = right;
		this.gamepad = gamepad;
		
		this.DataReporter = new DataReporter();
	}

	public Data getData() {
		DataReporter.update();
		DataReporter.compile(); 
		return DataReporter.report();
	}

}
