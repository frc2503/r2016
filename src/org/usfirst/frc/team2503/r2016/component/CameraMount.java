package org.usfirst.frc.team2503.r2016.component;

import org.usfirst.frc.team2503.lib.util.WarriorMath;
import org.usfirst.frc.team2503.r2016.input.Data;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;

public class CameraMount extends Component {

	public static class CameraLights extends Relay {

		public CameraLights(int channel) {
			super(channel);
		}

		public CameraLights(int channel, Direction direction) {
			super(channel, direction);
		}

	}

	public enum CameraMountMode {
		UNKNOWN,
		LOOKING,
		TARGETING,
		INTAKE,
		BACKUP,
		STRAIGHT_FORWARD
	}

	public CameraLights lights;

	public Servo horizontal;
	public Servo vertical;

	private double x;
	private double y;

	private CameraMountMode mode = CameraMountMode.UNKNOWN;

	public void setMode(CameraMountMode mode) { this.mode = mode; }
	public CameraMountMode getMode() { return this.mode; }

	public void tweak(double horizontal, double vertical) {
		this.x = WarriorMath.gate(0.0, (this.x + (horizontal * 0.01)), 1.0);
		this.y = WarriorMath.gate(0.0, (this.y + (vertical * 0.01)), 1.0);
		//		this.x = Math.max(0.0, Math.min(1.0, this.x + (horizontal * 0.01)));
		//		this.y = Math.max(0.0, Math.min(1.0, this.y + (vertical * 0.01)));
	}

	public void tick(Data data) {
		switch(this.mode) {
		case LOOKING:
			/* this.x and this.y should already have been set by tweak(), so
			 * don't modify them.
			 */
			this.lights.set(Relay.Value.kOn);
			break;

		case INTAKE:
			this.lights.set(Relay.Value.kOn);
			this.x = 1.0;
			this.y = 0.2;
			break;

		case BACKUP:
			this.lights.set(Relay.Value.kOn);
			this.x = 0.0;
			this.y = 0.75;
			break;

		case STRAIGHT_FORWARD:
			this.lights.set(Relay.Value.kOn);
			this.x = 1.0;
			this.y = 0.4;
			break;

		case TARGETING:
			this.lights.set(Relay.Value.kOn);
			this.x = 1.0;
			this.y = 0.55;
			break;

		case UNKNOWN:
		default:
			this.lights.set(Relay.Value.kOff);
			this.x = 1.0;
			this.y = 0.4;
			break;
		}

		this.horizontal.set(this.x);
		this.vertical.set(this.y - 0.05d);
	}

	public CameraMount(Servo horizontal, Servo vertical, CameraLights lights) {
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.lights = lights;
	}

}
