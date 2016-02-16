package com.swacorp.paris.archivetool.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.swacorp.paris.archivetool.domain.DataContainer;
import com.swacorp.paris.archivetool.domain.DataContainerImpl;
import com.swacorp.paris.archivetool.helpers.DataHelper;
import com.swacorp.paris.archivetool.util.Utilities;

public class DataRetrievalFactory {
	
	private final String tableName = "SIRAX_EXTRACTS_ADMIN.FI";
	private final String parameterList = "TKT_NUM, FI_DOC_NUM, FI_PERIOD_DATE, SIRAX_DOC_DATE, FI_ID, FILE_DATE";
	
	private static Logger logger = Logger.getLogger(DataRetrievalFactory.class.getName());
	
	private String getTable(){
		return tableName;
	}

	private String getParameterList(){
		return parameterList;
	}

	public List<DataContainer> findData(DataHelper reportHelper) throws Exception {
		
		logger.finest("Entering");
		long start = System.currentTimeMillis();
		List<DataContainer> dataList = null;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		
		try{
			connection = DatabaseConnection.getConnection();
			
			if(connection != null){
				
				StringBuilder sql = new StringBuilder("SELECT " + getParameterList() + " FROM " + getTable() + " WHERE ");
				
				if(!Utilities.isNullOrBlank(reportHelper.getTicketNumber()))				
					sql.append("TKT_NUM IN (" + reportHelper.getTicketNumber() + ")");

				else {
					boolean addAnd = false;
					if(reportHelper.getDocumentDateFrom() != null && reportHelper.getDocumentDateTo() != null) {
						sql.append("SIRAX_DOC_DATE BETWEEN " + Utilities.convertDateToSQLDate(reportHelper.getDocumentDateFrom()) + " AND " + Utilities.convertDateToSQLDate(reportHelper.getDocumentDateTo()));
						addAnd = true;
					}
					
					if(reportHelper.getPeriodDateFrom() != null && reportHelper.getPeriodDateTo() != null)
						sql.append((addAnd ? " AND " : " ") + "FI_PERIOD_DATE BETWEEN " + Utilities.convertDateToSQLDate(reportHelper.getPeriodDateFrom()) + " AND " + Utilities.convertDateToSQLDate(reportHelper.getPeriodDateTo()));
				}
				
				logger.info("SQL: "+sql.toString());
				
				statement = connection.createStatement();
				results = statement.executeQuery(sql.toString());
				
				dataList = new ArrayList<DataContainer>();
				
				while(results.next()){
					
					DataContainer tempContainer = new DataContainerImpl();

					tempContainer.setTicketNumber(results.getString("TKT_NUM"));
					tempContainer.setDocumentNumber(results.getString("FI_DOC_NUM"));
					tempContainer.setId(results.getInt("FI_ID"));
					tempContainer.setDocumentDate(results.getDate("SIRAX_DOC_DATE"));
					tempContainer.setPeriodDate(results.getDate("FI_PERIOD_DATE"));
					tempContainer.setFileDate(results.getDate("FILE_DATE"));
					
					dataList.add(tempContainer);
				}
				
				//Testing memory utilisation
				/*Runtime.getRuntime().runFinalization();
				
				logger.fine("total memory - "+ Runtime.getRuntime().totalMemory() );
				logger.fine("free memory - "+ Runtime.getRuntime().freeMemory() );
				logger.fine("max memory - "+ Runtime.getRuntime().maxMemory() );*/
			}
		}catch(Exception ex){
			logger.severe("Caught error - "+ ex.getMessage());	
			throw ex;
		
		}finally{
			try{
				if(results != null)
					results.close();
				
				if(statement != null)
					statement.close();
				
				if(connection != null)
					connection.close();
				
			}catch(SQLException ex){
				logger.severe("Caught error - "+ ex.getMessage());
				throw new DataBaseException("Unable to retrieve data.");
			}
			logger.finest("Data Retrieved in: " +(System.currentTimeMillis()-start) + " Milli Seconds" );
		}
		return dataList;		
	}
}
