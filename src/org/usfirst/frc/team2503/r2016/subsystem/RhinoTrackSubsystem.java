package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.subsystem.base.DataSpeedControllerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.DataSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class RhinoTrackSubsystem extends DataSpeedControllerSubsystem {
	
	public enum RhinoTrackSubsystemDataKey implements DataSubsystem.SubsystemDataKey {
		POWER
	}

	
	
	@Override
	public void tick() {
		double power = (double) this.getDataKey(RhinoTrackSubsystemDataKey.POWER);
		
		this.set(power);
	}
	
	public void setPower(double power) {
		this.setDataKey(RhinoTrackSubsystemDataKey.POWER, power);
	}
	
	
	
	public RhinoTrackSubsystem(SpeedController _controller) {
		super(_controller);
	}

	public RhinoTrackSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

}
