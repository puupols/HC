package pojo;

import java.util.Date;

public class Temperature {
	private double value;
	private Date logDate;
	
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public Date getLogDate() {
		return this.logDate;
	} 
	
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
}
