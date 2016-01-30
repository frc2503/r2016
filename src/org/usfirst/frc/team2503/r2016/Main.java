package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.data.DataServer;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {

	public static void main(String[] args) {

		DataServer wc = new DataServer();

		Thread t = new Thread(wc.getServerInstance());

		System.out.println("[main] Starting Thread...");
		t.start();
		System.out.println("[main] Thread started!");

		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
