package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.lib.util.WarriorMath;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {
	
	public static void main(String[] args) {
		for(double d = 0.0; d <= 1.0; d += 0.125) {	
			double degrees = WarriorMath.map(-90.0, 90.0, 87.5, 0.0, 1.0);
			System.out.println(degrees);
		}
	}
	
}
