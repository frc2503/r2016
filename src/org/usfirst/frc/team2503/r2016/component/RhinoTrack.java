package org.usfirst.frc.team2503.r2016.component;

import edu.wpi.first.wpilibj.SpeedController;

public class RhinoTrack implements SpeedController, Component {

	public SpeedController controller;
	
	public RhinoTrack(SpeedController controller) {
		this.controller = controller;
	}
	
	public void pidWrite(double output) { this.controller.pidWrite(output); }
	public double get() { return this.controller.get(); }
	public void set(double speed) { this.controller.set(speed); }
	public void set(double speed, byte syncGroup) { this.controller.set(speed, syncGroup); }
	public void setInverted(boolean isInverted) { this.controller.setInverted(isInverted); }
	public boolean getInverted() { return this.controller.getInverted(); }
	public void disable() { this.controller.disable(); }
	
}
