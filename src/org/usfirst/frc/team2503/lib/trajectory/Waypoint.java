package org.usfirst.frc.team2503.lib.trajectory;

public class Waypoint {

	public double x;
	public double y;
	public double theta;
	
	public Waypoint(double x, double y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
	
	public Waypoint(Waypoint toCopy) {
		this.x = toCopy.x;
		this.y = toCopy.y;
		this.theta = toCopy.theta;
	}
	
}
