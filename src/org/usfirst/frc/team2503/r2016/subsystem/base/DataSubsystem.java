package org.usfirst.frc.team2503.r2016.subsystem.base;

import java.util.HashMap;

public interface DataSubsystem extends Subsystem {

	public interface SubsystemDataKey {}
	
	public class SubsystemData extends HashMap<SubsystemDataKey, Object> {

		/**
		 * This class is Serializable, apparently.
		 */
		private static final long serialVersionUID = 1L;

	}

	public void setData(SubsystemData data);
	public void setDataKey(SubsystemDataKey key, Object value);
	
	public SubsystemData getData();
	public Object getDataKey(SubsystemDataKey key);
	
}
