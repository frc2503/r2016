package org.usfirst.frc.team2503.r2016;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Constants {
	
	public static final String defaultHostname = "0.0.0.0";
	
	public static final SpeedController leftTrackSpeedController = new Talon(0);
	public static final SpeedController rightTrackSpeedController = new Talon(1);
	public static final SpeedController shooterSpeedController = new Spark(2);
	public static final SpeedController winchSpeedController = new Talon(3);
	public static final SpeedController hookerSpeedController = new Talon(4);
	public static final SpeedController intakeSpeedController = new Talon(6);
	
	public static final Servo cameraVerticalRotationServo = new Servo(7);
	public static final Servo cameraHorizontalRotationServo = new Servo(5);
	
	public static final Compressor compressor = new Compressor(1);
	public static final DoubleSolenoid lift = new DoubleSolenoid(1, 0, 1);
	
	public static final DigitalInput leftTrackEncoderAChannel = new DigitalInput(0);
	public static final DigitalInput leftTrackEncoderBChannel = new DigitalInput(1);
	public static final DigitalInput rightTrackEncoderAChannel = new DigitalInput(2);
	public static final DigitalInput rightTrackEncoderBChannel = new DigitalInput(3);
	public static final DigitalInput intakeEncoderAChannel = new DigitalInput(4);
	public static final DigitalInput intakeEncoderBChannel = new DigitalInput(5);
	public static final DigitalInput intakeLimitSwitch = new DigitalInput(6);
	public static final DigitalInput hookerLimitSwitch = new DigitalInput(7);
	
	public static final Relay lightRelay = new Relay(0);
	public static final Relay indicatorRelay = new Relay(1);
	
}
