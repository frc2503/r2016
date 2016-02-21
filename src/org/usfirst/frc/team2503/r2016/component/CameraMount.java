package org.usfirst.frc.team2503.r2016.component;

import edu.wpi.first.wpilibj.Servo;

public class CameraMount extends Component {

	public Servo horizontal;
	public Servo vertical;
	
	public double[] horizontalAngleMap;
	public double[] verticalAngleMap;
	 
	public void set(double horizontal, double vertical) {
		this.horizontal.set(horizontal);
		this.vertical.set(vertical);
	}
	
	public CameraMount(Servo horizontal, Servo vertical) {
		this.horizontal = horizontal;
		this.vertical = vertical;
	}
	
}
