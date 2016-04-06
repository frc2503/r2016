package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.input.sensor.LimitSwitch;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Hardware {

	public static final SpeedController leftTrackSpeedController = new Talon(0);
	public static final SpeedController rightTrackSpeedController = new Talon(1);
	public static final SpeedController shooterSpeedController = new Spark(2);
	public static final SpeedController winchSpeedController = new Talon(3);
	private static final SpeedController hookerSpeedController = new Talon(4);
	public static final SpeedController intakeSpeedController = new Talon(6);

	private static final Servo cameraHorizontalRotationServo = new Servo(5);
	private static final Servo cameraVerticalRotationServo = new Servo(7);

	/**
	 * The pneumatics control module is sometimes assigned a non-standard
	 * CAN bus ID number.  This variable should be used to explicitly specify where
	 * the PCM is to be found.
	 *
	 * Additional pneumatics WPIlib objects should use this variable for
	 * initialization.
	 */
	private static final int PCMCANChannel = 1;

	private static final Compressor compressor = new Compressor(PCMCANChannel);

	private static final DoubleSolenoid lift = new DoubleSolenoid(PCMCANChannel, 0, 1);

	private static final DigitalInput leftTrackEncoderAChannel = new DigitalInput(0);
	private static final DigitalInput leftTrackEncoderBChannel = new DigitalInput(1);
	private static final DigitalInput rightTrackEncoderAChannel = new DigitalInput(2);
	private static final DigitalInput rightTrackEncoderBChannel = new DigitalInput(3);
	private static final DigitalInput hookerEncoderAChannel = new DigitalInput(4);
	private static final DigitalInput hookerEncoderBChannel = new DigitalInput(5);

	public static final LimitSwitch intakeLimitSwitch = new LimitSwitch(6);
	private static final LimitSwitch hookerLimitSwitch = new LimitSwitch(7);

	private static final Relay cameraLightsRelay = new Relay(0);
	private static final Relay intakeIndicatorRelay = new Relay(1);

	private static final Encoder leftTrackEncoder = new Encoder(leftTrackEncoderAChannel, leftTrackEncoderBChannel);
	private static final Encoder rightTrackEncoder = new Encoder(rightTrackEncoderAChannel, rightTrackEncoderBChannel);
	private static final Encoder hookerEncoder = new Encoder(hookerEncoderAChannel, hookerEncoderBChannel);

	static {
		cameraLightsRelay.setDirection(Relay.Direction.kForward);
		intakeIndicatorRelay.setDirection(Relay.Direction.kForward);

		leftTrackSpeedController.setInverted(false);
		rightTrackSpeedController.setInverted(true);

		shooterSpeedController.setInverted(true);
		winchSpeedController.setInverted(true);
		hookerSpeedController.setInverted(true);
		intakeSpeedController.setInverted(true);

		leftTrackEncoder.reset();
		rightTrackEncoder.reset();
		hookerEncoder.reset();

		compressor.setClosedLoopControl(true);
		compressor.start();
	}

}
