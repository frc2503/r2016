package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.subsystem.CameraSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.DriveBaseSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.HookerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.IntakeSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.IntakeSubsystem.IntakeSubsystemMode;
import org.usfirst.frc.team2503.r2016.subsystem.PortcullisLiftSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.PortcullisLiftSubsystem.PortcullisLiftSubsystemMode;
import org.usfirst.frc.team2503.r2016.subsystem.ShooterSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.WinchSubsystem;
import org.usfirst.frc.team2503.r2016.control.DriveHelper;
import org.usfirst.frc.team2503.r2016.control.hid.*;

public class WarriorDriveHelper extends DriveHelper {
	
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
	
	private DriveBaseSubsystem _driveBaseSubsystem;
	private IntakeSubsystem _intakeSubsystem;
	private ShooterSubsystem _shooterSubsystem;
	private WinchSubsystem _winchSubsystem;
	private CameraSubsystem _cameraSubsystem;
	private PortcullisLiftSubsystem _portcullisLiftSubsystem;
	private HookerSubsystem _hookerSubsystem;
	
	@Override
	public void tick() {
		this._driveBaseSubsystem.tick();
		this._intakeSubsystem.tick();
		this._shooterSubsystem.tick();
		this._winchSubsystem.tick();
		this._cameraSubsystem.tick();
		this._portcullisLiftSubsystem.tick();
		this._hookerSubsystem.tick();
	}
	
	protected void autonomous(Joystick left, Joystick right, Joystick operator) {
	}
	
	protected void teleoperated(Joystick _left, Joystick _right, Joystick _operator) {
		MadCatzV1Joystick left = (MadCatzV1Joystick) _left;
		MadCatzV1Joystick right = (MadCatzV1Joystick) _right;
		LogitechF310Gamepad operator = (LogitechF310Gamepad) _operator;
		
		// TODO: Replace logic with Velocity mapping
		double leftPower = left.yAxis.get();
		double rightPower = right.yAxis.get();
		
		double shooterPower = operator.getRawAxis(3);
		double winchPower = operator.getY(GenericHID.Hand.kLeft) * 0.25d;
		
		{
			if(operator.getRawButton(2))
				this._intakeSubsystem.setMode(IntakeSubsystemMode.OUTPUTTING);
			else if(operator.getRawButton(1))
				this._intakeSubsystem.setMode(IntakeSubsystemMode.INTAKING);
			else if(operator.getRawButton(5))
				this._intakeSubsystem.setMode(IntakeSubsystemMode.FIRING);
			else
				this._intakeSubsystem.setMode(IntakeSubsystemMode.STOPPED);
		}
		
		{
			if(right.liftRaiseButton.get())
				this._portcullisLiftSubsystem.setMode(PortcullisLiftSubsystemMode.RAISING);
			else if(right.liftLowerButton.get())
				this._portcullisLiftSubsystem.setMode(PortcullisLiftSubsystemMode.LOWERING);
			else
				this._portcullisLiftSubsystem.setMode(PortcullisLiftSubsystemMode.STOPPED);
		}
		
		{
			this._hookerSubsystem.setPower(0.0d);
		}
	}
	
	protected void disabled(Joystick left, Joystick right, Joystick operator) {
	}
	
	protected void test(Joystick left, Joystick right, Joystick operator) {
	}
	
	
	
	public WarriorDriveHelper(DriveBaseSubsystem driveBaseSubsystem, IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem, WinchSubsystem winchSubsystem, CameraSubsystem cameraSubsystem, PortcullisLiftSubsystem portcullisLiftSubsystem, HookerSubsystem hookerSubsystem) {
		super(driveBaseSubsystem, intakeSubsystem, shooterSubsystem, winchSubsystem, cameraSubsystem, portcullisLiftSubsystem);
	
		this._driveBaseSubsystem = driveBaseSubsystem;
		this._intakeSubsystem = intakeSubsystem;
		this._shooterSubsystem = shooterSubsystem;
		this._winchSubsystem = winchSubsystem;
		this._cameraSubsystem = cameraSubsystem;
		this._portcullisLiftSubsystem = portcullisLiftSubsystem;
		this._hookerSubsystem = hookerSubsystem;
	}



}
