package pojo;

import java.util.Date;

import pojo.switchStatus;

public class Switch {
	private switchStatus status;
	private Date logDate;
	
	
	public switchStatus getStatus() {
		return this.status;
	}
	public void setStatus(switchStatus status) {
		this.status = status;
	}
	public Date getLogDate() {
		return this.logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
}
