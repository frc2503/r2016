package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.subsystem.CameraSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.CameraSubsystem.CameraSubsystemDataKey;
import org.usfirst.frc.team2503.r2016.subsystem.DriveBaseSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.HookerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.IntakeSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.IntakeSubsystem.IntakeSubsystemMode;
import org.usfirst.frc.team2503.r2016.subsystem.PortcullisLiftSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.PortcullisLiftSubsystem.PortcullisLiftSubsystemMode;
import org.usfirst.frc.team2503.r2016.subsystem.ShooterSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.WinchSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.WinchSubsystem.WinchSubsystemMode;
import org.usfirst.frc.team2503.lib.util.WarriorMath;
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
	
	private double horizontalRotationDegrees = 90.0d;
	private double verticalRotationDegrees = 0.0d;
	
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
	
	@SuppressWarnings("unused")
	protected void teleoperated(Joystick _left, Joystick _right, Joystick _operator) {
		MadCatzV1Joystick left = (MadCatzV1Joystick) _left;
		MadCatzV1Joystick right = (MadCatzV1Joystick) _right;
		LogitechF310Gamepad operator = (LogitechF310Gamepad) _operator;
		
		{
			if(operator.intakeOutputModeButton.get()) {
				this._intakeSubsystem.setMode(IntakeSubsystemMode.OUTPUTTING);
			} else if(operator.intakeIntakeModeButton.get()) {
				this._intakeSubsystem.setMode(IntakeSubsystemMode.INTAKING);
			} else if(operator.intakeFireModeButton.get()) {
				this._intakeSubsystem.setMode(IntakeSubsystemMode.FIRING);
			} else {
				this._intakeSubsystem.setMode(IntakeSubsystemMode.STOPPED);
			}
		}
		
		{
			if(right.liftRaiseModeButton.get())
				this._portcullisLiftSubsystem.setMode(PortcullisLiftSubsystemMode.RAISING);
			else if(right.liftLowerModeButton.get())
				this._portcullisLiftSubsystem.setMode(PortcullisLiftSubsystemMode.LOWERING);
			else
				this._portcullisLiftSubsystem.setMode(PortcullisLiftSubsystemMode.STOPPED);
		}
		
		{
			double leftPower = -left.yAxis.get();
			double rightPower = -right.yAxis.get();
			
			// Crude square input profile
			// TODO: Velocity mapping
			leftPower *= Math.abs(leftPower);
			rightPower *= Math.abs(rightPower);
			
			this._driveBaseSubsystem.drive(leftPower, rightPower);
		}
		
		{
			this._hookerSubsystem.setPower(operator.leftYAxis.get());
		}
		
		{
			this._shooterSubsystem.setPower(operator.rightTriggerAxis.get());
		}

		{
			if(true) {
				double winchAxisValue = operator.leftTriggerAxis.get();
				
				if(operator.winchReleaseButton.get()) {
					this._winchSubsystem.setMode(WinchSubsystemMode.LOWERING);
				} else {
					if(winchAxisValue > 0.5000d) {
						this._winchSubsystem.setMode(WinchSubsystemMode.WINCHING);
					} else if(winchAxisValue > 0.2000d && winchAxisValue <= 0.5000d) {
						this._winchSubsystem.setMode(WinchSubsystemMode.SLOW_WINCHING);
					} else if(winchAxisValue <= 0.2000d) {
						this._winchSubsystem.setMode(WinchSubsystemMode.STOPPED);
					}
				}
			} else {
				double winchAxisValue = operator.leftTriggerAxis.get();

				if(winchAxisValue > 0.5000d)
					this._winchSubsystem.setMode(WinchSubsystemMode.WINCHING);
				else if(winchAxisValue > 0.2000d && winchAxisValue <= 0.5000d)
					this._winchSubsystem.setMode(WinchSubsystemMode.SLOW_WINCHING);
				else if(winchAxisValue > 0.0500d && winchAxisValue <= 0.2000d)
					this._winchSubsystem.setMode(WinchSubsystemMode.BASIC_SUSPENSION_WINCHING);
				else if(winchAxisValue < 0.0500d)
					this._winchSubsystem.setMode(WinchSubsystemMode.STOPPED);
			}
		}
		
		{
			double povAngle = (double) operator.POV.get();
			
			// Only do math if we have to.
			if(povAngle >= 0.0d) {
				// The POV returns a North-based angle which increases clockwise.
				//
				// For trigonometric math, we need to convert this to a standard
				// mathematical angle (with Easterly basis).
				double mathAngle = WarriorMath.degreesToRadians(90.0d - povAngle);
				
				// Compute and store the trigonometric values for
				// the angle
				double cos = Math.cos(mathAngle);
				double sin = Math.sin(mathAngle);
				
				// This double will get multiplied by the sine and cosine
				// of the angle.  If you want the camera to rotate faster
				// (or slower) you can modify this value.
				double factor = 1.0d;
				
				// Add to the current values the trigonometric values for
				// the angle times a factor
				this.horizontalRotationDegrees += cos * factor;
				this.verticalRotationDegrees += sin * factor;
			}
			
			// Set the values in the CameraSubsystem for horizontal and vertical rotation
			this._cameraSubsystem.setDataKey(CameraSubsystemDataKey.HORIZONTAL_ROTATION_DEGREES, this.horizontalRotationDegrees);
			this._cameraSubsystem.setDataKey(CameraSubsystemDataKey.VERTICAL_ROTATION_DEGREES, this.verticalRotationDegrees);

			// Always turn the lights on.
			this._cameraSubsystem.setDataKey(CameraSubsystemDataKey.LIGHTS, true);
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
