package org.usfirst.frc.team2503.r2016.component;

import org.usfirst.frc.team2503.r2016.input.Data;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class Hooker extends SpeedControllerComponent {

	public enum HookerMode {
		RESET,
		HORIZONTAL,
		READY,
		ATTACH
	}

	public DigitalInput limitSwitch;
	public Encoder encoder;

	public void tick(Data data) {
		// If the limit switch is pressed, reset the encoder to 0
		if(this.limitSwitch.get())
			this.encoder.reset();
	}

	public Hooker(SpeedController controller, Encoder encoder, DigitalInput limitSwitch) {
		super(controller);

		this.encoder = encoder;
		this.limitSwitch = limitSwitch;
	}

}
