package org.usfirst.frc.team2503.r2016.input.vision;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.usfirst.frc.team2503.r2016.Constants;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import java.time.*;

import static org.bytedeco.javacpp.opencv_core.*;

public class CameraMonitor {

	public static final boolean borked = true;

	public static Frame latestFrame = null;
	public static FrameGrabber grabber = new OpenCVFrameGrabber(1);
	public static BufferedImage latestBufferedImage = null;
	public static IplImage latestIplImage = null;
	
	public static OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
	public static Java2DFrameConverter paintConverter = new Java2DFrameConverter();
	
	public static void grabFrame() throws Exception {
		grabber.start();
		Frame frame = grabber.grabFrame();
		grabber.stop();
		
		latestFrame = frame;
	}
	
	public static void convertLatestFrameToBufferedImage() {
		latestBufferedImage = paintConverter.getBufferedImage(latestFrame);
	}
	
	public static void convertLatestFrameToIplImage() {
		latestIplImage = grabberConverter.convert(latestFrame);
	}
	
	public static String getImageDataURIFromDevice() {
		try {
			LocalTime t0 = LocalTime.now();
			
			grabFrame();

			convertLatestFrameToBufferedImage();
			// convertLatestFrameToIplImage();

			BufferedImage newImage = new BufferedImage(128, 80, BufferedImage.TYPE_INT_RGB);
			
			Graphics g = newImage.createGraphics();
			g.drawImage(latestBufferedImage, 0, 0, 128, 80, null);
			g.dispose();
			
			File file = new File(Constants.HOME_DIRECTORY + "/capture.jpg");
			File file2 = new File(Constants.HOME_DIRECTORY + "/capture2.jpg");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
						
			ImageIO.write(latestBufferedImage, "jpg", file);
			ImageIO.write(newImage, "jpg", baos);
			ImageIO.write(newImage, "jpg", file2);
			
			LocalTime t1 = LocalTime.now();
			
			Duration duration = Duration.between(t0, t1);
			System.out.println(duration.toMillis());
			
			return DatatypeConverter.printBase64Binary(baos.toByteArray());
		} catch(IOException | Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	
}
