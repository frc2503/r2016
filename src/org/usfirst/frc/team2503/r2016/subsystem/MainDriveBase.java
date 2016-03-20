package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.component.RhinoTrack;
import org.usfirst.frc.team2503.r2016.data.Data;

import edu.wpi.first.wpilibj.Encoder;

public class MainDriveBase extends DriveBase {


	public void tick(Data data) {
	}

	public Data getData() {
		Data data = new Data();

		{
			Data encoders = new Data();

			encoders.put("left", leftEncoder.get());
			encoders.put("right", rightEncoder.get());

			data.put("encoders", encoders);
		}

		return data;
	}

	public MainDriveBase(RhinoTrack left, Encoder leftEncoder, RhinoTrack right, Encoder rightEncoder) {
		super(left, right);

		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
	}

}
