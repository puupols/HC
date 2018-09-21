package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.DataBaseService;
import pojo.DayPeriod;
import pojo.DesiredTemperature;
import pojo.StatusCalculationType;
import pojo.Temperature;
import pojo.TemperatureType;

public class TemperatureService {
	
	private DataBaseService dataBaseService;
	private ConfigurationService configurationService;
	private Logger logger = LoggerFactory.getLogger(TemperatureService.class);
	private Map<TemperatureType, Temperature> temperatureMap = new HashMap<TemperatureType, Temperature>();
	
	private Map<DayPeriod, DesiredTemperature> desiredTemperatureMap = new HashMap<DayPeriod, DesiredTemperature>();
	
	public Temperature getDesiredTemperature(StatusCalculationType type){
		Temperature desiredTemperature = new Temperature();
		DayPeriod dayPeriod = calculateDayPeriod();
		
		if(type == StatusCalculationType.PROGRAMMED){
			return desiredTemperatureMap.get(dayPeriod);
		} else if (type == StatusCalculationType.STATIC){
			desiredTemperature = getLastTemperature(TemperatureType.DESIRED);
		}		
		
		return desiredTemperature;		
	}
	
	private DayPeriod calculateDayPeriod() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.format(date);
		
		try {
			if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("12:00"))){
				return DayPeriod.DAY;
			} else {
				return DayPeriod.NIGHT;
			}
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return null;
	}

	public TemperatureService(DataBaseService dataBaseService, ConfigurationService configurationService) {
		this.dataBaseService = dataBaseService;
		this.configurationService = configurationService;
	}
	
	public void storeTemperature(Temperature temperature) {
		temperatureMap.put(temperature.getType(), temperature);
		logger.info("Temperature stored in runtime map");
		dataBaseService.saveTemperature(temperature);		
	}
	
	public Temperature getLastTemperature(TemperatureType type) {		
		Temperature temperature = new Temperature();		
		if(!temperatureMap.containsKey(type)) {
			logger.info("Temperature of type " + type + " not received yet. Uses default from configuration.");
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
		
		if(lastTemperatureDate != null && lastTemperatureDate.before(temperatureValidityTime)) {			
			isTemperatureValid = false;
		} 		
		return isTemperatureValid;
	}
	
	public boolean isBelowThreshold(StatusCalculationType statusCalculationType) {
		Double desiredTemperature = getDesiredTemperature(statusCalculationType).getValue();
		Double currentTemperature = getLastTemperature(TemperatureType.MEASURED).getValue();
		Double temperatureThreshold = getLastTemperature(TemperatureType.THRESHOLD).getValue();		
		return (desiredTemperature - currentTemperature) > temperatureThreshold;		
	}
	
	public boolean isInThreshold(StatusCalculationType statusCalculationType) {
		Double desiredTemperature = getDesiredTemperature(statusCalculationType).getValue();
		Double currentTemperature = getLastTemperature(TemperatureType.MEASURED).getValue();
		Double temperatureThreshold = getLastTemperature(TemperatureType.THRESHOLD).getValue();		
		return ((currentTemperature - desiredTemperature) <= temperatureThreshold &&
				(desiredTemperature - currentTemperature) <= temperatureThreshold);
	}
}
