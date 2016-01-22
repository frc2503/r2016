package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.input.NullInput;
import org.usfirst.frc.team2503.r2016.input.NullMonitor;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {

	public static void main(String[] args) {
		NullInput nullInput = new NullInput();
		NullMonitor nullMonitor = new NullMonitor(nullInput);
		
		Thread thread = new Thread(nullMonitor);
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
