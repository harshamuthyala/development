package com.swacorp.paris.archivetool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import oracle.jdbc.OracleDriver;

import com.swacorp.paris.archivetool.util.Constants;

public class DatabaseConnection {

	private static Logger logger = Logger.getLogger(DatabaseConnection.class
			.getName());

	public static Connection getConnection() throws DataBaseException {

		Connection connection = null;

		//String DB_CONNECTION_URL = null;

		try {
			DriverManager.registerDriver(new OracleDriver());
			connection = DriverManager.getConnection(
					Constants.DB_CONNECTION_URL, Constants.DB_USER_NAME,
					Constants.DB_USER_PASSWORD);

			/*String hostname = System.getenv().get("HOSTNAME");

			if (hostname != null && !hostname.trim().equals("")) {

				if ((hostname).equalsIgnoreCase("xldcat03")) {
					DB_CONNECTION_URL = "jdbc:oracle:oci:@DC1";
				}

				else if (hostname.equalsIgnoreCase("xlqcat17")) {
					DB_CONNECTION_URL = "jdbc:oracle:oci:@QY5";
				}

				else if (hostname.equalsIgnoreCase("xlpcat22")) {
					DB_CONNECTION_URL = "jdbc:oracle:oci:@PY1";
				}

				
				 * else if(hostname.equalsIgnoreCase("SY4-91RMXR1")){
				 * DB_CONNECTION_URL = "jdbc:oracle:oci:@QY5"; }
				 
			} else {
				logger.severe("Invalid Host for DB Connection");
			}

			if (DB_CONNECTION_URL != null && !DB_CONNECTION_URL.trim().equals("")) {
				DriverManager.registerDriver(new OracleDriver());
				connection = DriverManager.getConnection(DB_CONNECTION_URL,
						Constants.DB_USER_NAME, Constants.DB_USER_PASSWORD);
			} else {
				logger.severe("Connection Failed");
			}*/

		} catch (SQLException ex) {
			logger.severe("Connection Failed - " + ex.getMessage());
			ex.printStackTrace();
			throw new DataBaseException(ex.getMessage());
		}
		return connection;
	}
}
