package org.usfirst.frc.team2503.r2016.components;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class RhinoTrack implements EncodedSpeedController {
	
	public SpeedController speedController;
	public Encoder encoder;
	
	public RhinoTrack(SpeedController speedController, Encoder encoder) {
		this.speedController = speedController;
		this.encoder = encoder;
	}

}
