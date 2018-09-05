package controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import database.DataBaseService;
import pojo.Temperature;
import pojo.TemperatureType;

public class TemperatureService {
	
	private DataBaseService dataBaseService;
	private ConfigurationService configurationService;
	private Map<TemperatureType, Temperature> temperatureMap = new HashMap<TemperatureType, Temperature>();		
	
	public TemperatureService(DataBaseService dataBaseService, ConfigurationService configurationService) {
		this.dataBaseService = dataBaseService;
		this.configurationService = configurationService;
	}
	
	public void storeTemperature(Temperature temperature) {
		temperatureMap.put(temperature.getType(), temperature);
		dataBaseService.saveTemperature(temperature);
	}
	
	public Temperature getLastTemperature(TemperatureType type) {		
		Temperature temperature = new Temperature();		
		if(!temperatureMap.containsKey(type)) {		
			Double temperatureValue = configurationService.getPropertyAsDouble("TEMPERATURE_" + type.toString());			
			temperature.setType(type);
			temperature.setValue(temperatureValue);
		} else {
			temperature = temperatureMap.get(type);
		}		
		return temperature;
	}	
	
	public boolean isTemperatresValid() {
		
		boolean isTemperatureValid = true;
		int temperatureValidityPeriodMils = configurationService.getPropertyAsInteger("TEMPERATURE_VALIDITY_PERIOD_MILISEC");
		Date lastTemperatureDate = getLastTemperature(TemperatureType.MEASURED).getLogDate();
		Date temperatureValidityTime = new Date(System.currentTimeMillis() - temperatureValidityPeriodMils);	
		
		if(lastTemperatureDate == null || lastTemperatureDate.before(temperatureValidityTime)) {			
			isTemperatureValid = false;
		} 		
		return isTemperatureValid;
	}
	
	public boolean isBelowThreshold() {
		Temperature desiredTemperature = getLastTemperature(TemperatureType.DESIRED);
		Temperature currentTemperature = getLastTemperature(TemperatureType.MEASURED);
		Temperature temperatureThreshold = getLastTemperature(TemperatureType.THRESHOLD);		
		return (desiredTemperature.getValue() - currentTemperature.getValue()) > temperatureThreshold.getValue();		
	}
	
	public boolean isInThreshold() {
		Temperature desiredTemperature = getLastTemperature(TemperatureType.DESIRED);
		Temperature currentTemperature = getLastTemperature(TemperatureType.MEASURED);
		Temperature temperatureThreshold = getLastTemperature(TemperatureType.THRESHOLD);		
		return ((currentTemperature.getValue() - desiredTemperature.getValue()) < temperatureThreshold.getValue() &&
				(desiredTemperature.getValue() - currentTemperature.getValue()) < temperatureThreshold.getValue());
	}
}
