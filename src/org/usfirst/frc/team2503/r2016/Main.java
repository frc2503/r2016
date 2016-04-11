package org.usfirst.frc.team2503.r2016;

import org.usfirst.frc.team2503.lib.util.WarriorMath;

import org.jruby.embed.ScriptingContainer;

/**
 * You can use this class for testing non-WPIlib code on your machine.
 */
public class Main {
	
	public static void main(String[] args) {
		for(double d = -90.0; d <= 90.0; d += 1.0) {	
			double degrees = WarriorMath.map(-90.0, 90.0, d, 0.0, 1.0);
			System.out.println(degrees);
		}
		
		System.out.println("[jruby] Booting...");
		
		ScriptingContainer container = new ScriptingContainer();
		container.runScriptlet("puts 'hello world'");
		container.runScriptlet("require 'java'; p org.usfirst.frc.team2503.lib.util.WarriorMath.map(-90.0, 90.0, 45.0, 0.0, 1.0)");
		
		System.out.println("[jruby] Shutting down...");
	}
	
}
