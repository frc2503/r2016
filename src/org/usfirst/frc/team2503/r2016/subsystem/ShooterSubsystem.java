package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.subsystem.base.DataSpeedControllerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.DataSubsystem;

import edu.wpi.first.wpilibj.SpeedController;

public class ShooterSubsystem extends DataSpeedControllerSubsystem {

	public enum ShooterSubsystemDataKey implements DataSubsystem.SubsystemDataKey {
		POWER
	}



	@Override
	public void tick() {
		double power = (double) this.getDataKey(ShooterSubsystemDataKey.POWER);

		this.set(power);
	}

	public void setPower(double power) {
		this.setDataKey(ShooterSubsystemDataKey.POWER, power);
	}



	public ShooterSubsystem(SpeedController _controller) {
		super(_controller);
	}

	public ShooterSubsystem(SpeedControllerSubsystemType type, final int channel) {
		super(type, channel);
	}

}
