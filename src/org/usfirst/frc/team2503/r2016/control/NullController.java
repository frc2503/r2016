package org.usfirst.frc.team2503.r2016.control;

import org.usfirst.frc.team2503.r2016.input.NullMonitor;

public class NullController extends Controller {

	@Override
	public CommandStack getCommands() {
		CommandStack stack = new CommandStack();
		
		Command command = new Command();
	
		command.put("do", "nothing");
		
		stack.push(command);
		
		return getCommands();
	}

}
