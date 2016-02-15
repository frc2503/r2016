package org.usfirst.frc.team2503.r2016.component;

public abstract class Component {

	private ControlMode mode;
	
	public void setMode(ControlMode mode) {
		this.mode = mode;
	}
	
	public ControlMode getMode() {
		return this.mode;
	}
	
}
