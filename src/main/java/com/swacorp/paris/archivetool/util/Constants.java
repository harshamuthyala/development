package com.swacorp.paris.archivetool.util;

public final class Constants {
	
	//Excel template location properties
	public static final String EXCEL_SOURCE_PATH = "/opt/catlogs/legacy_finance_ste/ArchiveTool-A";

	//DB properties
	public static final String DB_USER_NAME = "SIRAX_EXTRACTS_USER"; 
	public static final String DB_USER_PASSWORD = "SIRAX_EXTRACTS_USER99";
	public static final String DB_CONNECTION_URL = "jdbc:oracle:thin:@xlssap03-vip:1600/PY1.swacorp.com";
	
	//Logging properties
	public static final boolean LOGGING_CONSOLE_DISABLE = true;
	public static final boolean LOGGING_APPEND = true;	
	//public static final String LOGGING_FILE_LOCATION = "/opt/catlogs/legacy_finance_ste/ArchiveTool-A";
	public static final String LOGGING_FILE_LOCATION = "C:\\HCL Archieve Tool\\";
	public static final String LOGGING_FILE_NAME = "archive_log%g.log";
	public static final int LOGGING_FILE_SIZE = 10000000;
	public static final int LOGGING_NUMER_OF_FILES = 10;
	public static final String APM_URL= "https://archivetool.qa1.swacorp.com/v1/ArchiveTool/";
}
