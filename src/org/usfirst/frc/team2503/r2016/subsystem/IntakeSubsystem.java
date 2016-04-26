package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.component.sensor.LimitSwitch;
import org.usfirst.frc.team2503.r2016.subsystem.base.ModalSpeedControllerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.ModalSubsystem;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SpeedController;

public class IntakeSubsystem extends ModalSpeedControllerSubsystem {

	public enum IntakeSubsystemMode implements ModalSubsystem.SubsystemMode {
		DISABLED,
		STOPPED,
		
		OUTPUTTING,
		INTAKING,
		
		FIRING
	}
	
	
	private LimitSwitch _limitSwitch;
	private Relay _indicatorRelay;
	
	
	@Override
	public void tick() {
		IntakeSubsystemMode mode = (IntakeSubsystemMode) this.getMode();
		
		if(mode != null) {
			switch(mode) {
			case INTAKING:
				if(this.canIntake())
					this.set(1.0d);
				
				break;
				
			case OUTPUTTING:
				this.set(1.0d);
				break;
				
			case FIRING:
				this.set(1.0d);
				break;
	
			case DISABLED:
				this.stopMotor();
				break;
	
			case STOPPED:
			default:
				this.set(0.0d);
				break;
			}
		} else {
			this.set(0.0d);
		}
		
		if(this._limitSwitch.isTripped()) {
			this._indicatorRelay.set(Value.kOn);
		} else {
			this._indicatorRelay.set(Value.kOff);
		}
	}


	
	private boolean canIntake() {
		return (_limitSwitch.isTripped());
	}
	

	
	public IntakeSubsystem(SpeedController _controller, LimitSwitch limitSwitch, Relay indicatorRelay) {
		super(_controller);
		
		this._limitSwitch = limitSwitch;
		this._indicatorRelay = indicatorRelay;
		
		this._mode = IntakeSubsystemMode.STOPPED;
	}
	
	public IntakeSubsystem(SpeedControllerSubsystemType type, final int speedControllerChannel, final int limitSwitchChannel, final int indicatorRelayChannel) {
		super(type, speedControllerChannel);
		
		this._limitSwitch = new LimitSwitch(limitSwitchChannel);
		this._indicatorRelay = new Relay(indicatorRelayChannel);
		
		this._mode = IntakeSubsystemMode.STOPPED;
	}

}
