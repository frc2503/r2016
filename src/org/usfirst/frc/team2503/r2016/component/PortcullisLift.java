package org.usfirst.frc.team2503.r2016.component;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PortcullisLift extends DoubleSolenoid {

	public PortcullisLift(int forwardChannel, int reverseChannel) {
		super(forwardChannel, reverseChannel);
	}
	
	public PortcullisLift(int moduleNumber, int forwardChannel, int reverseChannel) {
		super(moduleNumber, forwardChannel, reverseChannel);
	}

}
