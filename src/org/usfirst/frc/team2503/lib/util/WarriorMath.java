package org.usfirst.frc.team2503.lib.util;

public class WarriorMath {

	static final double squareRootOf2Plus1 = 2.414213562373095048802e0d;
	static final double squareRootOf2Minus1 = .414213562373095048802e0d;
	static final double p4 = .161536412982230228262e2d;
	static final double p3 = .26842548195503973794141e3d;
	static final double p2 = .11530293515404850115428136e4d;
	static final double p1 = .178040631643319697105464587e4d;
	static final double p0 = .89678597403663861959987488e3d;
	static final double q4 = .5895697050844462222791e2d;
	static final double q3 = .536265374031215315104235e3d;
	static final double q2 = .16667838148816337184521798e4d;
	static final double q1 = .207933497444540981287275926e4d;
	static final double q0 = .89678597403663861962481162e3d;
	static final double PiOver2 = 1.5707963267948966135e0d;
	static final double nan	 = (0.0 / 0.0);

	private static double mxatan(double arg) {
		double argsq, value;

		argsq = arg * arg;
		value = ((((p4 * argsq + p3) * argsq + p2) * argsq + p1) * argsq + p0);
		value = value / (((((argsq + q4) * argsq + q3) * argsq + q2) * argsq + q1) * argsq + q0);
		return value * arg;
	}

	private static double msatan(double arg) {
		if (arg < squareRootOf2Minus1) {
			return mxatan(arg);
		}

		if (arg > squareRootOf2Plus1) {
			return PiOver2 - mxatan(1 / arg);
		}

		return PiOver2 / 2 + mxatan((arg - 1) / (arg + 1));
	}

	public static double atan(double arg) {
		if (arg > 0) {
			return msatan(arg);
		}

		return -msatan(-arg);
	}

	public static double atan2(double arg1, double arg2) {
		if (arg1 + arg2 == arg1) {
			if (arg1 >= 0) {
				return PiOver2;
			}

			return -PiOver2;
		}

		arg1 = atan(arg1 / arg2);

		if (arg2 < 0) {
			if (arg1 <= 0) {
				return arg1 + Math.PI;
			}

			return arg1 - Math.PI;
		}

		return arg1;
	}

	public static double asin(double arg) {
		double temp;
		int sign;

		sign = 0;

		if (arg < 0) {
			arg = -arg;
			sign++;
		}

		if (arg > 1) {
			return nan;
		}

		temp = Math.sqrt(1 - arg * arg);

		if (arg > 0.7) {
			temp = PiOver2 - atan(temp / arg);
		} else {
			temp = atan(arg / temp);
		}

		if (sign > 0) {
			temp = -temp;
		}

		return temp;
	}

	public static double acos(double arg) {
		if (arg > 1 || arg < -1) {
			return nan;
		}

		return PiOver2 - asin(arg);
	}

	public static double getDifferenceInAngleRadians(double from, double to) {
		return boundAngleNegPiToPiRadians(to - from);
	}


	public static double getDifferenceInAngleDegrees(double from, double to) {
		return boundAngleNeg180to180Degrees(to - from);
	}

	public static double boundAngle0to360Degrees(double angle) {
		while (angle >= 360.0) {
			angle -= 360.0;
		}

		while (angle < 0.0) {
			angle += 360.0;
		}

		return angle;
	}

	public static double boundAngleNeg180to180Degrees(double angle) {
		while (angle >= 180.0) {
			angle -= 360.0;
		}

		while (angle < -180.0) {
			angle += 360.0;
		}

		return angle;
	}

	public static double boundAngle0to2PiRadians(double angle) {
		while (angle >= 2.0 * Math.PI) {
			angle -= 2.0 * Math.PI;
		}

		while (angle < 0.0) {
			angle += 2.0 * Math.PI;
		}

		return angle;
	}

	public static double boundAngleNegPiToPiRadians(double angle) {
		while (angle >= Math.PI) {
			angle -= 2.0 * Math.PI;
		}

		while (angle < -Math.PI) {
			angle += 2.0 * Math.PI;
		}

		return angle;
	}

	public static double radiansToDegrees(double radians) {
		return (180.0d / Math.PI) * radians;
	}

	public static double degreesToRadians(double degrees) {
		return (Math.PI / 180.0d) * degrees;
	}

	public static double gate(double min, double value, double max) {
		return Math.max(min, Math.min(max, value));
	}

	public WarriorMath() {
	}
}
