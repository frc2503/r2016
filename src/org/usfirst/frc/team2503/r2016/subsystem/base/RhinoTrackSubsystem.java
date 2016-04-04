package org.usfirst.frc.team2503.r2016.subsystem.base;

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

	
	
	public void setDataKey(RhinoTrackSubsystemDataKey key, Object value) {
		super.setDataKey(key, value);
	}

	public Object getDataKey(RhinoTrackSubsystemDataKey key) {
		return this._data.get(key);
	}
	
	
	
	public RhinoTrackSubsystem(SpeedController _controller) {
		super(_controller);
	}

	public RhinoTrackSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

}
