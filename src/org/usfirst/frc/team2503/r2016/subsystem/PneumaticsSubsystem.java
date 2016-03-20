package org.usfirst.frc.team2503.r2016.subsystem;

import org.usfirst.frc.team2503.r2016.data.Data;
import org.usfirst.frc.team2503.r2016.data.DataSource;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PneumaticsSubsystem extends Subsystem implements DataSource {

	public Compressor compressor;
	public DoubleSolenoid lift;
	
	public Data getData() {
		return null;
	}

	public void tick(Data data) {
	}
	
	public PneumaticsSubsystem(Compressor compressor, DoubleSolenoid lift) {
		this.compressor = compressor;
		this.lift = lift;
	}

}
