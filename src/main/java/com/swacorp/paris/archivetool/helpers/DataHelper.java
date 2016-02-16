package com.swacorp.paris.archivetool.helpers;

import java.util.Date;
import java.util.List;

import com.swacorp.paris.archivetool.domain.DataContainer;

public class DataHelper {

	private List<DataContainer> dataContainers;
	private Date documentDateFrom;
	private Date documentDateTo;
	private Date periodDateFrom;
	private Date periodDateTo;
	private String ticketNumber;

	public List<DataContainer> getDataContainers() {
		return dataContainers;
	}

	public void setDataContainers(List<DataContainer> reportContainers) {
		this.dataContainers = reportContainers;
	}

	public Date getDocumentDateFrom() {
		return documentDateFrom;
	}

	public void setDocumentDateFrom(Date documentDateFrom) {
		this.documentDateFrom = documentDateFrom;
	}

	public Date getDocumentDateTo() {
		return documentDateTo;
	}

	public void setDocumentDateTo(Date documentDateTo) {
		this.documentDateTo = documentDateTo;
	}

	public Date getPeriodDateFrom() {
		return periodDateFrom;
	}

	public void setPeriodDateFrom(Date periodDateFrom) {
		this.periodDateFrom = periodDateFrom;
	}

	public Date getPeriodDateTo() {
		return periodDateTo;
	}

	public void setPeriodDateTo(Date periodDateTo) {
		this.periodDateTo = periodDateTo;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}	
}
