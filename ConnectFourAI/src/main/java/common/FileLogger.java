package common;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;

public class FileLogger {

	private static FileLogger instance = new FileLogger();
	private static Writer logWriter;
	
	public FileLogger() {
	}
	
	public static FileLogger getInstance() {
	      if(instance == null) {
	         instance = new FileLogger();
	      }
	      return instance;
	}
	
	public void init(String playerName){
		try {
			logWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(playerName + "_log.txt"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		println("logger initialized");
	}
	
	/**
	 * Writes the given message to the appropriate log file
	 * 
	 * @param msg
	 */
	public void println(String msg) {
		try {
			logWriter.write(msg + "\n");
			logWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printBoard(Board board){
		int[][] gameBoard = board.getBoard();
		for (int[] row : gameBoard){
			println(Arrays.toString(row));
		}
	}

}
