package com.swacorp.paris.archivetool.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class Utilities {
	
	public static String noNull(String str) {

		return str != null ? str : "";
	}

	public static boolean isNullOrBlank(String str) {

		return (str == null || str.isEmpty());
	}
	
	public static int getInteger(String rawNumber, int defaultValue) {
		
		if (rawNumber != null) {
			try {
				return Integer.parseInt(rawNumber);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		return defaultValue;
	}
	
	public static String convertDateToSQLDate(java.util.Date fieldValue) {

		StringBuilder formattedDate = new StringBuilder();
		if (fieldValue != null) {
			formattedDate.append("to_date('");
			formattedDate.append(new SimpleDateFormat("MM/dd/yyyy").format(fieldValue));
			formattedDate.append("', 'mm/dd/yyyy')");
		} else
			formattedDate.append("null");
		
		return formattedDate.toString();
	}
	
	public static String getStackTraceAsString(Throwable t) {

		ByteArrayOutputStream traceStream = new ByteArrayOutputStream();
		PrintWriter out = new PrintWriter(traceStream);
		t.printStackTrace(out);
		out.flush();

		return traceStream.toString();
	}

}
