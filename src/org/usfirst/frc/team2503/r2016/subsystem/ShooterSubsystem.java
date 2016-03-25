package org.usfirst.frc.team2503.r2016.subsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class ShooterSubsystem extends SpeedControllerSubsystem {

	public enum ShooterSubsystemMode implements ModalSubsystem.SubsystemMode {
		DISABLED,
		EIGHTY_POWER,
		FULL_POWER
	}
	
	public void tick() {}
	
	public ShooterSubsystem(SpeedController _controller) {
		super(_controller);
	}
	
	public ShooterSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}


}
