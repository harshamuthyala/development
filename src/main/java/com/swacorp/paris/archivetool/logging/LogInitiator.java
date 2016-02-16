package com.swacorp.paris.archivetool.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.swacorp.paris.archivetool.util.Constants;

public class LogInitiator {
	
	public LogInitiator(){
		super();
	}
	
	public static void initialize( Logger logger ){
			
		Logger rootLogger = logger;
		
		String logLocation = "/logs/";
		String pattern = "archieve%g.log";
		int limit = 1000000; // Approx 1 MB
		int numLogFiles = 5;
	
		if (Constants.LOGGING_FILE_LOCATION != null && Constants.LOGGING_FILE_LOCATION.length() > 0) {
			logLocation = Constants.LOGGING_FILE_LOCATION;
			System.out.println("Overriding logging file location property to " + logLocation);
		}
		
		if (Constants.LOGGING_FILE_NAME != null && Constants.LOGGING_FILE_NAME.length() > 0) {
			pattern = Constants.LOGGING_FILE_NAME;
			//System.out.println("Overriding logging filename property to " + pattern);
		}
	
		if (Constants.LOGGING_FILE_SIZE > 0) {
			limit = Constants.LOGGING_FILE_SIZE;
			//System.out.println("Overriding logging max file size property to " + limit);
		}
	
		if (Constants.LOGGING_NUMER_OF_FILES > 0) {
			numLogFiles = Constants.LOGGING_NUMER_OF_FILES;
			//System.out.println("Overriding logging number of files property to " + numLogFiles);
		}
	
		while (rootLogger.getParent() != null)
			rootLogger = rootLogger.getParent();
	
		if (Constants.LOGGING_CONSOLE_DISABLE) {
			System.out.println("Console logging is disabled - see log file.");
		} else {
	
			ConsoleHandler handler = new ConsoleHandler();
			handler.setLevel(Level.ALL);
			handler.setFormatter(new LogFormatter());
			rootLogger.addHandler(handler);
		}
		try {
			FileHandler fileHandler = new FileHandler(logLocation + pattern, limit, numLogFiles, Constants.LOGGING_APPEND);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(new LogFormatter());
			rootLogger.addHandler(fileHandler);
	
		} catch (SecurityException e) {
			System.err.println("Security exception while opening logging file: " + e.toString());
			e.printStackTrace(System.err);
		} catch (IOException e) {
			System.err.println("IO exception while opening logging file: " + e.toString());
			e.printStackTrace(System.err);
		}
	}

}
