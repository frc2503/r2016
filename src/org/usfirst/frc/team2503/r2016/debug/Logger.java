package org.usfirst.frc.team2503.r2016.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Logger {

	public static class LoggerChannelDoesNotExistException extends Exception {

		private static final long serialVersionUID = 1L;

	}

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

	private static JSONObject printStreams = new JSONObject();

	public static void addPrintStream(String channelName, LoggerPrintStream ps) {
		JSONArray channel;

		try {
			channel = printStreams.getJSONArray(channelName);
			channel.put(ps);
		} catch(JSONException e) {
			channel = new JSONArray();
			channel.put(ps);
		}

		printStreams.put(channelName, channel);
	}

	public static void print(String channelName, Object x) {
		JSONArray channel = printStreams.getJSONArray(channelName);

		LoggerPrintStream nextPrintStream = null;
		Iterator<Object> iterator = channel.iterator();

		while(iterator.hasNext()) {
			nextPrintStream = (LoggerPrintStream) iterator.next();
			nextPrintStream.print(x);
		}
	}

	protected static void _println(String channelName, Object x, boolean prefix) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement caller = stackTraceElements[3];

		String prefixString = (prefix ? "[(" + caller.getFileName() + ":" + caller.getLineNumber() + ") <" + caller.getMethodName() + ">] " : "");

		JSONArray channel = printStreams.getJSONArray(channelName);

		LoggerPrintStream nextPrintStream = null;
		Iterator<Object> iterator = channel.iterator();
		while(iterator.hasNext()) {
			nextPrintStream = (LoggerPrintStream) iterator.next();
			nextPrintStream.println(prefixString + x);
		}
	}

	public static void println(String channelName, Object x) {
		_println(channelName, x, true);
	}

	public static void println(String channelName, Object x, boolean prefix) {
		_println(channelName, x, prefix);
	}

}
