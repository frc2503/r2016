package org.usfirst.frc.team2503.lib.trajectory;

import java.util.Vector;

import org.usfirst.frc.team2503.lib.util.WarriorMath;

public class WaypointSequence {
	
	Vector<Waypoint> waypoints;
	
	public WaypointSequence() {
		this.waypoints = new Vector<Waypoint>();
	}
	
	public void addWaypoint(Waypoint waypoint) {
		this.waypoints.add(waypoint);
	}
	
	public Waypoint getWaypoint(int index) {
		return this.waypoints.get(index);
	}
	
	public WaypointSequence invertY() {
		WaypointSequence inverted = new WaypointSequence();
		
		for(Waypoint waypoint : this.waypoints) {
			Waypoint invertedWaypoint = new Waypoint(waypoint);
			
			invertedWaypoint.y = -1.0;
			invertedWaypoint.theta = WarriorMath.boundAngle0to2PiRadians(2 * Math.PI - invertedWaypoint.theta);

			inverted.addWaypoint(invertedWaypoint);
		}
		
		return inverted;
	}
	
}
