package org.usfirst.frc.team2503.r2016;

import java.net.InetSocketAddress;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.debug.Logger;
import org.usfirst.frc.team2503.r2016.debug.Logger.LoggerPrintStream;
import org.usfirst.frc.team2503.r2016.server.DataServer;
import org.usfirst.frc.team2503.r2016.server.MessageServer;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {
	
	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "true");
		
//		Logger.addPrintStream(new LoggerPrintStream(System.out));
//		
//		Camera camera = new Camera(0);
//		try {
//			camera.start();
//			camera.grabFrame();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Logger.println(camera.getLatestFrame());
//		
//		Logger.println("foo");
		
		Thread dataServerThread = null;
		Thread messageServerThread = null;
		
		DataServer ds = new DataServer(new InetSocketAddress(5800));
		MessageServer ms = new MessageServer(new InetSocketAddress(5801));
		
		dataServerThread = new Thread(ds);
		messageServerThread = new Thread(ms);

		dataServerThread.start();
		messageServerThread.start();
		
		try {
			dataServerThread.join();
			messageServerThread.join();
		} catch(InterruptedException ex) {
			ex.printStackTrace(System.err);
		}
	}
	
}
