package org.usfirst.frc.team2503.r2016.control;

import java.util.Date;

public class Ticker implements Runnable {

	@Override
	public void run() {
		while(true) {
			long dt0 = new Date().getTime();
			
			this._tickable.tick();
			
			long dt1 = new Date().getTime();
			
			long d = dt1 - dt0;
			
			try {
				Thread.sleep(this._periodMilliseconds - d);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Tickable _tickable;
	private long _periodMilliseconds;
	
	public Ticker(Tickable _tickable, long periodMilliseconds) {
		this._tickable = _tickable;
		this._periodMilliseconds = periodMilliseconds;
	}

}
