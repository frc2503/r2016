package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.web.WebConfiguration;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {

	public static void main(String[] args) {

		WebConfiguration wc = new WebConfiguration();

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
