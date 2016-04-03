package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.subsystem.base.ModalSpeedControllerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.ModalSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class WinchSubsystem extends ModalSpeedControllerSubsystem {
	
	public enum WinchSubsystemMode implements ModalSubsystem.SubsystemMode {
		DISABLED,
		STOPPED,
		
		BASIC_SUSPENSION_WINCHING,
		
		SLOW_WINCHING,
		WINCHING,
		LOWERING
	}
	
	public void tick() {
		WinchSubsystemMode mode = (WinchSubsystemMode) this.getMode();
		
		switch(mode) {
		case BASIC_SUSPENSION_WINCHING:
			this._controller.set(0.0500d);
			break;
			
		case SLOW_WINCHING:
			this._controller.set(0.2000d);
			break;
			
		case WINCHING:
			this._controller.set(1.0000d);
			break;
			
		case LOWERING:
			this._controller.set(-0.3000d);
			break;

		case DISABLED:
			this._controller.stopMotor();
			break;

		case STOPPED:
		default:
			this._controller.set(0.0000d);
			break;
		}
	}
	
	public WinchSubsystem(SpeedController _controller) {
		super(_controller);
	}
	
	public WinchSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

}
