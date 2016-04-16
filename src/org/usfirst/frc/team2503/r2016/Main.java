package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.lib.trajectory.BezierCurve;
import org.usfirst.frc.team2503.lib.trajectory.Point;
import org.usfirst.frc.team2503.lib.util.WarriorMath;
import org.usfirst.frc.team2503.r2016.control.Tickable;
import org.usfirst.frc.team2503.r2016.control.Ticker;
import org.usfirst.frc.team2503.r2016.debug.CSVDataLogger;
import org.usfirst.frc.team2503.r2016.debug.CSVDataLogger.Entry;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.jruby.embed.ScriptingContainer;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {

	public static class Roomba implements Tickable {
		public void tick() {
			System.out.println("[Roomba] Ticking...");
		}
	}

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		System.out.println("[main] Testing Ticker...");

		{
			Thread t = new Thread(new Ticker(new Roomba(), WarriorMath.frequencyToPeriodMilliseconds(50.0d)));
			t.start();

			Thread.sleep(5000L);

			t.interrupt();
		}

		System.out.println("[main] Testing WarriorMath.map()...");

		{
			for(double d = -90.0; d <= 90.0; d += 1.0) {
				double degrees = WarriorMath.map(-90.0, 90.0, d, 0.0, 1.0);
				System.out.println(degrees);
			}
		}

		System.out.println("[main] Testing WarriorMath.nCr()...");

		{
			for(int i = 0; i < 4; i += 1) {
				System.out.println(WarriorMath.nCr(3, i));
			}
		}

		System.out.println("[main] Testing jruby...");

		{
			System.out.println("[jruby] Booting...");

			ScriptingContainer container = new ScriptingContainer();
			container.runScriptlet("puts 'hello world'");
			container.runScriptlet("require 'java'; p org.usfirst.frc.team2503.lib.util.WarriorMath.map(-90.0, 90.0, 45.0, 0.0, 1.0)");

			System.out.println("[jruby] Shutting down...");
		}

		System.out.println("[main] Testing CSVDataLogger...");

		{
			FileOutputStream outputStream = new FileOutputStream(Constants.USB_BASE_DIRECTORY_FILENAME + "dank.csv", true);
			PrintWriter writer = new PrintWriter(outputStream);
			CSVDataLogger logger = new CSVDataLogger(writer);

			for(int i = 0; i < 1000; i += 1) {
				Entry entry = new Entry(Math.random(), Math.random());
				logger.log(entry);
			}
		}

		System.out.print("[main] Testing BezierCurve...");

		{
			Point p0 = new Point(0.0d, 0.0d);
			Point p1 = new Point(1.0d, 0.0d);
			Point p2 = new Point(1.0d, 2.0d);
			Point p3 = new Point(2.0d, 2.0d);

			BezierCurve bezierCurve = new BezierCurve(p0, p1, p2, p3);

			FileOutputStream outputStream = new FileOutputStream(Constants.USB_BASE_DIRECTORY_FILENAME + "bezierCurve.csv", true);
			PrintWriter writer = new PrintWriter(outputStream);
			CSVDataLogger logger = new CSVDataLogger(writer);

			double timeMax = 30.0d;
			double timeStep = WarriorMath.frequencyToPeriodMilliseconds(2.0d);

			Entry headers = new Entry("time (s)", "x", "y", "dy/dx", "d2y/dx2", "dtheta/dt");
			logger.log(headers);

			for(double time = 0.0d; time <= timeMax; time += (timeStep / 1000.0d)) {
				double x = bezierCurve.x(time, timeMax);
				double y = bezierCurve.y(time, timeMax);
				double dydx = bezierCurve.dydx(time, timeMax, (timeStep / 2000.0d));
				double d2ydx2 = bezierCurve.d2ydx2(time, timeMax, (timeStep / 2000.0d));
				double dthetadt = Math.atan(d2ydx2) / Math.PI;

				Entry entry = new Entry(time, x, y, dydx, d2ydx2, dthetadt);
				logger.log(entry);

				System.out.println("at time " + time + ": (" + x + ", " + y + ") " + dydx + " " + d2ydx2 + " " + dthetadt);
			}
		}
	}

}
