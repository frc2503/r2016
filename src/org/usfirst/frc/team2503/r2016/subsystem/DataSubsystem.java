package org.usfirst.frc.team2503.r2016.subsystem;

import java.util.HashMap;

public interface DataSubsystem {

	public interface SubsystemDataKey {}
	
	public class SubsystemData extends HashMap<SubsystemDataKey, Object> {

		/**
		 * This class is Serializable, apparently.
		 */
		private static final long serialVersionUID = 1L;

	}
	
	public abstract void setData(SubsystemData data);
	public abstract void setDataKey(SubsystemDataKey key, Object value);
	
	public abstract SubsystemData getData();
	public abstract Object getDataKey(SubsystemDataKey key);
	
}
