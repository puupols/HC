package controller;

import java.util.Date;
import database.DataBaseService;
import pojo.HeaterSwitch;
import pojo.Temperature;

public class TemperatureService {

	public DataBaseService db;
	private static Temperature desiredTemperature;
	private static Temperature temperatureThershold;		
	
	public static TemperatureService instance;
		
	public TemperatureService() {
		ConfigurationService configurationService = ConfigurationService.getConfigurationService();		
		Double defaultDesiredTemperature = configurationService.getPropertyAsDouble("DESIRED_TEMPERATURE");
		Double defaultTemperatureTrashold = configurationService.getPropertyAsDouble("TEMPERATURE_TRASHOLD");		
		
		db = new DataBaseService();		
		desiredTemperature = new Temperature();
		desiredTemperature.setValue(defaultDesiredTemperature);		
		temperatureThershold = new Temperature();
		temperatureThershold.setValue(defaultTemperatureTrashold);				
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
	
	public HeaterSwitch calculateHeaterStatus() {		
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
		return heaterSwitch;
	}
	
	public HeaterSwitch storeHeaterStatus(HeaterSwitch heaterSwitch) {
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
