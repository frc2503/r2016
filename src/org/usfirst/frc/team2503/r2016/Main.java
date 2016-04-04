package org.usfirst.frc.team2503.r2016;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {
	
	public static double[] VALUE_RANGE = {0.0, 1.0};
	public static double[] DEGREE_RANGE = {-91.0, 87.0};

	public static double MAPPING_SLOPE = (DEGREE_RANGE[1] - DEGREE_RANGE[0]) / (VALUE_RANGE[1] - VALUE_RANGE[0]);
	
	public static double valueToDegrees(double value) {
		return DEGREE_RANGE[0] + MAPPING_SLOPE * (value - VALUE_RANGE[0]);
	}
	
	public static double degreesToValue(double degrees) {
		return VALUE_RANGE[0] + (1.0 / MAPPING_SLOPE) * (degrees - DEGREE_RANGE[0]);
	}
	
	public static void main(String[] args) {
		for(double d = 0.0; d <= 1.0; d += 0.125) {	
			double degrees = valueToDegrees(d);
			System.out.println(degrees + " - " + degreesToValue(degrees));
		}
	}
	
}
