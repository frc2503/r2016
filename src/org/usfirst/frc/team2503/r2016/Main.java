package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {
	
	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "true");
		
		Logger.addPrintStream(new LoggerPrintStream(System.out));
		
		Logger.println("foo");
	}
	
}
