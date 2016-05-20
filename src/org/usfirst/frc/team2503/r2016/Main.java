package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.lib.util.WarriorMath;
import org.usfirst.frc.team2503.r2016.control.Tickable;
import org.usfirst.frc.team2503.r2016.control.Ticker;

import java.io.FileNotFoundException;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {

	public static class Roomba implements Tickable {
		public void tick() {
			System.out.println("[Roomba] Ticking...");
			try {
				Thread.sleep((long)(1000L * (0.01d * Math.random())));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		System.out.println("[main] Testing Ticker...");

		{
			Ticker ticker = new Ticker(new Roomba(), WarriorMath.frequencyToPeriodMilliseconds(50.0d));
			Thread t = new Thread(ticker);
			t.start();

			long previousLength = ticker.getPreviousPeriodLength();
			
			while(t.isAlive()) {
				long periodLength = ticker.getPreviousPeriodLength();
				
				if(periodLength != previousLength) {
					System.out.println(periodLength);
					
					previousLength = periodLength;
				}
			}
		}
	}

}
