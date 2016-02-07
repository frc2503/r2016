package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.r2016.data.DataServer;
import org.usfirst.frc.team2503.r2016.input.vision.CameraMonitor;
import org.usfirst.frc.team2503.websocket.WebSocket;

public class CameraMonitorTest implements Runnable {

	public DataServer ds;
	
	@Override
	public void run() {
		while(true) {
			String dataURI = "data:image/jpeg;base64," + CameraMonitor.getImageDataURIFromDevice();
			
			this.ds.put("image", dataURI);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public CameraMonitorTest(DataServer ds) {
		this.ds = ds;

	}
	
}
