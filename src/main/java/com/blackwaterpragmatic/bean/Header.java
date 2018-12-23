package com.blackwaterpragmatic.bean;

public class Header {
	private String logType;
	private Integer version;
	private Integer numberOfRecords;

	public String getLogType() {
		return logType;
	}

	public void setLogType(final String logType) {
		this.logType = logType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(final Integer version) {
		this.version = version;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(final Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}


}
