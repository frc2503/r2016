package org.usfirst.frc.team2503.r2016;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.json.JSONObject;
import org.usfirst.frc.team2503.lib.util.WarriorMath;
import org.usfirst.frc.team2503.r2016.component.CameraMount.CameraMountMode;
import org.usfirst.frc.team2503.r2016.component.Intake.IntakeMode;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechF310Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.MadCatzV1Joystick;
import org.usfirst.frc.team2503.r2016.server.DataServer;
import org.usfirst.frc.team2503.r2016.server.MessageServer;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;

public class Robot extends IterativeRobot {

	public class RobotDataServer extends DataServer {

		public void update() {
			{
				JSONObject encoders = new JSONObject();

				encoders.put("hooker", robotMap.hooker.encoder.get());

				this.serverData.put("encoders", encoders);
			}

			{
				JSONObject switches = new JSONObject();

				switches.put("intake", robotMap.intake.limitSwitch.get());
				switches.put("hooker", robotMap.hooker.limitSwitch.get());

				this.serverData.put("switches", switches);
			}

			{
				JSONObject pneumatics = new JSONObject();

				pneumatics.put("charged", robotMap.pneumaticsSubsystem.compressor.getPressureSwitchValue());
				pneumatics.put("enabled", robotMap.pneumaticsSubsystem.compressor.enabled());
				pneumatics.put("closed", robotMap.pneumaticsSubsystem.compressor.getClosedLoopControl());

				this.serverData.put("pneumatics", pneumatics);
			}

			{
				JSONObject joystickInputs = new JSONObject();

				joystickInputs.put("left", leftValue);
				joystickInputs.put("right", rightValue);
				joystickInputs.put("winch", winchValue);
				joystickInputs.put("hooker", hookerValue);
				joystickInputs.put("shooter", shooterValue);

				this.serverData.put("joysticks", joystickInputs);
			}
		}

		public RobotDataServer() throws UnknownHostException {
			super();
		}

		public RobotDataServer(InetSocketAddress address) {
			super(address);
		}


	}

	public RobotDataServer robotDataServer;
	public MessageServer messageServer;

	public Thread dataServerThread, messageServerThread;

	public MadCatzV1Joystick leftJoystick;
	public MadCatzV1Joystick rightJoystick;
	public LogitechF310Gamepad gamepad;

	public JSONObject modeObject;

	public MainRobotMap robotMap;

	public double leftValue;
	public double rightValue;
	public double winchValue;
	public double hookerValue;
	public double shooterValue;

	public Robot() {
		Logger.addPrintStream("main", new LoggerPrintStream(System.out));
		Logger.addPrintStream("error", new LoggerPrintStream(System.err));
		Logger.addPrintStream("warning", new LoggerPrintStream(System.err));
		Logger.addPrintStream("debug", new LoggerPrintStream(System.out));
		Logger.addPrintStream("information", new LoggerPrintStream(System.out));

		File csvDataFile = new File("data.csv");

		try {
			Logger.addPrintStream("data", new LoggerPrintStream(csvDataFile));
		} catch(FileNotFoundException e) {
			try {
				csvDataFile.createNewFile();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}

		robotMap = new MainRobotMap();

		robotDataServer = new RobotDataServer(new InetSocketAddress(5800));
		messageServer = new MessageServer(new InetSocketAddress(5801));

		dataServerThread = new Thread(robotDataServer);
		messageServerThread = new Thread(messageServer);

		Logger.println("information", "Starting DataServer...");
		dataServerThread.start();

		Logger.println("information", "Starting MessageServer...");
		messageServerThread.start();

		leftJoystick = new MadCatzV1Joystick(0);
		rightJoystick = new MadCatzV1Joystick(1);

		gamepad = new LogitechF310Gamepad(2);

		modeObject = new JSONObject();
	}

	public void robotInit() {
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
	}

	public void autonomousInit() {
		robotMap.hooker.encoder.reset();
	}

	public void autonomousPeriodic() {
		int ticks = robotMap.hooker.encoder.get();

		if(ticks < 175) {
			robotMap.hooker.set(-0.5);
		} else if(ticks >= 175 && ticks < 185) {
			robotMap.hooker.set(0.0);
		} else {
			robotMap.hooker.set(0.5);
		}
	}

	public void teleopInit() {
	}

	public void teleopPeriodic() {
		leftValue = (leftJoystick.y.get());
		rightValue = (rightJoystick.y.get());
		winchValue = gamepad.rightY.get();
		hookerValue = (gamepad.leftY.get() * 0.5);
		shooterValue = (gamepad.rightTrigger.get());

		leftValue *= leftJoystick.throttle.get();
		rightValue *= rightJoystick.throttle.get();

		// Inverted mode.  Flip values, and swap sides to do mirrored driving.
		if(leftJoystick.button5.get()) {
			double oldLeft = leftValue;
			double oldRight = rightValue;

			leftValue = -oldRight;
			rightValue = -oldLeft;
		}

		if(gamepad.b.get()) {
			robotMap.intake.setMode(IntakeMode.OUTBOUND);
		} else if(gamepad.a.get()) {
			robotMap.intake.setMode(IntakeMode.INBOUND);
		} else if(gamepad.leftBumper.get())  {
			robotMap.intake.setMode(IntakeMode.FIRE);
		} else {
			robotMap.intake.setMode(IntakeMode.STOPPED);
		}

		robotMap.winch.set(winchValue);
		robotMap.hooker.set(hookerValue);
		robotMap.shooter.set(shooterValue);

		robotMap.intake.tick(null);
		robotMap.winch.tick(null);
		robotMap.hooker.tick(null);
		robotMap.shooter.tick(null);

		if(rightJoystick.button2.get() && !rightJoystick.trigger.get()) {
			robotMap.pneumaticsSubsystem.lift.set(DoubleSolenoid.Value.kForward);
		} else if(rightJoystick.trigger.get() && !rightJoystick.button2.get()) {
			robotMap.pneumaticsSubsystem.lift.set(DoubleSolenoid.Value.kReverse);
		} else {
			robotMap.pneumaticsSubsystem.lift.set(DoubleSolenoid.Value.kOff);
		}

		robotMap.driveBase.drive(leftValue, rightValue);

		double povAngle = (double) gamepad.pov.get();

		if(povAngle >= 0) {
			robotMap.cameraMount.setMode(CameraMountMode.LOOKING);
			robotMap.cameraMount.tweak(Math.cos(WarriorMath.degreesToRadians(90.0d - povAngle)), Math.sin(90.0d - povAngle));
		} else {
			robotMap.cameraMount.setMode(CameraMountMode.TARGETING);
		}

		robotMap.cameraMount.tick(null);

		if(robotMap.intake.limitSwitch.get()) {
			robotMap.indicatorRelay.set(Relay.Value.kOn);
		} else {
			robotMap.indicatorRelay.set(Relay.Value.kOff);
		}

		robotDataServer.update();
		robotDataServer.send();
	}


	public void testInit() {
	}

	public void testPeriodic() {
	}

}
