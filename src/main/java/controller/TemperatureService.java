package controller;

import java.util.Date;

import database.DataBaseService;
import pojo.HeaterSwitch;
import pojo.Temperature;

public class TemperatureService {
	
	private static final double defaultDesiredTemperature = 21;
	private static final double defaultTemperatureTrashold = 0.5;
	
	private static Temperature desiredTemperature;
	private static Temperature temperatureThershold;		
	
	public static TemperatureService instance;
	
	DataBaseService db = new DataBaseService();
	
	public TemperatureService() {
		Date date = new Date();
		desiredTemperature = new Temperature();
		desiredTemperature.setValue(defaultDesiredTemperature);
		desiredTemperature.setLogDate(date);
		temperatureThershold = new Temperature();
		temperatureThershold.setValue(defaultTemperatureTrashold);
		temperatureThershold.setLogDate(date);		
	}
		
	public static TemperatureService get() {		
			if(instance == null) {
				instance = new TemperatureService();
			}		
		return instance;
	}
	
	public Temperature storeTemperature(Temperature temperature) {		
		db.saveTemperature(temperature);
		return temperature;
	}
	
	public HeaterSwitch getHeaterStatus() {
		
		HeaterSwitch heaterSwitch = new HeaterSwitch();
		Date logDate = new Date();		
		heaterSwitch.setLogDate(logDate);
		Temperature currentTemperature = db.getLastTemperature();
		if(desiredTemperature.getValue() - currentTemperature.getValue() > temperatureThershold.getValue()) {
			heaterSwitch.setStatus("ON");
			return heaterSwitch;
		} else if(heaterSwitch.getStatus() == "ON" && currentTemperature.getValue() - desiredTemperature.getValue() < temperatureThershold.getValue()){
			heaterSwitch.setStatus("ON");
			return heaterSwitch;
		} else {
			heaterSwitch.setStatus("OFF");
			return heaterSwitch;
		}
	}
	
	public Temperature getCurrentTemperature() {		
		return db.getLastTemperature();
	}
	
	public Temperature getDesiredTemperature() {
		return desiredTemperature;
	}	
		
	public Temperature setDesiredTemperature(Temperature temperature) {
		desiredTemperature.setValue(temperature.getValue());
		desiredTemperature.setLogDate(temperature.getLogDate());
		return desiredTemperature;
	}
	
	public Temperature getTemperatureThershold() {
		return temperatureThershold;
	}

	public Temperature setTemperatureThershold(Temperature temperature) {
		temperatureThershold.setValue(temperature.getValue());
		temperatureThershold.setLogDate(temperature.getLogDate());
		return temperatureThershold;
	}	
}
