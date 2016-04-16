package org.usfirst.frc.team2503.lib.trajectory;

import org.usfirst.frc.team2503.lib.util.WarriorMath;

public class BezierCurve {

	public Point[] points;

	// TODO: Document
	public double x(double time, double timeMax) {
		return 0.0d;

	// TODO: Document
	private double coefficient(int i) {
		return this.binomialCoefficient(this.n(), i);
	}
	}

	// TODO: Document
	private int n() {
		return this.points.length - 1;
	}

	// TODO: Document
	private int binomialCoefficient(int n, int i) {
		return (int) WarriorMath.nCr(n, i);
	}

	// TODO: Document
	public BezierCurve(Point... points) {
		this.points = points;
	}

}
