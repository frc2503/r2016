package org.usfirst.frc.team2503.r2016.subsystem.base;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class DataSpeedControllerSubsystem extends SpeedControllerSubsystem implements DataSubsystem {

	protected SubsystemData _data = new SubsystemData();
	
	public void setData(SubsystemData data) {
		this._data = data;
	}
	
	public void setDataKey(SubsystemDataKey key, Object value) {
		this._data.put(key, value);
	}
	
	public SubsystemData getData() {
		return this._data;
	}
	
	public Object getDataKey(SubsystemDataKey key) {
		return this._data.get(key);
	}
	
	
	
	public DataSpeedControllerSubsystem(SpeedController _controller) {
		super(_controller);
	}

	public DataSpeedControllerSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

}
