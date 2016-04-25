package org.usfirst.frc.team2503.lib.trajectory;

import org.usfirst.frc.team2503.lib.util.WarriorMath;

public class BezierCurve {

	public Point[] points;

	// TODO: Document
	public double x(double time, double timeMax) {
		double x = 0.0;

		for(int i = 0; i < this.points.length; i += 1) {
			double factor = this.factor(i, time, timeMax);
			Point p_i = this.points[i];

			x += (factor) * p_i.x;
		}

		return x;
	}

	// TODO: Document
	public double y(double time, double timeMax) {
		double y = 0.0;

		for(int i = 0; i < this.points.length; i += 1) {
			double factor = this.factor(i, time, timeMax);
			Point p_i = this.points[i];

			y += (factor) * p_i.y;
		}

		return y;
	}

	// TODO: Document
	public double dxdt(double time, double timeMax, double timeIncrement) {
		double x1 = this.x(time + timeIncrement, timeMax);
		double x0 = this.x(time - timeIncrement, timeMax);

		return (x1 - x0) / (2.0d * timeIncrement);
	}

	// TODO: Document
	public double dydt(double time, double timeMax, double timeIncrement) {
		double y1 = this.y(time + timeIncrement, timeMax);
		double y0 = this.y(time - timeIncrement, timeMax);

		return (y1 - y0) / (2.0d * timeIncrement);
	}

	// TODO: Document
	public double dydx(double time, double timeMax, double timeIncrement) {
		double dydt = this.dydt(time, timeMax, timeIncrement);
		double dxdt = this.dxdt(time, timeMax, timeIncrement);

		if(dxdt == 0.0d) {
			dxdt = WarriorMath.ALMOST_ZERO;
		}

		return dydt / dxdt;
	}

	// TODO: Document
	public double d2ydx2(double time, double timeMax, double timeIncrement) {
		double dydx1 = this.dydx(time + timeIncrement, timeMax, timeIncrement);
		double dydx0 = this.dydx(time - timeIncrement, timeMax, timeIncrement);

		return (dydx1 - dydx0) / (2.0d * timeIncrement);

	}

	// TODO: Document
	private double coefficient(int i) {
		return this.binomialCoefficient(this.n(), i);
	}

	// TODO: Document
	private double timeTerm(int i, double time, double timeMax) {
		return Math.pow(time / timeMax, i);
	}

	// TODO: Document
	private double inverseTimeTerm(int i, double time, double timeMax) {
		return Math.pow(1.0 - time / timeMax, this.n() - i);
	}

	// TODO: Document
	private double factor(int i, double time, double timeMax) {
		return this.coefficient(i) * timeTerm(i, time, timeMax) * inverseTimeTerm(i, time, timeMax);
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
