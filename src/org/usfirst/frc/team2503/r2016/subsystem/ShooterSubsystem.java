package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.subsystem.base.DataSpeedControllerSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class ShooterSubsystem extends DataSpeedControllerSubsystem {

	public enum ShooterSubsystemDataKey implements SubsystemDataKey {
		POWER
	}
	
	public void tick() {
		double power = (double) this.getDataKey(ShooterSubsystemDataKey.POWER);
		
		this._controller.set(power);
	}
	
	public ShooterSubsystem(SpeedController _controller) {
		super(_controller);
	}
	
	public ShooterSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

	public void setPower(double power) {
		this.setDataKey(ShooterSubsystemDataKey.POWER, power);
	}
	
	public void setDataKey(ShooterSubsystemDataKey key, Object value) {
		super.setDataKey(key, value);
	}

	public Object getDataKey(ShooterSubsystemDataKey key) {
		return this._data.get(key);
	}

}
