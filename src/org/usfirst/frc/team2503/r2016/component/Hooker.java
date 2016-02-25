package org.usfirst.frc.team2503.r2016.component;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class Hooker extends SpeedControllerComponent {
	
	public DigitalInput limitSwitch;
	public Encoder encoder;
	
	public Hooker(SpeedController controller) {
		super(controller);
	}
	
	public Hooker(SpeedController controller, DigitalInput limitSwitch) {
		super(controller);
		
		this.limitSwitch = limitSwitch;
	}
	
	public Hooker(SpeedController controller, Encoder encoder) {
		super(controller);
		
		this.encoder = encoder;
	}
	
	public Hooker(SpeedController controller, Encoder encoder, DigitalInput limitSwitch) {
		super(controller);
		
		this.encoder = encoder;
		this.limitSwitch = limitSwitch;
	}
	
}
