package org.usfirst.frc.team2503.r2016;

import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.control.ControlMode;
import org.usfirst.frc.team2503.r2016.control.DriveBaseController;
import org.usfirst.frc.team2503.r2016.data.DataServer;
import org.usfirst.frc.team2503.r2016.input.gamepad.Gamepad;
import org.usfirst.frc.team2503.r2016.input.gamepad.LogitechF310Gamepad;
import org.usfirst.frc.team2503.r2016.input.vision.CameraMonitor;
import org.usfirst.frc.team2503.r2016.subsystems.DriveBase;
import org.usfirst.frc.team2503.r2016.subsystems.MainDriveBase;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {

	public Gamepad gamepad;

	public DataServer configurationServer;
	public DataServer imageServer;
	public DataServer dataServer;
	
	public DriveBaseController driveBaseController;

	public Talon shooter;
	
	public JSONObject DATA;
	
	public DriveBase driveBase;

	public Robot() {
		DATA = new JSONObject();
		
		gamepad = new LogitechF310Gamepad(2);
		driveBase = new MainDriveBase(DATA, 0, 1);

		configurationServer = new DataServer(5800);
		imageServer = new DataServer(5801);
		dataServer = new DataServer(5802);

		Thread configurationServerThread = new Thread(configurationServer.getServerInstance());
		Thread imageServerThread = new Thread(imageServer.getServerInstance());
		Thread dataServerThread = new Thread(dataServer.getServerInstance());

		configurationServerThread.start();
		imageServerThread.start();
		dataServerThread.start();
		
		shooter = new Talon(2);
		shooter.setInverted(true);
		
		driveBaseController = new DriveBaseController(DATA, ControlMode.DEFAULT);
	}

	public void robotInit() {
	}

	public void disabledInit() {

		try {
			String key = null;
			
			while((key = DATA.keys().next()) != null) {
				DATA.remove(key);
			}
		} catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		
	}

	public void disabledPeriodic() {
		
		System.out.println(DATA.toString());
		
	}

	public void autonomousInit() {
		
		try {
			String key = null;
			
			while((key = DATA.keys().next()) != null) {
				DATA.remove(key);
			}
		} catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		
	}

	public void autonomousPeriodic() {
		
		System.out.println(DATA.toString());
		
	}

	public void teleopInit() {
		
		try {
			String key = null;
			
			while((key = DATA.keys().next()) != null) {
				DATA.remove(key);
			}
		} catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		
	}

	public void teleopPeriodic() {
		
		driveBaseController.setMode(ControlMode.TELEOPERATED_DEFAULT);
		
		DATA.put("leftX", gamepad.getRawAxis(0));
		DATA.put("leftY", gamepad.getRawAxis(1));
		DATA.put("leftTrigger", gamepad.getRawAxis(2));
		DATA.put("rightTrigger", gamepad.getRawAxis(3));
		DATA.put("rightX", gamepad.getRawAxis(4));
		DATA.put("rightY", gamepad.getRawAxis(5));
		
		driveBaseController.tick();
		
		driveBase.tick();
		
		shooter.set((DATA.getDouble("leftTrigger") + DATA.getDouble("rightTrigger")) / 2.0);
		
		System.out.println(DATA.toString());
		
		System.out.println(CameraMonitor.getImageDataURIFromDevice());
		
	}

	public void testInit() {
		
		try {
			String key = null;
			
			while((key = DATA.keys().next()) != null) {
				DATA.remove(key);
			}
		} catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		
	}

	public void testPeriodic() {
	}

}
