package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.input.sensor.LimitSwitch;
import org.usfirst.frc.team2503.r2016.subsystem.base.ModalSpeedControllerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.ModalSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class IntakeSubsystem extends ModalSpeedControllerSubsystem {

	private LimitSwitch _limitSwitch;
	
	public enum IntakeSubsystemMode implements ModalSubsystem.SubsystemMode {
		DISABLED,
		STOPPED,
		OUTPUTTING,
		INTAKING,
		FIRING
	}
	
	public void tick() {
		IntakeSubsystemMode mode = (IntakeSubsystemMode) this.getMode();
		
		switch(mode) {
		case INTAKING:
			if(this.canIntake())
				this._controller.set(1.0d);
			
			break;
			
		case OUTPUTTING:
			this._controller.set(1.0d);
			break;
			
		case FIRING:
			this._controller.set(1.0d);
			break;

		case DISABLED:
			this._controller.stopMotor();
			break;

		case STOPPED:
		default:
			this._controller.set(0.0d);
			break;
		}
	}


	
	private boolean canIntake() {
		return (_limitSwitch.isTripped());
	}
	

	
	public IntakeSubsystem(SpeedController _controller, LimitSwitch limitSwitch) {
		super(_controller);
		
		this._limitSwitch = limitSwitch;
	}
	
	public IntakeSubsystem(SpeedControllerSubsystemType type, final int speedControllerChannel, final int limitSwitchChannel) {
		super(type, speedControllerChannel);
		
		this._limitSwitch = new LimitSwitch(limitSwitchChannel);
	}

}
