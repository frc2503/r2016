package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.input.DataSource;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class DriveBase extends Subsystem implements DataSource {

	public SpeedController left;
	public SpeedController right;

	public double leftPower;
	public double rightPower;

	public void drive(double left, double right) {
		this.left.set(left);
		this.right.set(right);
	}

	public DriveBase(SpeedController left, SpeedController right) {
		this.left = left;
		this.right = right;
	}

}
