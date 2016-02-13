package org.usfirst.frc.team2503.r2016;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.component.RhinoTrack;
import org.usfirst.frc.team2503.r2016.component.Winch;
import org.usfirst.frc.team2503.r2016.data.DataServer;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;
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
	public Gamepad gamepad;

	public RhinoTrack leftTrack;
	public RhinoTrack rightTrack;

	public Encoder leftTrackEncoder;
	public Encoder rightTrackEncoder;

	public JSONObject modeObject;
	
	public Winch winch;
	public DriveBase driveBase;
	
	public DataServer dataServer;

	public Robot() {
		// Set the Java AWT to be happy with the fact that we're
		// running in a decapitated state.
		System.setProperty("java.awt.headless", "true");
		
		Logger.addPrintStream(new LoggerPrintStream(System.out));

		leftJoystick = new MadCatzV1Joystick(0);
		rightJoystick = new MadCatzV1Joystick(1);

		gamepad = new LogitechDualActionGamepad(2);

		leftTrack = new RhinoTrack(Constants.leftTrackSpeedController);
		rightTrack = new RhinoTrack(Constants.rightTrackSpeedController);

		leftTrackEncoder = new Encoder(Constants.leftTrackEncoderAChannel, Constants.leftTrackEncoderBChannel);
		rightTrackEncoder = new Encoder(Constants.rightTrackEncoderAChannel, Constants.rightTrackEncoderBChannel);

		winch = new Winch(Constants.winchSpeedController);
		driveBase = new MainDriveBase(leftTrack, rightTrack);
		
		modeObject = new JSONObject();
		dataServer = new DataServer(5800);
	}

	public void robotInit() {
	}

	public void disabledInit() {
		dataServer.data = new JSONObject();
		
		modeObject.put("name", "disabled");
		dataServer.put("mode", modeObject);
	}

	public void disabledPeriodic() {
	}

	public void autonomousInit() {
		dataServer.data = new JSONObject();
		
		modeObject.put("name", "autonomous");
		dataServer.put("mode", modeObject);
	}

	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		dataServer.data = new JSONObject();
		
		modeObject.put("name", "teleop");
		dataServer.put("mode", modeObject);
	}

	public void teleopPeriodic() {
		double leftY = leftJoystick.y.get();
		
		dataServer.put("left", new JSONObject().put("y", leftY));
		
		winch.set(leftY);
	}

	public void testInit() {
		dataServer.data = new JSONObject();
		
		modeObject.put("name", "test");
		dataServer.put("mode", modeObject);
	}

	public void testPeriodic() {
	}

}
