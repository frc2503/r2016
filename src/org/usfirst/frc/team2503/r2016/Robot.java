package org.usfirst.frc.team2503.r2016;

import java.net.InetSocketAddress;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.component.Hooker;
import org.usfirst.frc.team2503.r2016.component.RhinoTrack;
import org.usfirst.frc.team2503.r2016.component.Winch;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;
import org.usfirst.frc.team2503.r2016.input.gamepad.Gamepad;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechDualActionGamepad;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechF310Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.MadCatzV1Joystick;
//import org.usfirst.frc.team2503.r2016.input.vision.Camera;
import org.usfirst.frc.team2503.r2016.server.DataServer;
import org.usfirst.frc.team2503.r2016.server.ImageServer;
import org.usfirst.frc.team2503.r2016.server.MessageServer;
import org.usfirst.frc.team2503.r2016.subsystem.DriveBase;
import org.usfirst.frc.team2503.r2016.subsystem.MainDriveBase;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {

	public DataServer dataServer;
	public MessageServer messageServer;
	public ImageServer imageServer;
	
	public Thread dataServerThread, messageServerThread, imageServerThread;
	
	public MadCatzV1Joystick leftJoystick;
	public MadCatzV1Joystick rightJoystick;
	public LogitechF310Gamepad gamepad;

	public RhinoTrack leftTrack;
	public RhinoTrack rightTrack;

	public Encoder leftTrackEncoder;
	public Encoder rightTrackEncoder;

	public JSONObject modeObject;
	
	public Winch winch;
	public Hooker hooker;
	public DriveBase driveBase;
	
	public Robot() {
		// Set the Java AWT to be happy with the fact that we're
		// running in a decapitated state.
		System.setProperty("java.awt.headless", "true");
		
		dataServer = new DataServer(new InetSocketAddress(5800));
		messageServer = new MessageServer(new InetSocketAddress(5801));
		imageServer = new ImageServer(new InetSocketAddress(5802));
		
		dataServerThread = new Thread(dataServer);
		messageServerThread = new Thread(messageServer);
		imageServerThread = new Thread(imageServer);
		
		dataServerThread.start();
		messageServerThread.start();
		imageServerThread.start();
		
		Logger.addPrintStream(new LoggerPrintStream(System.out));

		leftJoystick = new MadCatzV1Joystick(0);
		rightJoystick = new MadCatzV1Joystick(1);

		gamepad = new LogitechF310Gamepad(2);

		leftTrack = new RhinoTrack(Constants.leftTrackSpeedController);
		rightTrack = new RhinoTrack(Constants.rightTrackSpeedController);

		leftTrackEncoder = new Encoder(Constants.leftTrackEncoderAChannel, Constants.leftTrackEncoderBChannel);
		rightTrackEncoder = new Encoder(Constants.rightTrackEncoderAChannel, Constants.rightTrackEncoderBChannel);
		
		winch = new Winch(Constants.winchSpeedController);
		hooker = new Hooker(Constants.hookerSpeedController);
		driveBase = new MainDriveBase(leftTrack, rightTrack);
		
		modeObject = new JSONObject();
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
		double vert = (gamepad.rightY.get() + 1.0) / 2.0;
		double horz = (gamepad.rightX.get() + 1.0) / 2.0;
		double winchV = leftJoystick.y.get();
		
		double hookerV = (gamepad.leftY.get() * 0.5);
		
		winch.set(winchV);
		hooker.set(hookerV);
		
		if(gamepad.a.get()) {
			Constants.cameraHorizontalRotationServo.set(1.0);
			Constants.cameraVerticalRotationServo.set(0.5);
		} else if(gamepad.b.get()) {
			Constants.cameraHorizontalRotationServo.set(1.0);
			Constants.cameraVerticalRotationServo.set(0.6);
		} else if(gamepad.y.get()) {
			Constants.cameraHorizontalRotationServo.set(0.0);
			Constants.cameraVerticalRotationServo.set(0.3);
		} else if(gamepad.x.get()) {
			Constants.cameraVerticalRotationServo.set(vert);
			Constants.cameraHorizontalRotationServo.set(horz);
		}
	}

	public void testInit() {
	}
 
	public void testPeriodic() {
	}

}
