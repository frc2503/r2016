package org.usfirst.frc.team2503.r2016.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class Logger {
	
	public static class LoggerPrintStream extends PrintStream {
		
		public LoggerPrintStream(File file) throws FileNotFoundException {
			super(file);
		}
		
		public LoggerPrintStream(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
			super(file, csn);
		}
		
		public LoggerPrintStream(OutputStream out) {
			super(out);
		}
		
		public LoggerPrintStream(OutputStream out, boolean autoFlush) {
			super(out, autoFlush);
		}
		
		public LoggerPrintStream(OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
			super(out, autoFlush, encoding);
		}
		
		public LoggerPrintStream(String fileName) throws FileNotFoundException {
			super(fileName);
		}
		
		public LoggerPrintStream(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
			super(fileName, csn);
		}
		
	}
	
	private static Vector<LoggerPrintStream> printStreams = new Vector<LoggerPrintStream>();
	
	public static void addPrintStream(LoggerPrintStream ps) {
		printStreams.add(ps);
	}
	
	public static void removePrintStream(LoggerPrintStream ps) {
		printStreams.remove(ps);
	}
	
	public static void print(Object x) { for(LoggerPrintStream ps : printStreams) { ps.print(x); } }
	public static void println(Object x) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		
		StackTraceElement caller = stackTraceElements[2];
		
		for(LoggerPrintStream ps : printStreams) {
			String prefix = "[(" + caller.getFileName() + ":" + caller.getLineNumber() + ") <" + caller.getMethodName() + ">] ";
			
			ps.println(prefix + x);
		}
	}

}
