package org.usfirst.frc.team2503.r2016.input.gamepad;

import org.usfirst.frc.team2503.r2016.input.Data;
import org.usfirst.frc.team2503.r2016.input.DataSource;

public class LogitechF310Gamepad extends Gamepad implements DataSource {

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

	public class DataReporter {
		private Data data = new Data();
		private Data buttons = new Data();
		private Data axes = new Data();
		private Data leftAxes = new Data();
		private Data rightAxes = new Data();

		public void update() {
			this.buttons.put("a", a.get());
			this.buttons.put("b", b.get());
			this.buttons.put("x", x.get());
			this.buttons.put("y", y.get());

			this.buttons.put("leftBumper", leftBumper.get());
			this.buttons.put("rightBumper", rightBumper.get());
			this.buttons.put("back", back.get());
			this.buttons.put("start", start.get());
			this.buttons.put("leftStick", leftStick.get());
			this.buttons.put("rightStick", rightStick.get());

			this.leftAxes.put("x", leftX.get());
			this.leftAxes.put("y", leftY.get());
			this.leftAxes.put("t", leftTrigger.get());

			this.rightAxes.put("x", rightX.get());
			this.rightAxes.put("y", rightY.get());
			this.rightAxes.put("t", rightTrigger.get());
		}

		public Data report() {
			return this.data;
		}

		public void compile() {
			this.data.put("buttons", this.buttons);
			this.axes.put("left", this.leftAxes);
			this.axes.put("right", this.rightAxes);
			this.data.put("axes", this.axes);
		}
	}

	private DataReporter DataReporter;

	public Data getData() {
		DataReporter.update();
		DataReporter.compile();
		return DataReporter.report();
	}

	public LogitechF310Gamepad(int port) {
		super(port);

		this.DataReporter = new DataReporter();
	}

}
