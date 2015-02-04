/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package common;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import common.board.Board;

public class FileLogger {

	private static FileLogger instance = new FileLogger();
	private static Writer logWriter;

	/** The state of the logger */
	private static boolean active = true;

	private FileLogger() {
	}

	public static FileLogger getInstance() {
		if (instance == null) {
			instance = new FileLogger();
		}
		return instance;
	}

	public void init(String file_name, List<String> args) {
		// Check for the '--no-logs' argument
		if (args.contains("--no-logs")) {
			FileLogger.deactivate();
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

	public void printList(List lst) {
		println(lst.toString());
	}

	/**
	 * Writes a given board to the appropriate log file formatted similiar to
	 * how the referee would print.
	 * 
	 * @param board
	 */
	public void printBoard(Board board) {
		if (!active)
			return;

		int[][] gameBoard = board.getBoard();
		for (int r = board.height - 1; r >= 0; r--) {
			println(Arrays.toString(gameBoard[r]));
		}
	}

	/**
	 * Writes a given board to the appropriate log file formatted similiar to
	 * how the referee would print.
	 * 
	 * @param board
	 */
	public void printBoard(Board board, int depth) {
		println("Depth = " + depth);
		if (!active)
			return;

		int[][] gameBoard = board.getBoard();
		for (int[] row : gameBoard) {
			println(Arrays.toString(row));
		}
	}

	/**
	 * Activates the logging system
	 */
	public static void activate() {
		FileLogger.active = true;
	}

	/**
	 * Deactivates the logging system
	 */
	public static void deactivate() {
		FileLogger.active = false;
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

	/**
	 * Print a move value, column and type
	 * 
	 * @param m
	 * @param depth
	 */
	public void printMove(MoveHolder m, int depth) {
		println(m.getValue() + " " + m.getCol() + " " + m.getMove(), depth);
	}

	/* The following functions are utilities specific to the minimax algorithm */
	// TODO asynchronously generate a tree...
	// TODO generate a tree image..

}
