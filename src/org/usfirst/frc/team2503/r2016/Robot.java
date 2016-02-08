package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.data.DataServer;
import org.usfirst.frc.team2503.r2016.input.vision.CameraMonitor;
import org.usfirst.frc.team2503.r2016.subsystems.DriveBase;
import org.usfirst.frc.team2503.r2016.subsystems.MainDriveBase;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {

	public Joystick gamepad;

	public DataServer configurationServer;
	public DataServer imageServer;
	public DataServer dataServer;

	public DriveBase driveBase;

	public Robot() {
		gamepad = new Joystick(0);
		driveBase = new MainDriveBase(0, 1);

		configurationServer = new DataServer(5800);
		imageServer = new DataServer(5801);
		dataServer = new DataServer(5802);

		Thread configurationServerThread = new Thread(configurationServer.getServerInstance());
		Thread imageServerThread = new Thread(imageServer.getServerInstance());
		Thread dataServerThread = new Thread(dataServer.getServerInstance());

		configurationServerThread.start();
		imageServerThread.start();
		dataServerThread.start();
	}

	public void robotInit() {
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {

		System.out.println(CameraMonitor.getImageDataURIFromDevice());

	}

	public void autonomousInit() {
	}

	public void autonomousPeriodic() {
	}

	public void teleopInit() {
	}

	public void teleopPeriodic() {
		driveBase.drive(gamepad.getRawAxis(2), gamepad.getRawAxis(3));
	}

	public void testInit() {
	}

	public void testPeriodic() {
	}

}
