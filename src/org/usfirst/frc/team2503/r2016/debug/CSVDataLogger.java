package org.usfirst.frc.team2503.r2016.debug;

import java.io.PrintWriter;

public class CSVDataLogger {
	
	public static class Entry {
		public String toString() {
			String[] strings = new String[this._objects.length];
			
			for(int i = 0; i < this._objects.length; i += 1) {
				strings[i] = this._objects[i].toString();
			}
			
			return String.join(",", strings);
		}
		
		private Object[] _objects;
		
		public Entry(Object... objects) {
			this._objects = objects;
		}
	}

	public void log(Entry entry) {
		this._writer.println(entry.toString());
		this._writer.flush();
	}
	
	private PrintWriter _writer;
	
	public CSVDataLogger(PrintWriter writer) {
		this._writer = writer;
	}

}
