package org.usfirst.frc.team2503.lib.trajectory;

import org.usfirst.frc.team2503.lib.util.WarriorMath;
import org.usfirst.frc.team2503.r2016.debug.Logger;

public class Spline {

	public static class Type {

		private final String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public final String toString() {
			return this.name;
		}
		
	}
	
	// Cubic spline where positions and first derivatives (angle) constraints will
	// be met but second derivatives may be discontinuous.
	public static final Type CubicHermite = new Type("CubicHermite");
	
	// Quintic spline where positions and first derivatives (angle) constraints
	// will be met, and all second derivatives at knots = 0.
	public static final Type QuinticHermite = new Type("QuinticHermite");
	
	Type type;
	
	double a; // ax^5
	double b; //  + bx^4
	double c; //  + cx^3
	double d; //  + dx^2
	double e; //  + ex
	double f = 0.0; // + f // f is always 0 for the spline formulation we support.

	// The offset from the world frame to the spline frame.
	// Add these to the output of the spline to obtain world coordinates.
	double x_offset;
	double y_offset;
	double knot_distance;
	double theta_offset;
	double arc_length;
	
	Spline() {
		arc_length = -1;
	}
	
	private static boolean almostEqual(double x, double y) {
		return Math.abs(x - y) < 1e-6d;
	}
	
	public static boolean reticulateSplines(Waypoint start, Waypoint goal, Spline result, Type type) {
		return reticulateSplines(start.x, start.y, start.theta, goal.x, goal.y, goal.theta, result, type);
	}
	
	public double valueAt(double percentage) {
		percentage = WarriorMath.gate(0.0, percentage, 1.0);
		double x_hat = percentage * knot_distance;
		double y_hat = (1 * a * x_hat * x_hat * x_hat * x_hat * x_hat) +
				(1 * b * x_hat * x_hat * x_hat * x_hat) +
				(1 * c * x_hat * x_hat * x_hat) +
				(1 * d * x_hat * x_hat) + 
				(1 * e * x_hat);
		
		double cos_theta = Math.cos(theta_offset);
		double sin_theta = Math.sin(theta_offset);
		
		double value = x_hat * sin_theta + y_hat * cos_theta + y_offset;
		return value;
	}
	
	private double derivativeAt(double percentage) {
		percentage = WarriorMath.gate(0.0, percentage, 1.0);
		
		double x_hat = percentage * knot_distance;
		double yp_hat = (5 * a * x_hat * x_hat * x_hat * x_hat) +
				(4 * b * x_hat * x_hat * x_hat) +
				(3 * c * x_hat * x_hat) +
				(2 * d * x_hat) + 
				(1 * e);
		
		return yp_hat;
	}
	
	private double secondDerivativeAt(double percentage) {
		percentage = WarriorMath.gate(0.0, percentage, 1.0);
		
		double x_hat = percentage * knot_distance;
		double ypp_hat = (20 * a * x_hat * x_hat * x_hat) +
				(12 * b * x_hat * x_hat) +
				(6 * c * x_hat) +
				(1 * d);
		
		return ypp_hat;
	}
	
	public double angleAt(double percentage) {
		double angle = WarriorMath.boundAngle0to2PiRadians(Math.atan(derivativeAt(percentage)) + theta_offset);
		return angle;
	}
	
	public double angleChangeAt(double percentage) {
		return WarriorMath.boundAngleNegPiToPiRadians(Math.atan(secondDerivativeAt(percentage)));
	}
	
	public String toString() {
		return "a = " + a + "; b = " + b + "; c = " + c + "; d = " + d + "; e = " + e;
	}
	
	public static boolean reticulateSplines(double x0, double y0, double theta0, double x1, double y1, double theta1, Spline result, Type type) {
		Logger.println("info", "Reticulating splines...");
		
		// Ensure that result type equals the current type
		result.type = type;
		
		result.x_offset = x0;
		result.y_offset = y0;
		double x1_hat = Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));

		if(x1_hat == 0) {
			return false;
		}
		
		result.knot_distance = x1_hat;
		result.theta_offset  = Math.atan2(y1 - y0, x1 - x0);
		double theta0_hat = WarriorMath.getDifferenceInAngleRadians(result.theta_offset, theta0);
		double theta1_hat = WarriorMath.getDifferenceInAngleRadians(result.theta_offset, theta1);
		
		// We cannot handle vertical slopes in our rotated, translated basis.
		// This would mean the user wants to end up 90 degrees off of the straight
		// line between p0 and p1.
		if(almostEqual(Math.abs(theta0_hat), Math.PI / 2) ||
		   almostEqual(Math.abs(theta1_hat), Math.PI / 2)) {
			return false;
		}
		
		// We also cannot handle the case that the end angle is facing towards the start angle (total turn > 90 degrees)
		if(Math.abs(WarriorMath.getDifferenceInAngleRadians(theta0_hat, theta1_hat)) >= Math.PI / 2) {
			return false;
		}
		
		double yp0_hat = Math.tan(theta0_hat);
		double yp1_hat = Math.tan(theta1_hat);
		
		if(type == CubicHermite) {
			// Calculate the cubic spline coefficients;
			
			result.a = 0;
			result.b = 0;
			result.c = (yp1_hat + yp0_hat) / (x1_hat * x1_hat);
			result.d = -(2 * yp0_hat + yp1_hat) / x1_hat;
			result.e = yp0_hat;
		} else if (type == QuinticHermite) {
			result.a = -(3 * (yp0_hat + yp1_hat)) / (x1_hat * x1_hat * x1_hat * x1_hat);
			result.b = (8 * yp0_hat + 7 * yp1_hat) / (x1_hat * x1_hat * x1_hat);
			result.c = -(6 * yp0_hat + 4 * yp1_hat) / (x1_hat * x1_hat);
			result.d = 0;
			result.e = yp0_hat;
		}
		
		return true;
	}
	
	public double calculateLength() {
		if(arc_length >= 0) {
			return arc_length;
		}
		
		// I don't wanna touch doing actual integration, so
		// instead we will just do stupid things.
		final int samples = (int) 1E6;
		
		double arc_length = 0;
		double t, dydt;
		double integrand, last_integrand = Math.sqrt(1 + derivativeAt(0) * derivativeAt(0)) / samples;
		
		for(int i = 1; i <= samples; i += 1) {
			t = ((double) i) / samples;
			dydt = derivativeAt(t);
			integrand = Math.sqrt(1 + dydt * dydt) / samples;
			arc_length += (integrand + last_integrand) / 2;
			last_integrand = integrand;
		}
		
		arc_length = knot_distance * arc_length;
		return arc_length;
	}
	
}
