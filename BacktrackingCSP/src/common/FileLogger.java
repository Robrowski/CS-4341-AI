package common;
/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

public class FileLogger {

	private Writer logWriter;

	/** The state of the logger */
	private boolean active = true;

	public FileLogger(String file_name, List<String> args) {
		// Check for the '--no-logs' argument
		if (args.contains("--no-logs")) {
			deactivate();
			// Don't return because the logger can still be activated later
		}

		try {
			logWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file_name), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Close up the log writing service */
	public void close() {
		try {
			logWriter.close();
		} catch (IOException e) {
			logException(e);
		}
	}

	/**
	 * Writes the given message to the appropriate log file
	 * 
	 * @param msg
	 */
	public void println(String msg) {
		if (!active)
			return;
		try {
			logWriter.write(msg + "\n");
			logWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the given message to the appropriate log file
	 * 
	 * @param msg
	 */
	public void print(String msg) {
		if (!active)
			return;

		try {
			logWriter.write(msg);
			logWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the given message to the appropriate log file with tabs
	 * 
	 * @param msg
	 *            the message
	 * @param tabs
	 *            the number of tabs
	 */
	public void println(String msg, int tabs) {
		for (int i = 0; i < tabs; i++)
			msg = "    " + msg;
		println(msg);
	}



	/**
	 * Activates the logging system
	 */
	public void activate() {
		active = true;
	}

	/**
	 * Deactivates the logging system
	 */
	public void deactivate() {
		active = false;
	}

	/**
	 * Logs important information from an exception
	 * 
	 * @param e
	 */
	public void logException(Exception e) {
		// TODO throw an exception and see what this stuff actually is...
		println("\n\n A " + e.getClass().toString() + " was caught.");
		println("Localized Description: " + e.getLocalizedMessage());
		println("Message: " + e.getMessage());
		println("Stack trace...");
		for (StackTraceElement st : e.getStackTrace()) {
			println("	" + st.toString());
		}
	}

}
