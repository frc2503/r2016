package org.usfirst.frc.team2503.r2016.subsystems;

import org.usfirst.frc.team2503.r2016.components.RhinoTrack;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class MainDriveBase implements DriveBase {

	public RhinoTrack leftTrack;
	public RhinoTrack rightTrack;
	
	public void drive(double left, double right) {
		this.leftTrack.speedController.set(left);
		this.rightTrack.speedController.set(right);
	}

	public MainDriveBase(int leftTrackTalonChannel, Encoder leftEncoder, int rightTrackTalonChannel, Encoder rightEncoder) {
		Talon leftTalon = new Talon(leftTrackTalonChannel);
		Talon rightTalon = new Talon(rightTrackTalonChannel);
		
		this.leftTrack = new RhinoTrack(leftTalon, leftEncoder);
		this.rightTrack = new RhinoTrack(rightTalon, rightEncoder);
	}
	
	public MainDriveBase(RhinoTrack leftTrack, RhinoTrack rightTrack) {
		this.leftTrack = leftTrack;
		this.rightTrack = rightTrack;
	}
	
	public MainDriveBase(int leftTrackTalonChannel, int leftEncoderAChannel, int leftEncoderBChannel, int rightTrackTalonChannel, int rightEncoderAChannel, int rightEncoderBChannel) {
		Talon leftTalon = new Talon(leftTrackTalonChannel);
		Talon rightTalon = new Talon(rightTrackTalonChannel);
	}
	
}
