package org.usfirst.frc.team2503.r2016.input.joystick;

import org.usfirst.frc.team2503.r2016.data.DataSource;

public abstract class Joystick extends edu.wpi.first.wpilibj.Joystick {

	public class ControllerButton {

		private final int channel;

		public boolean get() {
			return Joystick.this.getRawButton(channel);
		}

		public ControllerButton(final int port) {
			this.channel = port;
		}

	}

	public class ControllerPOV {

		public final int channel;

		public int get() {
			return Joystick.this.getPOV(channel);
		}

		public double getDegrees() {
			return (double) this.get();
		}

		public ControllerPOV(final int channel) {
			this.channel = channel;
		}

		public ControllerPOV() {
			this.channel = 0;
		}

	}

	public enum ControllerAxisType {
		NEGATIVE_TO_POSITIVE, POSITIVE_TO_NEGATIVE, ZERO_TO_POSITIVE
	}

	public class ControllerAxis {

		private final int channel;
		private final ControllerAxisType type;

		public double get() {
			return Joystick.this.getRawAxis(channel);
		}

		public ControllerAxisType getType() {
			return type;
		}

		public ControllerAxis(final int port, ControllerAxisType type) {
			this.channel = port;
			this.type = type;
		}

	}

	public Joystick(int port) {
		super(port);
	}

}
