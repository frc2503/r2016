package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.control.DualMotorDrivable;
import org.usfirst.frc.team2503.r2016.control.DualMotorDriveHelper;

import edu.wpi.first.wpilibj.Joystick;

public class WarriorDriveHelper extends DualMotorDriveHelper {
	
	@Override
	public void drive(Joystick left, Joystick right, Joystick operator) {
		switch(this.getMode()) {
		case AUTONOMOUS:
			autonomous(left, right, operator);
			break;
			
		case TELEOPERATED:
			teleoperated(left, right, operator);
			break;
			
		case TEST:
			test(left, right, operator);
			break;
			
		default:
		case DISABLED:
			disabled(left, right, operator);
			break;
		}
	}
	
	protected void autonomous(Joystick left, Joystick right, Joystick operator) {
	}
	
	protected void teleoperated(Joystick left, Joystick right, Joystick operator) {
	}
	
	protected void disabled(Joystick left, Joystick right, Joystick operator) {
	}
	
	protected void test(Joystick left, Joystick right, Joystick operator) {
	}
	
	public WarriorDriveHelper(DualMotorDrivable _drive) {
		super(_drive);
	}

}
