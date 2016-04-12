package org.usfirst.frc.team2503.r2016.subsystem.base;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class ModalSpeedControllerSubsystem extends SpeedControllerSubsystem implements ModalSubsystem {

	private SubsystemMode _mode;

	public void setMode(SubsystemMode mode) {
		this._mode = mode;
	}

	public SubsystemMode getMode() {
		return this._mode;
	}
	
	public ModalSpeedControllerSubsystem(SpeedController _controller) {
		super(_controller);
	}

	public ModalSpeedControllerSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}


}
