package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.component.sensor.LimitSwitch;
import org.usfirst.frc.team2503.r2016.subsystem.base.DataSpeedControllerSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.DataSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.DataSubsystem.SubsystemData;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class HookerSubsystem extends DataSpeedControllerSubsystem {

	public enum HookerSubsystemDataKey implements DataSubsystem.SubsystemDataKey {
		POWER
	}

	@Override
	public void tick() {
		if(this._limitSwitch.isTripped())
			this._encoder.reset();
		
		double power = (double) this.getDataKey(HookerSubsystemDataKey.POWER);
		
		this._controller.set(power);
	}

	public void setPower(double power) {
		this.setDataKey(HookerSubsystemDataKey.POWER, power);
	}
	
	
	
	private Encoder _encoder;
	private LimitSwitch _limitSwitch;
	
	
	
	public HookerSubsystem(SpeedController _controller, Encoder _encoder, LimitSwitch _limitSwitch) {
		super(_controller);

		this._encoder = _encoder;
		this._limitSwitch = _limitSwitch;
	}
	
	public HookerSubsystem(SpeedControllerSubsystemType type, final int speedControllerChannel, final DigitalInput _encoderAChannel, final DigitalInput _encoderBChannel, final int limitSwitchChannel) {
		super(type, speedControllerChannel);
		
		this._encoder = new Encoder(_encoderAChannel, _encoderBChannel);
		this._limitSwitch = new LimitSwitch(limitSwitchChannel);
	}


}
