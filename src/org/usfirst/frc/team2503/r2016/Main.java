package org.usfirst.frc.team2503.r2016;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {
	
	public static void main(String[] args) {
		

		File csvDataFile = new File("data.csv");
		
		Logger.addPrintStream("ayy", new LoggerPrintStream(System.out));
		Logger.addPrintStream("ayy", new LoggerPrintStream(System.err));
		Logger.addPrintStream("lmao", new LoggerPrintStream(System.err));
		Logger.addPrintStream("data", new LoggerPrintStream(System.out));

		try {
			Logger.addPrintStream("data-raw", new LoggerPrintStream(csvDataFile));
		} catch(FileNotFoundException e) {
			try {
				csvDataFile.createNewFile();
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		Logger.println("ayy", "foo");
		Logger.println("lmao", "test");
		
		{
			Logger.println("data", 1.0 + 2.0);
			Logger.println("data-raw", 1.0 + 2.0, false);
		}
		
	}
	
}
