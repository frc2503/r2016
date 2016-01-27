package org.usfirst.frc.team2503.r2016.input;

public class NullInput extends Input {

	public Data getData() {
		Data data = new Data();
		
		data.put("null", null);
		
		return data;	
	}
	
}
