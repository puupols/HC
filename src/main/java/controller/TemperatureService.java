package controller;

import java.util.Date;
import database.DataBaseService;
import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;
import pojo.SwitchStatus;



public class TemperatureService {

	public ConfigurationService configurationService;
	public DataBaseService dataBaseService;
	private Temperature desiredTemperature;
	private Temperature currentTemperature;
	private Temperature temperatureThreshold;
	private Switch heaterSwitch;
		
	public TemperatureService(DataBaseService dataBaseService,ConfigurationService configurationService) {		
		this.configurationService = configurationService;		
		this.dataBaseService = dataBaseService;
		desiredTemperature = new Temperature();
		desiredTemperature.setType(TemperatureType.DESIRED);
		currentTemperature = new Temperature();
		currentTemperature.setType(TemperatureType.MEASURED);
		temperatureThreshold = new Temperature();
		temperatureThreshold.setType(TemperatureType.THRESHOLD);
		heaterSwitch = new Switch();
	}
			
	public Temperature storeTemperature(Temperature temperature) {
		switch (temperature.getType()) {
		case MEASURED:
			currentTemperature = temperature;
			break;
		case DESIRED:
			desiredTemperature = temperature;
			break;
		case THRESHOLD:
			temperatureThreshold = temperature;
			break;
		default:
			break;
		}		
		dataBaseService.saveTemperature(temperature);
		return temperature;
	}
	
	public Switch getCalculatedHeaterStatus() {
		
		Switch lastHeaterSwitch = heaterSwitch;		
		Date logDate = new Date();		
		heaterSwitch.setLogDate(logDate);
				
		if(desiredTemperature.getValue() - currentTemperature.getValue() > temperatureThreshold.getValue()) {
			heaterSwitch.setStatus(SwitchStatus.ON);			
		} else if(lastHeaterSwitch.getStatus() == SwitchStatus.ON && currentTemperature.getValue() - desiredTemperature.getValue() < temperatureThreshold.getValue()){
			heaterSwitch.setStatus(SwitchStatus.ON);			
		} else {
			heaterSwitch.setStatus(SwitchStatus.OFF);			
		}		
		return heaterSwitch;
	}	
	
	public Switch storeHeaterStatus(Switch heaterSwitch) {
		Switch lastHeaterSwitch = this.heaterSwitch;		
		this.heaterSwitch = heaterSwitch;
		if(lastHeaterSwitch.getStatus() != this.heaterSwitch.getStatus()) {
			dataBaseService.saveSwitchStatus(this.heaterSwitch);
		}		
		return heaterSwitch;
	}
	
	public Temperature getLastTemperature() {
		return currentTemperature;
	}
	
	public Temperature getDesiredTemperature() {
		return desiredTemperature;
	}
	
	public Switch getLastHeaterSwitch() {
		return heaterSwitch;
	}
	
	public Temperature getTemperatureThreshold() {
		return temperatureThreshold;
	}
	
}
