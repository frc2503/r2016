package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.component.pneumatics.PortcullisLift;
import org.usfirst.frc.team2503.r2016.subsystem.base.ModalSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class PortcullisLiftSubsystem implements ModalSubsystem {

	public enum PortcullisLiftSubsystemMode implements ModalSubsystem.SubsystemMode {
		DISABLED,
		STOPPED,

		RAISING,
		LOWERING
	}



	private PortcullisLift _lift;
	private SubsystemMode _mode;



	@Override
	public void tick() {
		PortcullisLiftSubsystemMode mode = (PortcullisLiftSubsystemMode) this.getMode();

		switch(mode) {
		case RAISING:
			this._lift.set(Value.kForward);
			break;

		case LOWERING:
			this._lift.set(Value.kReverse);
			break;

		case DISABLED:
		case STOPPED:
		default:
			this._lift.set(Value.kOff);
			break;
		}
	}



	public void setMode(SubsystemMode mode) {
		this._mode = mode;
	}

	public SubsystemMode getMode() {
		return this._mode;
	}

	public PortcullisLiftSubsystem(PortcullisLift lift) {
		this._lift = lift;
	}

}
