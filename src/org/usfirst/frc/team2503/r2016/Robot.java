package org.usfirst.frc.team2503.r2016;

import java.net.InetSocketAddress;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.component.CameraMount;
import org.usfirst.frc.team2503.r2016.component.Hooker;
import org.usfirst.frc.team2503.r2016.component.Intake;
import org.usfirst.frc.team2503.r2016.component.RhinoTrack;
import org.usfirst.frc.team2503.r2016.component.Shooter;
import org.usfirst.frc.team2503.r2016.component.Winch;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;
import org.usfirst.frc.team2503.r2016.input.gamepad.Gamepad;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechDualActionGamepad;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechF310Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.MadCatzV1Joystick;
//import org.usfirst.frc.team2503.r2016.input.vision.Camera;
import org.usfirst.frc.team2503.r2016.server.DataServer;
import org.usfirst.frc.team2503.r2016.server.MessageServer;
import org.usfirst.frc.team2503.r2016.subsystem.DriveBase;
import org.usfirst.frc.team2503.r2016.subsystem.MainDriveBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {

	public DataServer dataServer;
	public MessageServer messageServer;
	
	public Thread dataServerThread, messageServerThread;
	
	public MadCatzV1Joystick leftJoystick;
	public MadCatzV1Joystick rightJoystick;
	public LogitechF310Gamepad gamepad;

	public RhinoTrack leftTrack;
	public RhinoTrack rightTrack;

	public Encoder leftTrackEncoder;
	public Encoder rightTrackEncoder;
	public Encoder hookerEncoder;

	public JSONObject modeObject;
	
	public Winch winch;
	public Hooker hooker;
	public Shooter shooter;
	public Intake intake;
	public DriveBase driveBase;
	
	public CameraMount cameraMount;
	
	public double horz;
	public double vert;
	
	public Robot() {
		// Set the Java AWT to be happy with the fact that we're
		// running in a decapitated state.
		System.setProperty("java.awt.headless", "true");
		
		dataServer = new DataServer(new InetSocketAddress(5800));
		messageServer = new MessageServer(new InetSocketAddress(5801));
		
		dataServerThread = new Thread(dataServer);
		messageServerThread = new Thread(messageServer);
		
		System.out.println("[DataServer] Starting...");
		dataServerThread.start();
		
		System.out.println("[MessageServer] Starting...");
		messageServerThread.start();
		
		Logger.addPrintStream(new LoggerPrintStream(System.out));

		leftJoystick = new MadCatzV1Joystick(0);
		rightJoystick = new MadCatzV1Joystick(1);

		gamepad = new LogitechF310Gamepad(2);

		leftTrack = new RhinoTrack(Constants.leftTrackSpeedController);
		rightTrack = new RhinoTrack(Constants.rightTrackSpeedController);

		leftTrack.setInverted(true);
		rightTrack.setInverted(false);

		leftTrackEncoder = new Encoder(Constants.leftTrackEncoderAChannel, Constants.leftTrackEncoderBChannel);
		rightTrackEncoder = new Encoder(Constants.rightTrackEncoderAChannel, Constants.rightTrackEncoderBChannel);
		hookerEncoder = new Encoder(Constants.intakeEncoderAChannel, Constants.intakeEncoderBChannel);
		
		winch = new Winch(Constants.winchSpeedController);
		hooker = new Hooker(Constants.hookerSpeedController);
		shooter = new Shooter(Constants.shooterSpeedController);
		intake = new Intake(Constants.intakeSpeedController, Constants.intakeLimitSwitch);
		driveBase = new MainDriveBase(leftTrack, rightTrack);
		
		cameraMount = new CameraMount(Constants.cameraHorizontalRotationServo, Constants.cameraVerticalRotationServo);

		winch.setInverted(true);
		hooker.setInverted(true);
		shooter.setInverted(true);
		intake.setInverted(true);
		
		modeObject = new JSONObject();
	}

	public void robotInit() {
		Constants.compressor.setClosedLoopControl(true);
		Constants.compressor.start();
		
		Constants.lightRelay.setDirection(Relay.Direction.kForward);
		
		leftTrackEncoder.reset();
		rightTrackEncoder.reset();
		hookerEncoder.reset();
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
		messageServer.sendMessage("info", "DANK MEMES CAN'T MELT JET FUEL");
	}

	public void autonomousInit() {
	}

	public void autonomousPeriodic() {
		messageServer.sendMessage("info", "DANK MEMES CAN'T MELT JET FUEL");
	}

	public void teleopInit() {
		horz = 1.0;
		vert = 0.5;
	}

	public void teleopPeriodic() {
		double left = (leftJoystick.y.get());
		double right = (rightJoystick.y.get());
		
		left *= leftJoystick.throttle.get();
		right *= rightJoystick.throttle.get();
		
		if(leftJoystick.button5.get()) {
			double oldLeft = left;
			double oldRight = right;
			
			left = -oldRight;
			right = -oldLeft;
		}
		
		double winchV = gamepad.rightY.get();
		double hookerV = (gamepad.leftY.get() * 0.5);
		double shooterV = (gamepad.rightTrigger.get());
		
		if(!Constants.intakeLimitSwitch.get()) {
			if((gamepad.a.get() && !gamepad.b.get()) || gamepad.rightBumper.get() || gamepad.leftBumper.get()) {
				intake.set(1.0);
			} else if(gamepad.b.get()  && !gamepad.a.get()) {
				intake.set(-1.0);
			} else {
				intake.set(0.0);
			}
		} else {
			if(gamepad.leftBumper.get() && gamepad.rightBumper.get()) {
				intake.set(1.0);
			} else {
				intake.set(0.0);
			}
		}

		winch.set(winchV);
		hooker.set(hookerV);
		shooter.set(shooterV);
		
//		if(gamepad.a.get()) {
//			Constants.cameraHorizontalRotationServo.set(1.0);
//			Constants.cameraVerticalRotationServo.set(0.5);
//		} else if(gamepad.b.get()) {
//			Constants.cameraHorizontalRotationServo.set(1.0);
//			Constants.cameraVerticalRotationServo.set(0.6);
//		} else if(gamepad.y.get()) {
//			Constants.cameraHorizontalRotationServo.set(0.0);
//			Constants.cameraVerticalRotationServo.set(0.3);
//		} else if(gamepad.x.get()) {
//			Constants.cameraVerticalRotationServo.set(vert);
//			Constants.cameraHorizontalRotationServo.set(horz);
//		}
		
		if(rightJoystick.button2.get() && !rightJoystick.trigger.get()) {
			Constants.lift.set(DoubleSolenoid.Value.kForward);
		} else if(rightJoystick.trigger.get() && !rightJoystick.button2.get()) {
			Constants.lift.set(DoubleSolenoid.Value.kReverse);
		} else {
			Constants.lift.set(DoubleSolenoid.Value.kOff);
		}
		
		driveBase.drive(left, right);
		
		if(gamepad.pov.get() >= 0) {
			double povAngle = 90.0d - (double) gamepad.pov.get();
			
			horz += (Math.cos((Math.PI / 180.0) * povAngle) * 0.01);
			vert += (Math.sin((Math.PI / 180.0) * povAngle) * 0.01);
			
			if(horz > 1.0) horz = 1.0;
			if(horz < 0.0) horz = 0.0;
			
			if(vert > 1.0) vert = 1.0;
			if(vert < 0.0) vert = 0.0;
		}
		
		if(gamepad.back.get()) {
			vert = 0.5;
			horz = 1.0;
		}
		
		cameraMount.set(horz, vert);

		if(rightJoystick.button3.get()) {
			Constants.lightRelay.set(Relay.Value.kOn);
		} else {
			Constants.lightRelay.set(Relay.Value.kOff);
		}
		
		dataServer.serverData.put("hooker", hookerEncoder.get());
		dataServer.serverData.put("left", leftTrackEncoder.get());
		dataServer.serverData.put("right", rightTrackEncoder.get());
		dataServer.serverData.put("pressure-charged", Constants.compressor.getPressureSwitchValue());
		dataServer.serverData.put("compressor-enabled", Constants.compressor.enabled());
		dataServer.serverData.put("compressor-closed-loop", Constants.compressor.getClosedLoopControl());
		
		// System.out.println(hookerEncoder.get() + ", " + leftTrackEncoder.get() + ", " + rightTrackEncoder.get());

		dataServer.send();
		
		messageServer.sendMessage("info", "DANK MEMES CAN'T MELT JET FUEL");
		
	}
	

	public void testInit() {
	}
 
	public void testPeriodic() {
		messageServer.sendMessage("info", "DANK MEMES CAN'T MELT JET FUEL");	
	}

}