package controller;

import java.util.Date;
import java.util.Properties;

import database.DataBaseService;
import pojo.HeaterSwitch;
import pojo.Temperature;

public class TemperatureService {

	public DataBaseService db;
	private static Temperature desiredTemperature;
	private static Temperature temperatureThershold;		
	
	public static TemperatureService instance;
		
	public TemperatureService() {
		ConfigurationService configurationService = new ConfigurationService();
		Properties properties = configurationService.getProperties();
		String defaultDesiredTemperature = properties.getProperty("DESIRED_TEMPERATURE");
		String defaultTemperatureTrashold = properties.getProperty("TEMPERATURE_TRASHOLD");		
		
		db = new DataBaseService();		
		desiredTemperature = new Temperature();
		desiredTemperature.setValue(Double.parseDouble(defaultDesiredTemperature));		
		temperatureThershold = new Temperature();
		temperatureThershold.setValue(Double.parseDouble(defaultTemperatureTrashold));				
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
		} else if(heaterSwitch.getStatus() == "ON" && currentTemperature.getValue() - desiredTemperature.getValue() < temperatureThershold.getValue()){
			heaterSwitch.setStatus("ON");			
		} else {
			heaterSwitch.setStatus("OFF");			
		}
		db.saveSwitchStatus(heaterSwitch);
		return heaterSwitch;
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
