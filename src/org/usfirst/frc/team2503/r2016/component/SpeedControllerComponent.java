package org.usfirst.frc.team2503.r2016.component;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class SpeedControllerComponent extends Component implements SpeedController {
	
	protected SpeedController controller;
	
	public SpeedControllerComponent(SpeedController controller) {
		this.controller = controller;
	}
	
	public void disable() { this.controller.disable(); }
	public double get() { return this.controller.get(); }
	public boolean getInverted() { return this.controller.getInverted(); }
	public void pidWrite(double output) { this.controller.pidWrite(output); }
	public void set(double speed) { this.controller.set(speed); }
	public void set(double speed, byte syncGroup) { this.controller.set(speed, syncGroup); }
	public void setInverted(boolean isInverted) { this.controller.setInverted(isInverted); }
	
}
