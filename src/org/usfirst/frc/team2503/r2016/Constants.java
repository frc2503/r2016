package org.usfirst.frc.team2503.r2016;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Constants {
	
	public static final SpeedController leftTrackSpeedController = new Talon(0);
	public static final SpeedController rightTrackSpeedController = new Talon(1);
	public static final SpeedController shooterSpeedController = new Spark(2);
	public static final SpeedController winchSpeedController = new Talon(3);
	public static final SpeedController hookerSpeedController = new Talon(4);
	
	public static final DigitalInput leftTrackEncoderAChannel = new DigitalInput(0);
	public static final DigitalInput leftTrackEncoderBChannel = new DigitalInput(1);
	public static final DigitalInput rightTrackEncoderAChannel = new DigitalInput(2);
	public static final DigitalInput rightTrackEncoderBChannel = new DigitalInput(3);
	public static final DigitalInput intakeDigitalInput = new DigitalInput(4);
	
}
