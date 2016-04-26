package org.usfirst.frc.team2503.r2016.component.sensor;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch extends DigitalInput {

	public enum LimitSwitchWiringSchema {
		NORMALLY_OPEN,
		NORMALLY_CLOSED
	}
	
	private LimitSwitchWiringSchema _schema = null;
	
	public LimitSwitch(final int channel) {
		super(channel);
		
		this.setSchema(LimitSwitchWiringSchema.NORMALLY_OPEN);
	}
	
	public LimitSwitch(final int channel, LimitSwitchWiringSchema schema) {
		super(channel);
		
		this.setSchema(schema);
	}
	
	public LimitSwitchWiringSchema getSchema() { return _schema; }
	public void setSchema(LimitSwitchWiringSchema schema) { this._schema = schema; }
	
	public boolean isTripped() {
		/**
		 * Signify whether the normal (non-activated) value of
		 * the LimitSwitch's DigitalInput feature should be false
		 * or true. 
		 */
		boolean normal = false;
		
		/**
		 * Based on our given LimitSwitchWiringSchema, decide what the default value of
		 * `get()` should be if the switch is not activated.
		 */
		switch(this.getSchema()) {
		case NORMALLY_OPEN:
			normal = false;
			break;
			
		case NORMALLY_CLOSED:
			normal = true;
			break;
			
		default:
			/**
			 * This case should be rare---but, if the `_schema` is improperly
			 * written, meaning that it is still `null` or is out of bounds for
			 * the enum, we should set it to a sensible default and continue
			 * like nothing happened.
			 */
			setSchema(LimitSwitchWiringSchema.NORMALLY_OPEN);
			normal = false;
			break;
		}
		
		return normal && this.get();
	}

}
