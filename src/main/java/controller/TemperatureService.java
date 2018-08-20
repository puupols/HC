package controller;

import java.util.Date;

import database.DataBaseService;
import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;
import pojo.SwitchStatus;
import pojo.SwitchType;



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
		initVariables();		
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
	
	public Switch getCalculatedHeaterSwitch() {
		
		Switch calculatedHeaterSwitch = new Switch();
		Date logDate = new Date();
		calculatedHeaterSwitch.setLogDate(logDate);
		calculatedHeaterSwitch.setType(SwitchType.HEATER);
				
		if(desiredTemperature.getValue() - currentTemperature.getValue() > temperatureThreshold.getValue()) {
			calculatedHeaterSwitch.setStatus(SwitchStatus.ON);			
		} else if(heaterSwitch.getStatus() == SwitchStatus.ON && currentTemperature.getValue() - desiredTemperature.getValue() < temperatureThreshold.getValue()){
			calculatedHeaterSwitch.setStatus(SwitchStatus.ON);			
		} else {
			calculatedHeaterSwitch.setStatus(SwitchStatus.OFF);			
		}		
		return calculatedHeaterSwitch;
	}	
	
	public Switch storeSwitch(Switch receivedSwitch) {
		Switch lastSwitch = new Switch();
		switch(receivedSwitch.getType()) {
		case HEATER:
			lastSwitch = this.heaterSwitch;	
			this.heaterSwitch = receivedSwitch;
			break;
		default:
			break;
		}		
		if(lastSwitch.getStatus() != receivedSwitch.getStatus()) {
			dataBaseService.saveSwitchStatus(receivedSwitch);
		}		
		return receivedSwitch;
	}
	
	public Temperature getLastTemperature(TemperatureType temperatureType) {
		switch(temperatureType) {
		case MEASURED:
			return currentTemperature;			
		case DESIRED:
			return desiredTemperature;			
		case THRESHOLD:
			return temperatureThreshold;			
		default:
			return null;
		}		
	}
	
	public Switch getLastSwitch(SwitchType switchType) {
		switch (switchType) {
		case HEATER:
			return heaterSwitch;
		default:
			return null;			
		}		
	}	

	private void initVariables() {
		desiredTemperature = new Temperature();
		desiredTemperature.setType(TemperatureType.DESIRED);
		desiredTemperature.setValue(configurationService.getPropertyAsDouble("DESIRED_TEMPERATURE"));
		currentTemperature = new Temperature();
		currentTemperature.setType(TemperatureType.MEASURED);
		currentTemperature.setValue(configurationService.getPropertyAsDouble("DEFAULT_TEMPERATURE"));
		temperatureThreshold = new Temperature();		
		temperatureThreshold.setType(TemperatureType.THRESHOLD);
		temperatureThreshold.setValue(configurationService.getPropertyAsDouble("TEMPERATURE_TRASHOLD"));
		heaterSwitch = new Switch();
		heaterSwitch.setStatus(SwitchStatus.OFF);
		heaterSwitch.setType(SwitchType.HEATER);
	}
	
}
