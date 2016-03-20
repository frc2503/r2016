package org.usfirst.frc.team2503.r2016.component;

import org.json.JSONObject;
import org.usfirst.frc.team2503.r2016.data.Data;

import edu.wpi.first.wpilibj.SpeedController;

public class Shooter extends SpeedControllerComponent {

	public enum ShooterMode {
		STOPPING,
		SPINNING,
		FIRING
	}
	
	public void tick(Data data) {
		Data shooterData = (Data) data.get("shooter");
		ShooterMode mode = (ShooterMode) shooterData.get("mode");
		double shooterValue = (double) shooterData.get("value");
	}

	public Shooter(SpeedController controller) {
		super(controller);
	}

}
