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
	
	@Override
	public void tick() {
		WinchSubsystemMode mode = (WinchSubsystemMode) this.getMode();
		
		switch(mode) {
		/**
		 * The robot is heavy enough that, if the ratchet is
		 * not engaged, it will slowly drift downwards.
		 * 
		 * Thus, we can apply a small amount of power (5%) to
		 * apply a bit of vertical lifting power.
		 */
		case BASIC_SUSPENSION_WINCHING:
			this._controller.set(0.0500d);
			break;
			
		/**
		 * This mode is for when we are lifting slowly, or trying
		 * to get the hook off before winching at full power.
		 */
		case SLOW_WINCHING:
			this._controller.set(0.2000d);
			break;
			
		/**
		 * This mode is for when we are lifting quickly, or trying
		 * to scale ASAP.
		 */
		case WINCHING:
			this._controller.set(1.0000d);
			break;

		/**
		 * This mode is for when we are lowering the robot (with the
		 * ratchet not set)
		 */
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
