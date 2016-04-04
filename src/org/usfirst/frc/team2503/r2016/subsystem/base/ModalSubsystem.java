package org.usfirst.frc.team2503.r2016.subsystem.base;

public interface ModalSubsystem extends Subsystem {
	
	public interface SubsystemMode {}
	
	public abstract void setMode(SubsystemMode mode);
	public abstract SubsystemMode getMode();

}
