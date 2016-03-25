package org.usfirst.frc.team2503.r2016.subsystem;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SD540;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public abstract class SpeedControllerSubsystem implements Subsystem, SpeedController {
	
	public enum SpeedControllerSubsystemType {
		CAN_JAGUAR, CAN_TALON, JAGUAR, SD540, SPARK, TALON, TALON_SRX, VICTOR, VICTOR_SP
	}
	
	private SpeedController _controller;
	
	public double get() { return _controller.get(); }
	public void set(double speed, byte syncGroup) { _controller.set(speed, syncGroup); }
	public void set(double speed) { _controller.set(speed); }
	public void setInverted(boolean isInverted) { _controller.setInverted(isInverted); }
	public boolean getInverted() { return _controller.getInverted(); }
	public void disable() { _controller.disable(); }
	public void stopMotor() { _controller.stopMotor(); }
	public void pidWrite(double output) { _controller.pidWrite(output); }

	public SpeedControllerSubsystem(SpeedController _controller) {
		this._controller = _controller;
	}
	
	public SpeedControllerSubsystem(SpeedControllerSubsystemType type, final int channel) {
		switch(type) {
		case CAN_JAGUAR:
			this._controller = new CANJaguar(channel);
			break;
			
		case CAN_TALON:
			this._controller = new CANTalon(channel);
			break;
			
		case JAGUAR:
			this._controller = new Jaguar(channel);
			break;
			
		case SD540:
			this._controller = new SD540(channel);
			break;
			
		case SPARK:
			this._controller = new Spark(channel);
			break;
			
		case TALON:
			this._controller = new Talon(channel);
			break;
			
		case TALON_SRX:
			this._controller = new TalonSRX(channel);
			break;
			
		case VICTOR:
			this._controller = new Victor(channel);
			break;
			
		case VICTOR_SP:
			this._controller = new VictorSP(channel);
			break;

		default:
			break;
		}
	}
	
}
