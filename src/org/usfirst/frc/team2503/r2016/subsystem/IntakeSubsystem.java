package org.usfirst.frc.team2503.r2016.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

public class IntakeSubsystem extends ModalSpeedControllerSubsystem {

	private DigitalInput _limitSwitch;
	
	public enum IntakeSubsystemMode implements ModalSubsystem.SubsystemMode {
		DISABLED,
		STOPPED,
		OUTPUTTING,
		INTAKING,
		FIRING
	}
	
	public IntakeSubsystem(SpeedController _controller, DigitalInput limitSwitch) {
		super(_controller);
		
		this._limitSwitch = limitSwitch;
	}
	
	public IntakeSubsystem(SpeedControllerSubsystemType type, final int speedControllerChannel, final int limitSwitchChannel) {
		super(type, speedControllerChannel);
		
		this._limitSwitch = new DigitalInput(limitSwitchChannel);
	}

	public void tick() {
		IntakeSubsystemMode mode = (IntakeSubsystemMode) this.getMode();
		
		
		
		switch(mode) {
		case DISABLED:
		case STOPPED:
			break;
			
		case INTAKING:
			break;
			
		case OUTPUTTING:
			break;
			
		case FIRING:
			break;

		default:
			break;
		}
	}


}
