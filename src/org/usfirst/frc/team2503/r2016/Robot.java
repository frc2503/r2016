package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.component.RhinoTrack;
import org.usfirst.frc.team2503.r2016.component.Winch;
import org.usfirst.frc.team2503.r2016.input.gamepad.Gamepad;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechDualActionGamepad;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechF310Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.MadCatzV1Joystick;
import org.usfirst.frc.team2503.r2016.subsystem.DriveBase;
import org.usfirst.frc.team2503.r2016.subsystem.MainDriveBase;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {
	
	public MadCatzV1Joystick leftJoystick;
	public MadCatzV1Joystick rightJoystick;
	
	public RhinoTrack leftTrack;
	public RhinoTrack rightTrack;
	
	public Encoder leftTrackEncoder;
	public Encoder rightTrackEncoder;
	
	public Winch winch;
	
	public DriveBase driveBase;
	
	public Gamepad gamepad;
	
	public Robot() {
		System.setProperty("java.awt.headless", "true");

		leftTrackEncoder = new Encoder(Constants.leftTrackEncoderAChannel, Constants.leftTrackEncoderBChannel);
		rightTrackEncoder = new Encoder(Constants.rightTrackEncoderAChannel, Constants.rightTrackEncoderBChannel);
		
		leftJoystick = new MadCatzV1Joystick(0);
		rightJoystick = new MadCatzV1Joystick(1);
		
		gamepad = new LogitechDualActionGamepad(2);
		
		leftTrack = new RhinoTrack(Constants.leftTrackSpeedController);
		rightTrack = new RhinoTrack(Constants.rightTrackSpeedController);

		driveBase = new MainDriveBase(leftTrack, rightTrack);
		
		winch = new Winch(Constants.winchSpeedController);
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
		double leftY = leftJoystick.y.get();
		
		System.out.println(leftY);
		
		winch.set(leftY);
	}

	public void testInit() {
	}

	public void testPeriodic() {
	}

}
