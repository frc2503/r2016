package org.usfirst.frc.team2503.r2016;

public class Constants {

	public static final String VERSION = "1.1.0";
	public static final String defaultHostname = "0.0.0.0";

	public static final boolean PRODUCTION = false;

	public static String USB_BASE_DIRECTORY_FILENAME;

	static {
		if(PRODUCTION)
			USB_BASE_DIRECTORY_FILENAME = "/U/";
		else
			USB_BASE_DIRECTORY_FILENAME = "./";
	}

}
