package org.usfirst.frc.team2503.r2016.input.joystick;

public class Joystick extends edu.wpi.first.wpilibj.Joystick {

	public enum AxisType {
		NEGATIVE_TO_POSITIVE(0),
		ZERO_TO_POSITIVE(1);
		
		private final int value;
		
		private AxisType(final int value) {
			this.value = value;
		}
		
		public final int getValue() {
			return value;
		}
	}
	
	public class Axis {
		private final int channel;
		private final AxisType axisType;
		private boolean inverted = false;
		
		public double getRaw() {
			return Joystick.this.getRawAxis(channel);
		}
		
		public double get() {
			switch(this.axisType) {
			case ZERO_TO_POSITIVE:
				return (this.inverted ? 1.0 - Math.abs(this.getRaw()) : Math.abs(this.getRaw()));
			case NEGATIVE_TO_POSITIVE:
			default:
				return this.getRaw() * (this.inverted ? -1.0 : 1.0);
			}
		}
		
		public Axis(AxisType axisType, final int channel) {
			this.axisType = axisType;
			this.channel = channel;
		}
		
		public Axis(final int channel) {
			this.axisType = AxisType.NEGATIVE_TO_POSITIVE;
			this.channel = channel;
		}
	}
	
	public Joystick(int port) {
		super(port);
	}

}
