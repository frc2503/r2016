package org.usfirst.frc.team2503.r2016.control;

import org.usfirst.frc.team2503.r2016.subsystem.base.Subsystem;
import org.usfirst.frc.team2503.r2016.control.hid.Joystick;

public abstract class DriveHelper implements Tickable {
	
	public enum DriveHelperMode {
		DISABLED,
		AUTONOMOUS,
		TELEOPERATED,
		TEST
	}

	private DriveHelperMode _mode;
	
	protected Subsystem[] _subsystems;
	
	public abstract void drive(Joystick left, Joystick right, Joystick gamepad);
	public abstract void tick();
	
	public void setMode(DriveHelperMode _mode) {
		this._mode = _mode;
	}
	
	public DriveHelperMode getMode() {
		return this._mode;
	}
	
	public DriveHelper(Subsystem... _subsystems) {
		this._subsystems = _subsystems;
	}
	
}
