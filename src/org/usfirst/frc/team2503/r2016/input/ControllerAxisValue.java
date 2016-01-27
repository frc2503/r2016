package org.usfirst.frc.team2503.r2016.input;

public class ControllerAxisValue {
	
	public final int channel;
	
	protected boolean inverted = false;
	
	public ControllerAxisValue(final int channel) {
		this.channel = channel;
	}

}
