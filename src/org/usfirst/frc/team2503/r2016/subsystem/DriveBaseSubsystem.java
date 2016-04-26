package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.control.DualMotorDrivable;
import org.usfirst.frc.team2503.r2016.subsystem.base.DataSubsystem;
import org.usfirst.frc.team2503.r2016.subsystem.base.SpeedControllerSubsystem.SpeedControllerSubsystemType;

import edu.wpi.first.wpilibj.SpeedController;

public class DriveBaseSubsystem implements DualMotorDrivable, DataSubsystem {
	
	public enum DriveBaseSubsystemDataKey implements DataSubsystem.SubsystemDataKey {
		LEFT_POWER,
		RIGHT_POWER
	}
	
	
	
	protected SubsystemData _data = new SubsystemData();
	
	private RhinoTrackSubsystem _leftSubsystem;
	private RhinoTrackSubsystem _rightSubsystem;
	
	
	
	@Override
	public void tick() {
		if((this.getDataKey(DriveBaseSubsystemDataKey.LEFT_POWER) != null) &&
		   (this.getDataKey(DriveBaseSubsystemDataKey.RIGHT_POWER) != null)) {
			double left = (double) this.getDataKey(DriveBaseSubsystemDataKey.LEFT_POWER);
			double right = (double) this.getDataKey(DriveBaseSubsystemDataKey.RIGHT_POWER);
			
			_leftSubsystem.setPower(left);
			_rightSubsystem.setPower(right);
		}
		
		_leftSubsystem.tick();
		_rightSubsystem.tick();
	}
	
	@Override
	public void drive(double left, double right) {
		this.setDataKey(DriveBaseSubsystemDataKey.LEFT_POWER, left);
		this.setDataKey(DriveBaseSubsystemDataKey.RIGHT_POWER, right);
	}
	
	
	
	public void setData(SubsystemData data) { this._data = data; }
	public void setDataKey(SubsystemDataKey key, Object value) { this._data.put(key, value); }
	
	public SubsystemData getData() { return this._data; }
	public Object getDataKey(SubsystemDataKey key) { return this._data.get(key); }
	
	
	
	public DriveBaseSubsystem(SpeedController _left, SpeedController _right) {
		this(new RhinoTrackSubsystem(_left), new RhinoTrackSubsystem(_right));
	}

	public DriveBaseSubsystem(SpeedControllerSubsystemType leftType, final int leftChannel, SpeedControllerSubsystemType rightType, final int rightChannel) {
		this(new RhinoTrackSubsystem(leftType, leftChannel), new RhinoTrackSubsystem(rightType, rightChannel));
	}

	public DriveBaseSubsystem(RhinoTrackSubsystem _leftSubsystem, RhinoTrackSubsystem _rightSubsystem) {
		this._leftSubsystem = _leftSubsystem;
		this._rightSubsystem = _rightSubsystem;
		
		this._data = new SubsystemData();
		this.setDataKey(DriveBaseSubsystemDataKey.LEFT_POWER, 0.0d);
		this.setDataKey(DriveBaseSubsystemDataKey.RIGHT_POWER, 0.d);
	}

}
