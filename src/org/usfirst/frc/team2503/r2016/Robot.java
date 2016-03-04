package org.usfirst.frc.team2503.r2016;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.json.JSONObject;
import org.usfirst.frc.team2503.lib.util.WarriorMath;
import org.usfirst.frc.team2503.r2016.component.CameraMount.CameraMountMode;
import org.usfirst.frc.team2503.r2016.component.Intake.IntakeMode;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;
import org.usfirst.frc.team2503.r2016.input.Data;
import org.usfirst.frc.team2503.r2016.input.JoystickJoystickGamepadControlLayout;
import org.usfirst.frc.team2503.r2016.input.MadCatzV1JoystickMadCatzV1JoystickLogitechF310GamepadControlLayout;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechF310Gamepad;
import org.usfirst.frc.team2503.r2016.input.joystick.MadCatzV1Joystick;
import org.usfirst.frc.team2503.r2016.server.DataServer;
import org.usfirst.frc.team2503.r2016.server.MessageServer;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;

public class Robot extends IterativeRobot {

	public class RobotDataServer extends DataServer implements Tickable {

		public void tick(Data data) {
			{
				JSONObject encoders = new JSONObject();

				encoders.put("hooker", R.hooker.encoder.get());

				this.serverData.put("encoders", encoders);
			}

			{
				JSONObject switches = new JSONObject();

				switches.put("intake", R.intake.limitSwitch.get());
				switches.put("hooker", R.hooker.limitSwitch.get());

				this.serverData.put("switches", switches);
			}

			{
				JSONObject pneumatics = new JSONObject();

				pneumatics.put("charged", R.pneumaticsSubsystem.compressor.getPressureSwitchValue());
				pneumatics.put("enabled", R.pneumaticsSubsystem.compressor.enabled());
				pneumatics.put("closed", R.pneumaticsSubsystem.compressor.getClosedLoopControl());

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

	public JoystickJoystickGamepadControlLayout L = new MadCatzV1JoystickMadCatzV1JoystickLogitechF310GamepadControlLayout(leftJoystick, rightJoystick, gamepad);
	
	public JSONObject modeObject;

	public final MainRobotMap R;

	public double leftValue;
	public double rightValue;
	public double winchValue;
	public double hookerValue;
	public double shooterValue;
	
	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	public Robot() {
		robotDataServer = new RobotDataServer(new InetSocketAddress(5800));
		messageServer = new MessageServer(new InetSocketAddress(5801));

		Logger.addPrintStream("main", new LoggerPrintStream(System.out));
		Logger.addPrintStream("error", new LoggerPrintStream(System.err));
		Logger.addPrintStream("warning", new LoggerPrintStream(System.err));
		Logger.addPrintStream("debug", new LoggerPrintStream(System.out));
		Logger.addPrintStream("information", new LoggerPrintStream(System.out));
		Logger.addPrintStream("data", new LoggerPrintStream(robotDataServer.new WebSocketByteArrayOutputStream()));

		Logger.println("main", "[Robot] Starting... Version '" + Constants.VERSION + "'");
		
		R = new MainRobotMap();

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
		gyro.reset();
		R.driveBase.leftEncoder.reset();
		R.driveBase.rightEncoder.reset();
	}

	public void autonomousPeriodic() {
		System.out.println("[" + R.driveBase.leftEncoder.get() + "] [" + R.driveBase.rightEncoder.get() + "] " + gyro.getAngle());
		
		double angle = gyro.getAngle();
		int leftTicks = R.driveBase.leftEncoder.get();
		int rightTicks = R.driveBase.rightEncoder.get();
		
		int averageTicks = (leftTicks + rightTicks) / 2;

		double left = -0.5 + 0.5 * Math.sin(WarriorMath.degreesToRadians(angle));
		double right = -0.5 - 0.5 * Math.sin(WarriorMath.degreesToRadians(angle));

		System.out.println(left + " " + right + " " + (left > right ? "L" : "R"));
				
		if(averageTicks <= 1450 * 5) {
			R.driveBase.drive(left,  right);
		} else if(averageTicks >= 1550 * 5) {
			R.driveBase.drive(-left, -right);
		} else {
			R.driveBase.drive(0.0, 0.0);
		}
		
		// R.driveBase.drive(left,  right);
	}

	public void teleopInit() {
	}

	public void teleopPeriodic() {
		// TODO: Move All of this into ControlLayouts
		leftValue = (leftJoystick.y.get());
		rightValue = (rightJoystick.y.get());
		winchValue = gamepad.rightY.get();
		hookerValue = (gamepad.leftY.get() * 0.5);
		shooterValue = (gamepad.rightTrigger.get());

		// TODO: Move all of this into
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
			R.intake.setMode(IntakeMode.OUTBOUND);
		} else if(gamepad.a.get()) {
			R.intake.setMode(IntakeMode.INBOUND);
		} else if(gamepad.leftBumper.get())  {
			R.intake.setMode(IntakeMode.FIRE);
		} else {
			R.intake.setMode(IntakeMode.STOPPED);
		}

		R.winch.set(winchValue);
		R.hooker.set(hookerValue);
		R.shooter.set(shooterValue);

		R.intake.tick(null);
		R.winch.tick(null);
		R.hooker.tick(null);
		R.shooter.tick(null);

		if(rightJoystick.button2.get() && !rightJoystick.trigger.get()) {
			R.pneumaticsSubsystem.lift.set(DoubleSolenoid.Value.kForward);
		} else if(rightJoystick.trigger.get() && !rightJoystick.button2.get()) {
			R.pneumaticsSubsystem.lift.set(DoubleSolenoid.Value.kReverse);
		} else {
			R.pneumaticsSubsystem.lift.set(DoubleSolenoid.Value.kOff);
		}

		R.driveBase.drive(leftValue, rightValue);

		double povAngle = (double) gamepad.pov.get();

		if(povAngle >= 0) {
			R.cameraMount.setMode(CameraMountMode.LOOKING);
			R.cameraMount.tweak(Math.cos(WarriorMath.degreesToRadians(90.0d - povAngle)), Math.sin(WarriorMath.degreesToRadians(90.0d - povAngle)));
		} else {
			R.cameraMount.setMode(CameraMountMode.TARGETING);
		}

		R.cameraMount.tick(null);

		if(R.intake.limitSwitch.get()) {
			R.indicatorRelay.set(Relay.Value.kOn);
		} else {
			R.indicatorRelay.set(Relay.Value.kOff);
		}

		robotDataServer.tick(null);
		robotDataServer.send();
	}


	public void testInit() {
	}

	public void testPeriodic() {
	}

}
