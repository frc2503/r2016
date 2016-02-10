package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechF310Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.MadCatzV1Joystick;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
	public MadCatzV1Joystick leftJoystick;
	public MadCatzV1Joystick rightJoystick;
	
	public LogitechF310Gamepad gamepad;
	
	public Robot() {
		leftJoystick = new MadCatzV1Joystick(0);
		rightJoystick = new MadCatzV1Joystick(1);
		
		gamepad = new LogitechF310Gamepad(2);
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
		System.out.println(leftJoystick.getButtonDebugString() + " " + rightJoystick.getButtonDebugString() + " - " + gamepad.getButtonDebugString());
	}

	public void testInit() {
	}

	public void testPeriodic() {
	}

}
