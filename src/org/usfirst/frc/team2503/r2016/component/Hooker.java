package org.usfirst.frc.team2503.r2016.component;

import org.usfirst.frc.team2503.r2016.input.Data;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class Hooker extends SpeedControllerComponent {

	public DigitalInput limitSwitch;
	public Encoder encoder;

	public void tick(Data data) {
	}

	public Hooker(SpeedController controller, Encoder encoder, DigitalInput limitSwitch) {
		super(controller);

		this.encoder = encoder;
		this.limitSwitch = limitSwitch;
	}

}
