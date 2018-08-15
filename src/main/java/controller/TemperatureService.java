package controller;

import java.util.Date;
import database.DataBaseService;
import pojo.Switch;
import pojo.Temperature;
import pojo.switchStatus;



public class TemperatureService {

	public ConfigurationService configurationService;
	public DataBaseService dataBaseService;	
		
	public TemperatureService(DataBaseService dataBaseService,ConfigurationService configurationService) {		
		this.configurationService = configurationService;		
		this.dataBaseService = dataBaseService;					
	}
			
	public Temperature storeTemperature(Temperature temperature) {		
		dataBaseService.saveTemperature(temperature);
		return temperature;
	}
	
	public Switch getCalculatedHeaterStatus() {
		
		Switch heaterSwitch = new Switch();
		Date logDate = new Date();		
		heaterSwitch.setLogDate(logDate);
		
		Temperature currentTemperature = getLastTemperature();
		Temperature desiredTemperature = getDesiredTemperature();
		Temperature temperatureThreshold = getTemperatureThreshold();
		Switch lasetHeaterSwitchStatus = getLastSwitchStatus();
				
		if(desiredTemperature.getValue() - currentTemperature.getValue() > temperatureThreshold.getValue()) {
			heaterSwitch.setStatus(switchStatus.ON);			
		} else if(lasetHeaterSwitchStatus.getStatus() == switchStatus.ON && currentTemperature.getValue() - desiredTemperature.getValue() < temperatureThreshold.getValue()){
			heaterSwitch.setStatus(switchStatus.ON);			
		} else {
			heaterSwitch.setStatus(switchStatus.OFF);			
		}		
		return heaterSwitch;
	}	
	
	public Switch storeHeaterStatus(Switch heaterSwitch) {
		dataBaseService.saveSwitchStatus(heaterSwitch);
		return heaterSwitch;
	}
	
	public Temperature getLastTemperature() {		
		return dataBaseService.getLastTemperature();
	}
	
	public Switch getLastSwitchStatus() {
		return dataBaseService.getLastSwitchStatus();		
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
