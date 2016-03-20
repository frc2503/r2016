package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.component.CameraMount.CameraLights;
import org.usfirst.frc.team2503.r2016.component.CameraMount;
import org.usfirst.frc.team2503.r2016.component.Hooker;
import org.usfirst.frc.team2503.r2016.component.Intake;
import org.usfirst.frc.team2503.r2016.component.RhinoTrack;
import org.usfirst.frc.team2503.r2016.component.Shooter;
import org.usfirst.frc.team2503.r2016.component.Winch;
import org.usfirst.frc.team2503.r2016.subsystem.DriveBase;
import org.usfirst.frc.team2503.r2016.subsystem.MainDriveBase;
import org.usfirst.frc.team2503.r2016.subsystem.PneumaticsSubsystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class MainRobotMap {

	private final SpeedController leftTrackSpeedController = new Talon(0);
	private final SpeedController rightTrackSpeedController = new Talon(1);
	private final SpeedController shooterSpeedController = new Spark(2);
	private final SpeedController winchSpeedController = new Talon(3);
	private final SpeedController hookerSpeedController = new Talon(4);
	private final SpeedController intakeSpeedController = new Talon(6);

	private final Servo cameraVerticalRotationServo = new Servo(7);
	private final Servo cameraHorizontalRotationServo = new Servo(5);

	private final Compressor compressor = new Compressor(1);
	private final DoubleSolenoid lift = new DoubleSolenoid(1, 0, 1);

	private final DigitalInput leftTrackEncoderAChannel = new DigitalInput(0);
	private final DigitalInput leftTrackEncoderBChannel = new DigitalInput(1);
	private final DigitalInput rightTrackEncoderAChannel = new DigitalInput(2);
	private final DigitalInput rightTrackEncoderBChannel = new DigitalInput(3);
	private final DigitalInput hookerEncoderAChannel = new DigitalInput(4);
	private final DigitalInput hookerEncoderBChannel = new DigitalInput(5);
	private final DigitalInput intakeLimitSwitch = new DigitalInput(6);
	private final DigitalInput hookerLimitSwitch = new DigitalInput(7);

	private final CameraLights cameraLights = new CameraLights(0);
	public final Relay indicatorRelay = new Relay(1);

	private final Encoder leftTrackEncoder = new Encoder(this.leftTrackEncoderAChannel, this.leftTrackEncoderBChannel);
	private final Encoder rightTrackEncoder = new Encoder(this.rightTrackEncoderAChannel, this.rightTrackEncoderBChannel);
	private final Encoder hookerEncoder = new Encoder(this.hookerEncoderAChannel, this.hookerEncoderBChannel);

	public final Winch winch = new Winch(this.winchSpeedController);
	public final Hooker hooker = new Hooker(this.hookerSpeedController, this.hookerEncoder, this.hookerLimitSwitch);
	public final Shooter shooter = new Shooter(this.shooterSpeedController);
	public final Intake intake = new Intake(this.intakeSpeedController, this.intakeLimitSwitch);
	public final CameraMount cameraMount = new CameraMount(this.cameraHorizontalRotationServo, this.cameraVerticalRotationServo, this.cameraLights);

	public final RhinoTrack leftTrack = new RhinoTrack(this.leftTrackSpeedController);
	public final RhinoTrack rightTrack = new RhinoTrack(this.rightTrackSpeedController);

	public final DriveBase driveBase = new MainDriveBase(this.leftTrack, this.leftTrackEncoder, this.rightTrack, this.rightTrackEncoder);
	public final PneumaticsSubsystem pneumaticsSubsystem = new PneumaticsSubsystem(this.compressor, this.lift);
	
	public MainRobotMap() {
		this.leftTrack.setInverted(false);
		this.rightTrack.setInverted(true);
		this.winch.setInverted(true);
		this.hooker.setInverted(true);
		this.shooter.setInverted(true);
		this.intake.setInverted(true);

		this.cameraLights.setDirection(Relay.Direction.kForward);
		this.indicatorRelay.setDirection(Relay.Direction.kForward);

		this.compressor.setClosedLoopControl(true);
		this.compressor.start();
	}

}
