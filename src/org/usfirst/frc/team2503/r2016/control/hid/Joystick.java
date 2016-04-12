package org.usfirst.frc.team2503.r2016.control.hid;

import org.usfirst.frc.team2503.r2016.control.hid.Joystick.Axis;
import org.usfirst.frc.team2503.r2016.control.hid.Joystick.Button;
import org.usfirst.frc.team2503.r2016.control.hid.Joystick.POV;

public abstract class Joystick extends edu.wpi.first.wpilibj.Joystick {

	public class Button {
		
		private final int _channel;
		
		public boolean get() {
			return Joystick.this.getRawButton(this._channel);
		}
		
		public Button(final int _channel) {
			this._channel = _channel;
		}
		
	}
	
	public class POV {
		
		private final int _channel;
		
		public int get() {
			return Joystick.this.getPOV(this._channel);
		}
		
		public double getDegrees() {
			return (double) this.get();
		}
		
		public POV(final int _channel) {
			this._channel = _channel;
		}
		
		public POV() {
			this(0);
		}
		
	}
	
	public enum AxisType {
		UNSPECIFIED, NEGATIVE_TO_POSITIVE, ZERO_TO_POSITIVE
	}

	public class Axis {

		private final int _channel;
		private AxisType _type;

		public double get() {
			return Joystick.this.getRawAxis(this._channel);
		}

		public AxisType getType() {
			return this._type;
		}
		
		public void setAxisType(AxisType _type) {
			this._type = _type;
		}

		public Axis(final int _channel) {
			this._channel = _channel;
			this._type = AxisType.UNSPECIFIED;
		}

	}
	
	
	
	// These are the raw buttons that we have access to.
	protected final Button button_1 = new Button(1);
	protected final Button button_2 = new Button(2);
	protected final Button button_3 = new Button(3);
	protected final Button button_4 = new Button(4);
	protected final Button button_5 = new Button(5);
	protected final Button button_6 = new Button(6);
	protected final Button button_7 = new Button(7);
	protected final Button button_8 = new Button(8);
	protected final Button button_9 = new Button(9);
	protected final Button button_10 = new Button(10);
	protected final Button button_11 = new Button(11);
	protected final Button button_12 = new Button(12);

	// These are the raw POVs that we have access to.
	protected final POV pov_0 = new POV(0);
	
	// These are all of the raw axes that we have access to.
	protected final Axis axis_0 = new Axis(0);
	protected final Axis axis_1 = new Axis(1);
	protected final Axis axis_2 = new Axis(2);
	protected final Axis axis_3 = new Axis(3);
	protected final Axis axis_4 = new Axis(4);
	protected final Axis axis_5 = new Axis(5);
	
	
	
	public Joystick(int port, int numAxisTypes, int numButtonTypes) {
		super(port, numAxisTypes, numButtonTypes);
	}
	
	public Joystick(int port) {
		super(port);
	}
	
}
