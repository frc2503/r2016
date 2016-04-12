package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.lib.util.WarriorMath;
import org.usfirst.frc.team2503.r2016.subsystem.base.DataSubsystem;

import edu.wpi.first.wpilibj.Servo;

public class CameraSubsystem implements DataSubsystem {

	public enum CameraSubsystemDataKey implements SubsystemDataKey {
		HORIZONTAL_ROTATION_DEGREES,
		VERTICAL_ROTATION_DEGREES
	}
	
	
	
	protected SubsystemData _data = new SubsystemData();

	private Servo _horizontal;
	private Servo _vertical;
	
	private double horizontalDegrees[] = {-90.0, 90.0};
	private double verticalDegrees[] = {-90.0, 90.0};
	
	
	
	@Override
	public void tick() {
		double horizontalRotationDegrees = (double) this.getDataKey(CameraSubsystemDataKey.HORIZONTAL_ROTATION_DEGREES);
		double verticalRotationDegrees = (double) this.getDataKey(CameraSubsystemDataKey.VERTICAL_ROTATION_DEGREES);
		
		double horizontalValue = WarriorMath.map(horizontalDegrees[1], horizontalDegrees[0], horizontalRotationDegrees, 0.0, 1.0);
		double verticalValue = WarriorMath.map(verticalDegrees[1], verticalDegrees[0], verticalRotationDegrees, 0.0, 1.0);
		
		_horizontal.set(horizontalValue);
		_vertical.set(verticalValue);
	}
	
	public void calibrateDegrees(double horizontal[], double vertical[]) {
		this.horizontalDegrees = horizontal;
		this.verticalDegrees = vertical;
	}
	
	
	
	public void setData(SubsystemData data) { this._data = data; }
	public void setDataKey(SubsystemDataKey key, Object value) { this._data.put(key, value); }
	
	public SubsystemData getData() { return this._data; }
	public Object getDataKey(SubsystemDataKey key) { return this._data.get(key); }
	
	
	
	public CameraSubsystem(Servo _horizontal, Servo _vertical) {
		this._horizontal = _horizontal;
		this._vertical = _vertical;
	}

}
