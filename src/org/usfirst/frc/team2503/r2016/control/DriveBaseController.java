package org.usfirst.frc.team2503.r2016.control;

import org.json.JSONObject;

public class DriveBaseController implements Controller {

	public JSONObject DATA;
	public ControlMode mode;
	
	public ControlMode getMode() {
		return this.mode;
	}
	
	public void setMode(ControlMode mode) {
		this.mode = mode;
	}
	
	public void tick() {
		JSONObject driveBaseCommands = new JSONObject();
		
		switch(this.mode) {
		case AUTONOMOUS_DEFAULT:
		case TELEOPERATED_DEFAULT:
			driveBaseCommands.put("left", DATA.get("leftY"));
			driveBaseCommands.put("right", DATA.get("rightY"));
			break;
			
		default:
			driveBaseCommands.put("left", 0.0);
			driveBaseCommands.put("right", 0.0);
		}
		
		DATA.put("driveBase", driveBaseCommands);
	}
	
	public DriveBaseController(JSONObject commands, ControlMode mode) {
		this.DATA = commands;
		this.mode = mode;
	}

}
