package org.usfirst.frc.team2503.lib.trajectory;

public class Segment {

	public double position;
	public double velocity;
	public double acceleration;
	public double jerk;
	public double heading;
	public double deltaTheta;
	public double x;
	public double y;
	
	public Segment() {
	}
	
	public Segment(double position, double velocity, double acceleration, double jerk, double heading, double deltaTheta, double x, double y) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.jerk = jerk;
		this.heading = heading;
		this.deltaTheta = deltaTheta;
		this.x = x;
		this.y = y;
	}
	
	public Segment(Segment toCopy) {
		this.position = toCopy.position;
		this.velocity = toCopy.velocity;
		this.acceleration = toCopy.acceleration;
		this.jerk = toCopy.jerk;
		this.heading = toCopy.heading;
		this.deltaTheta = toCopy.deltaTheta;
		this.x = toCopy.x;
		this.y = toCopy.y;
	}
	
}
