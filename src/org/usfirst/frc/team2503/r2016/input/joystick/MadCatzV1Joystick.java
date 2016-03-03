package org.usfirst.frc.team2503.r2016.input.joystick;

import org.usfirst.frc.team2503.r2016.input.Data;

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

	public class DataReporter {
		private Data data = new Data();
		private Data buttons = new Data();
		private Data axes = new Data();

		public void update() {
			this.buttons.put("trigger", trigger.get());
			this.buttons.put("button2", button2.get());
			this.buttons.put("button3", button3.get());
			this.buttons.put("button4", button4.get());
			this.buttons.put("button5", button5.get());
			this.buttons.put("button6", button6.get());
			this.buttons.put("gripButton", gripButton.get());

			this.axes.put("x", x.get());
			this.axes.put("y", y.get());
			this.axes.put("throttle", throttle.get());
			this.axes.put("rotation", rotation.get());
		}

		public Data report() {
			return this.data;
		}

		public void compile() {
			this.data.put("buttons", this.buttons);
			this.data.put("axes", this.axes);
		}
	}

	private DataReporter DataReporter;

	public Data getData() {
		DataReporter.update();
		DataReporter.compile();
		return DataReporter.report();
	}

	public MadCatzV1Joystick(int port) {
		super(port);

		this.DataReporter = new DataReporter();
	}

}
