package rest;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Temperature {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;	
	private double value;
	private Date logDate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
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
