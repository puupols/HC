package controller;

import java.util.Date;
import database.DataBaseService;
import pojo.HeaterSwitch;
import pojo.Temperature;
import pojo.switchStatus;



public class TemperatureService {

	public ConfigurationService configurationService;
	public DataBaseService db;	
	public static TemperatureService instance;
		
	public TemperatureService() {
		
		configurationService = ConfigurationService.getConfigurationService();		
		db = new DataBaseService();					
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
	
	public HeaterSwitch getCalculatedHeaterStatus() {
		
		HeaterSwitch heaterSwitch = new HeaterSwitch();
		Date logDate = new Date();		
		heaterSwitch.setLogDate(logDate);
		
		Temperature currentTemperature = getLastTemperature();
		Temperature desiredTemperature = getDesiredTemperature();
		Temperature temperatureThreshold = getTemperatureThreshold();
		HeaterSwitch lasetHeaterSwitchStatus = getLastSwitchStatus();
				
		if(desiredTemperature.getValue() - currentTemperature.getValue() > temperatureThreshold.getValue()) {
			heaterSwitch.setStatus(switchStatus.ON);			
		} else if(lasetHeaterSwitchStatus.getStatus() == switchStatus.ON && currentTemperature.getValue() - desiredTemperature.getValue() < temperatureThreshold.getValue()){
			heaterSwitch.setStatus(switchStatus.ON);			
		} else {
			heaterSwitch.setStatus(switchStatus.OFF);			
		}		
		return heaterSwitch;
	}	
	
	public HeaterSwitch storeHeaterStatus(HeaterSwitch heaterSwitch) {
		db.saveSwitchStatus(heaterSwitch);
		return heaterSwitch;
	}
	
	public Temperature getLastTemperature() {		
		return db.getLastTemperature();
	}
	
	public HeaterSwitch getLastSwitchStatus() {
		HeaterSwitch heaterSwitchStatus = new HeaterSwitch();
		heaterSwitchStatus = db.getLastSwitchStatus();
		return heaterSwitchStatus;
	}
	
	public Temperature getDesiredTemperature() {
		Temperature desiredTemperature = new Temperature();
		//ToDo get from DB
		desiredTemperature.setValue(configurationService.getPropertyAsDouble("DESIRED_TEMPERATURE"));
		return desiredTemperature;
	}	

	
	public Temperature getTemperatureThreshold() {
		Temperature temperatureThreshold = new Temperature();
		//ToDO get from DB
		temperatureThreshold.setValue(configurationService.getPropertyAsDouble("TEMPERATURE_TRASHOLD"));
		return temperatureThreshold;
	}
	
}
