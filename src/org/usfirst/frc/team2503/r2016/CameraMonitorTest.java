package org.usfirst.frc.team2503.r2016;

import org.bytedeco.javacv.FrameGrabber.Exception;
import org.usfirst.frc.team2503.r2016.data.DataServer;
import org.usfirst.frc.team2503.r2016.input.vision.CameraMonitor;

public class CameraMonitorTest implements Runnable {

	public DataServer ds;

	@Override
	public void run() {
		try {
			CameraMonitor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
