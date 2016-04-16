package org.usfirst.frc.team2503.lib.trajectory;

public class BezierCurve {

	public Point[] points;
	
	public double x(double time, double timeMax) {
		return 0.0d;
	}
	
	public int n() {
		return this.points.length - 1;
	}
	
	public BezierCurve(Point... points) {
		this.points = points;
	}
	
}
