package org.usfirst.frc.team2503.r2016.component;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

public class Intake extends SpeedControllerComponent {
	
	public enum IntakeMode {
		UNKNOWN,
		STOPPED,
		INBOUND,
		OUTBOUND,
		FIRE
	}

	public DigitalInput limitSwitch;

	private IntakeMode mode = IntakeMode.UNKNOWN;
	
	public void setMode(IntakeMode mode) { this.mode = mode; }
	public IntakeMode getMode() { return this.mode; }
	
	public void tick() {
		switch(mode) {
		case STOPPED:
			this.controller.set(0.0);
			break;
			
		case INBOUND:
			if(limitSwitch.get()) {
				this.controller.set(0.0);
			} else {
				this.controller.set(1.0);
			}
			
			break;
			
		case OUTBOUND:
			this.controller.set(-1.0);
			break;
			
		case FIRE:
			this.controller.set(1.0);
			break;
		
		case UNKNOWN:
		default:
			break;
		}
	}
	
	public Intake(SpeedController controller, DigitalInput limitSwitch) {
		super(controller);
		
		this.limitSwitch = limitSwitch;
	}
	
}
