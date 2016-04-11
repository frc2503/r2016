package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.control.DualMotorDriveHelper.DriveHelperMode;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;
import org.usfirst.frc.team2503.r2016.subsystem.CameraSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.DriveBaseSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.IntakeSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.ShooterSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.WinchSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private DriveBaseSubsystem driveBaseSubsystem = new DriveBaseSubsystem(Hardware.leftTrackSpeedController, Hardware.rightTrackSpeedController);
	private IntakeSubsystem intakeSubsystem = new IntakeSubsystem(Hardware.intakeSpeedController, Hardware.intakeLimitSwitch);
	private ShooterSubsystem shooterSubsystem = new ShooterSubsystem(Hardware.shooterSpeedController);
	private WinchSubsystem winchSubsystem = new WinchSubsystem(Hardware.winchSpeedController);
	private CameraSubsystem cameraSubsystem = new CameraSubsystem(Hardware.cameraHorizontalRotationServo, Hardware.cameraVerticalRotationServo);
	private WarriorDriveHelper warriorDriveHelper = new WarriorDriveHelper(driveBaseSubsystem);
	
	public Robot() {
		Logger.addPrintStream("main", new LoggerPrintStream(System.out));
		Logger.addPrintStream("error", new LoggerPrintStream(System.err));
		Logger.addPrintStream("warning", new LoggerPrintStream(System.err));
		Logger.addPrintStream("debug", new LoggerPrintStream(System.out));
		Logger.addPrintStream("information", new LoggerPrintStream(System.out));

		Logger.println("main", "[Robot] Starting... Version '" + Constants.VERSION + "'");
	}

	@Override
	public void robotInit() {
		this.warriorDriveHelper.setMode(DriveHelperMode.DISABLED);
	}

	@Override
	public void disabledInit() {
		this.warriorDriveHelper.setMode(DriveHelperMode.DISABLED);
	}

	@Override
	public void disabledPeriodic() {
		this.warriorDriveHelper.drive(Hardware.leftStick, Hardware.rightStick, Hardware.operatorPad);
		this.driveBaseSubsystem.tick();
		this.intakeSubsystem.tick();
		this.shooterSubsystem.tick();
		this.winchSubsystem.tick();
		this.cameraSubsystem.tick();
	}

	@Override
	public void autonomousInit() {
		this.warriorDriveHelper.setMode(DriveHelperMode.AUTONOMOUS);
	}

	@Override
	public void autonomousPeriodic() {
		this.driveBaseSubsystem.tick();
		this.intakeSubsystem.tick();
		this.shooterSubsystem.tick();
		this.winchSubsystem.tick();
		this.cameraSubsystem.tick();
	}

	@Override
	public void teleopInit() {
		this.warriorDriveHelper.setMode(DriveHelperMode.TELEOPERATED);
	}

	@Override
	public void teleopPeriodic() {
		this.driveBaseSubsystem.tick();
		this.intakeSubsystem.tick();
		this.shooterSubsystem.tick();
		this.winchSubsystem.tick();
		this.cameraSubsystem.tick();
	}

	@Override
	public void testInit() {
		this.warriorDriveHelper.setMode(DriveHelperMode.TEST);
	}

	@Override
	public void testPeriodic() {
		this.driveBaseSubsystem.tick();
		this.intakeSubsystem.tick();
		this.shooterSubsystem.tick();
		this.winchSubsystem.tick();
		this.cameraSubsystem.tick();
	}

}
