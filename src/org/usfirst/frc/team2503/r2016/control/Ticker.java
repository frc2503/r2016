package org.usfirst.frc.team2503.r2016.control;

import java.util.Date;

public class Ticker implements Runnable {

	private Tickable _tickable;
	private long _periodMilliseconds;
	private long _periodLength;
	
	@Override
	public void run() {
		while(true) {
			long dt0 = new Date().getTime();
			
			this._tickable.tick();
			
			long dt1 = new Date().getTime();
			
			this._periodLength = dt1 - dt0;
			
			try {
				Thread.sleep(this._periodMilliseconds - this._periodLength);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public long getPreviousPeriodLength() {
		return this._periodLength;
	}
	
	public Ticker(Tickable _tickable, long periodMilliseconds) {
		this._tickable = _tickable;
		this._periodMilliseconds = periodMilliseconds;
	}

}
