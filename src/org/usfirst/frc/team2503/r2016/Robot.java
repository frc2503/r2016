package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	public Robot() {
		Logger.addPrintStream("main", new LoggerPrintStream(System.out));
		Logger.addPrintStream("error", new LoggerPrintStream(System.err));
		Logger.addPrintStream("warning", new LoggerPrintStream(System.err));
		Logger.addPrintStream("debug", new LoggerPrintStream(System.out));
		Logger.addPrintStream("information", new LoggerPrintStream(System.out));

		Logger.println("main", "[Robot] Starting... Version '" + Constants.VERSION + "'");
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}

}
