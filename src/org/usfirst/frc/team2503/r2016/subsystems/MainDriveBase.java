package org.usfirst.frc.team2503.r2016.subsystems;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.components.RhinoTrack;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class MainDriveBase implements DriveBase {

	public JSONObject DATA;
	
	public RhinoTrack leftTrack;
	public RhinoTrack rightTrack;
	
	public void drive(double left, double right) {
		this.leftTrack.speedController.set(left);
		this.rightTrack.speedController.set(right);
	}

	public MainDriveBase(JSONObject DATA, RhinoTrack leftTrack, RhinoTrack rightTrack) {
		this.DATA = DATA;
		
		this.leftTrack = leftTrack;
		this.rightTrack = rightTrack;
	}
	
	public MainDriveBase(JSONObject DATA, Talon leftTalon, Encoder leftEncoder, Talon rightTalon, Encoder rightEncoder) {
		this.DATA = DATA;
		
		this.leftTrack = new RhinoTrack(leftTalon, leftEncoder);
		this.rightTrack = new RhinoTrack(rightTalon, rightEncoder);
	}
	
	public MainDriveBase(JSONObject DATA, int leftTalonChannel, int rightTalonChannel) {
		this.DATA = DATA;
		
		Talon leftTalon = new Talon(leftTalonChannel);
		Talon rightTalon = new Talon(rightTalonChannel);
		
		this.leftTrack = new RhinoTrack(leftTalon, null);
		this.rightTrack = new RhinoTrack(rightTalon, null);
	}
	
	public MainDriveBase(JSONObject DATA, int leftTrackTalonChannel, Encoder leftEncoder, int rightTrackTalonChannel, Encoder rightEncoder) {
		this.DATA = DATA;
		
		Talon leftTalon = new Talon(leftTrackTalonChannel);
		Talon rightTalon = new Talon(rightTrackTalonChannel);
		
		this.leftTrack = new RhinoTrack(leftTalon, leftEncoder);
		this.rightTrack = new RhinoTrack(rightTalon, rightEncoder);
	}
	
	public MainDriveBase(JSONObject DATA, int leftTrackTalonChannel, int leftEncoderAChannel, int leftEncoderBChannel, int rightTrackTalonChannel, int rightEncoderAChannel, int rightEncoderBChannel) {
		this.DATA = DATA;
		
		Talon leftTalon = new Talon(leftTrackTalonChannel);
		Talon rightTalon = new Talon(rightTrackTalonChannel);
		
		Encoder leftEncoder = new Encoder(leftEncoderAChannel, leftEncoderBChannel);
		Encoder rightEncoder = new Encoder(rightEncoderAChannel, rightEncoderBChannel);
		
		this.leftTrack = new RhinoTrack(leftTalon, leftEncoder);
		this.rightTrack = new RhinoTrack(rightTalon, rightEncoder);
	}

	public void drive() {
		drive(this.DATA.getJSONObject("driveBase").getDouble("left"), this.DATA.getJSONObject("driveBase").getDouble("right"));
	}
	
	public void tick() {
		drive();
	}
	
}
