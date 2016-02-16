package com.swacorp.paris.archivetool.domain;

import java.util.Date;

public interface DataContainer extends Comparable<DataContainer>{
	
	public void setTicketNumber(String ticketNumber); 
	public String getTicketNumber();
	public void setDocumentNumber(String docNumber);
	public String getDocumentNumber();
	public void setId(int id);
	public int getId();
	public void setPeriodDate(Date periodDate);
	public Date getPeriodDate();
	public void setDocumentDate(Date docDate);
	public Date getDocumentDate();
	public void setFileDate(Date fileDate);
	public Date getFileDate();
}
