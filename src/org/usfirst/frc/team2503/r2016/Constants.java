package org.usfirst.frc.team2503.r2016;

public class Constants {

	public enum Environment {
		DEVELOPMENT, PRODUCTION
	}
	
	public static Environment ENVIRONMENT = Environment.DEVELOPMENT;
	
	public static String HOME_DIRECTORY = (ENVIRONMENT == Environment.DEVELOPMENT ? "." : "/home/lvuser");
	
}
