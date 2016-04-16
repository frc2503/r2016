package org.usfirst.frc.team2503.lib.trajectory;

import org.usfirst.frc.team2503.lib.util.WarriorMath;

public class BezierCurve {

	public Point[] points;

	// TODO: Document
	public double x(double time, double timeMax) {
		return 0.0d;
	}

	// TODO: Document
	private int n() {
		return this.points.length - 1;
	}

	public BezierCurve(Point... points) {
		this.points = points;
	}

}
