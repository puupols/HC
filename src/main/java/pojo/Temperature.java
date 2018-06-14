package pojo;

import java.util.Date;

public class Temperature {
	private double value;
	private Date readDate;
	
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public Date getReadDate() {
		return this.readDate;
	} 
	
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
}
