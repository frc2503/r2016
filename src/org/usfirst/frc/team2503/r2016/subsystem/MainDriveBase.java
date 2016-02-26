package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.input.Data;

import edu.wpi.first.wpilibj.SpeedController;

public class MainDriveBase extends DriveBase {

	public void tick(Data data) {
	}

	public MainDriveBase(SpeedController left, SpeedController right) {
		super(left, right);
	}

}
