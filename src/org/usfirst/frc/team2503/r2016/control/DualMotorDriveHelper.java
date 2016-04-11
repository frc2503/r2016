package org.usfirst.frc.team2503.r2016.control;

import edu.wpi.first.wpilibj.Joystick;

public abstract class DualMotorDriveHelper {
	
	public enum DriveHelperMode {
		DISABLED,
		AUTONOMOUS,
		TELEOPERATED,
		TEST
	}

	private DriveHelperMode _mode;
	
	protected DualMotorDrivable _drive;
	
	public abstract void drive(Joystick left, Joystick right, Joystick gamepad);
	
	public void setMode(DriveHelperMode _mode) {
		this._mode = _mode;
	}
	
	public DriveHelperMode getMode() {
		return this._mode;
	}
	
	public DualMotorDriveHelper(DualMotorDrivable _drive) {
		this._drive = _drive;
	}
	
}
