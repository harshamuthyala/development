package com.swacorp.paris.archivetool.domain;

import java.util.Date;

public class DataContainerImpl implements DataContainer {

	private String ticketNumber;
	private String documentNumber;
	private int id;
	private Date periodDate;
	private Date documentDate;
	private Date fileDate;

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public Date getFileDate() {
		return fileDate;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	public int compareTo(DataContainer o) {

		if (this.id > o.getId())
			return 1;

		if (this.id > o.getId())
			return -1;

		return 0;
	}

}
