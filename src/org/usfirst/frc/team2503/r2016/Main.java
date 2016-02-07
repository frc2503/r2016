package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.data.DataServer;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {
	
	public static DataServer wc = new DataServer();
	
	public static void main(String[] args) {
		
		CameraMonitorTest test = new CameraMonitorTest(wc);
		
		Thread t = new Thread(wc.getServerInstance());
		Thread t2 = new Thread(test);
		
		System.out.println("[main] Starting Thread...");
		t.start();
		t2.start();
		System.out.println("[main] Thread started!");

		try {
			t.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
