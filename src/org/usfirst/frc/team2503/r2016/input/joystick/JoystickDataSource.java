package org.usfirst.frc.team2503.r2016.input.joystick;

import org.usfirst.frc.team2503.r2016.data.Data;
import org.usfirst.frc.team2503.r2016.data.DataSource;

public abstract class JoystickDataSource implements DataSource {
	
	public Data axes;
	public Data buttons;
	
	public Data getAxes() {
		return this.axes;
	}
	
	public Data getButtons() {
		return this.buttons;
	}

}
