package org.usfirst.frc.team2503.r2016.subsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class WinchSubsystem extends SpeedControllerSubsystem {

	public void tick() {}
	
	public WinchSubsystem(SpeedController _controller) {
		super(_controller);
	}
	
	public WinchSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

}
