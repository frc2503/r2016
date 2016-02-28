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

	public Data getData() {
		Data data = new Data();

		{
			Data buttons = new Data();

			buttons.put("a", a.get());
			buttons.put("b", b.get());
			buttons.put("x", x.get());
			buttons.put("y", y.get());

			buttons.put("leftBumper", leftBumper.get());
			buttons.put("rightBumper", rightBumper.get());
			buttons.put("back", back.get());
			buttons.put("start", start.get());
			buttons.put("leftStick", leftStick.get());
			buttons.put("rightStick", rightStick.get());

			data.put("buttons", buttons);
		}

		{
			Data axes = new Data();

			{
				Data leftAxes = new Data();

				leftAxes.put("x", leftX.get());
				leftAxes.put("y", leftY.get());
				leftAxes.put("t", leftTrigger.get());

				axes.put("left", leftAxes);
			}

			{
				Data rightAxes = new Data();

				rightAxes.put("x", rightX.get());
				rightAxes.put("y", rightY.get());
				rightAxes.put("t", rightTrigger.get());

				axes.put("right", rightAxes);
			}

			data.put("axes", axes);
		}

		return data;
	}

	public LogitechF310Gamepad(int port) {
		super(port);
	}

}
