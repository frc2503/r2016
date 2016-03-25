package org.usfirst.frc.team2503.r2016.subsystem;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class DataSpeedControllerSubsystem extends SpeedControllerSubsystem implements DataSubsystem {

	public DataSpeedControllerSubsystem(SpeedController _controller) {
		super(_controller);
	}

	public DataSpeedControllerSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

}
