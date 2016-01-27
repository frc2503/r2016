package org.usfirst.frc.team2503.r2016.input;

public abstract class Monitor extends DataSource implements Runnable {
	
	protected Input[] inputs;
	protected Data data;

	private boolean isRunning;
	protected long tickDelay;
	
	public Data getData() { return this.data; }
	protected abstract void setData();
	
	public  Monitor(Input... inputs) {
		this.tickDelay = 200; // delay between calls of the tick() function, in milliseconds

		this.inputs = inputs;
		this.data = new Data();
	}
	
	public void setTickDelay(long tickDelay) { this.tickDelay = tickDelay; }
	
	// These methods get called when the Runnable Monitor gets started or stopped.
	public abstract void onStart();
	public abstract void onStop();
	
	// This method gets called periodically.
	public abstract void tick();
	
	public void stop() {
		this.isRunning = false;
	}
	
	public void run() {
		this.isRunning = true;
		
		onStart();
		
		while(this.isRunning) {
			// Run the abstract tick function (this should be implemented by all Monitors)
			this.tick();
			
			try {
				// Try and synchronize with the main DS event queue
				// (update at 50Hz, which means sleep about 200ms) (not lag-safe, but whatever)
				Thread.sleep(this.tickDelay > 0 ? this.tickDelay : 200);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		onStop();
	}
	
}
