package org.usfirst.frc.team2503.r2016;

import edu.wpi.first.wpilibj.Talon;

public class TestbotDriveBase implements DriveBase{

	public static Talon outerLeftTalon;
	public static Talon innerLeftTalon;
	public static Talon innerRightTalon;
	public static Talon outerRightTalon;
	
	@Override
	public void drive(double left, double right) {
		
		outerLeftTalon.set(-left);
		innerLeftTalon.set(-left);
		innerRightTalon.set(right);
		outerRightTalon.set(right);
		
	}
	
	public TestbotDriveBase() {
		
		outerLeftTalon = new Talon(3);
		innerLeftTalon = new Talon(2);
		innerRightTalon = new Talon(1);
		outerRightTalon = new Talon(0);
		
	}

}
