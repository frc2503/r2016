package org.usfirst.frc.team2503.r2016;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {
	
	public Joystick gamepad;
	public DriveBase driveBase;
	
	public Robot() {
		
		gamepad = new Joystick(0);
		driveBase = new TestbotDriveBase();
		
	}
	
	public void robotInit() {
	}
	
	public void disabledInit() {
	}
	
	public void disabledPeriodic() {
	}
	
	public void autonomousInit() {
	}
	
	public void autonomousPeriodic() {
	}

	public void teleopInit() {		
	}
	
	public void teleopPeriodic() {
		driveBase.drive(gamepad.getRawAxis(2), gamepad.getRawAxis(3));
	}
	
	public void testInit() {
	}
	
	public void testPeriodic() {
	}
	
}
