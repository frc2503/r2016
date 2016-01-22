package org.usfirst.frc.team2503.r2016.input;

public class NullMonitor extends Monitor {
	
	public NullMonitor(Input... inputs) {
		super(inputs);
	}
	
	protected void setData() {
		System.out.println("[NullMonitor] Set data: " + this.data.toString());
		this.data.put("null", null);
	}

	public void onStart() {
	}
	
	public void onStop() {
	}

	public void tick() {
		setData();
	}
	
}
