package org.usfirst.frc.team2503.r2016.control;

public enum ControlMode {

	DEFAULT(0x0),
	AUTONOMOUS_DEFAULT(0x1000),
	TELEOPERATED_DEFAULT(0x2000);
	
	private int value;
	
	public final int getValue() {
		return this.value;
	}
	
	private ControlMode(final int value) {
		this.value = value;
	}
	
}
