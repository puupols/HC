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
import util.DataTime;

public class TemperatureService {
	
	private DataBaseService dataBaseService;
	private ConfigurationService configurationService;
	private DataTime dataTime;
	private Logger logger = LoggerFactory.getLogger(TemperatureService.class);
	private Map<TemperatureType, Temperature> temperatureMap = new HashMap<TemperatureType, Temperature>();
	
	private Map<DayPeriod, DesiredTemperature> desiredTemperatureMap = new HashMap<DayPeriod, DesiredTemperature>();

	public TemperatureService(DataBaseService dataBaseService, ConfigurationService configurationService,
			DataTime dataTime) {
		this.dataBaseService = dataBaseService;
		this.configurationService = configurationService;
		this.dataTime = dataTime;
		createDefaultTemperatures();
	}
	
	private void createDefaultTemperatures(){
		for(DayPeriod dayPeriod : DayPeriod.values()){
			DesiredTemperature defaultDesiredTemperature = new DesiredTemperature();
			defaultDesiredTemperature.setDayPeriod(dayPeriod);
			defaultDesiredTemperature.setType(TemperatureType.DESIRED);
			defaultDesiredTemperature.setValue(configurationService.getPropertyAsDouble("DESIRED_TEMPERATURE_" + dayPeriod.toString()));
			desiredTemperatureMap.put(defaultDesiredTemperature.getDayPeriod(), defaultDesiredTemperature);
		}		
	}
	
	public Temperature getDesiredTemperature(StatusCalculationType type){
		Temperature desiredTemperature = new Temperature();
		if(type == StatusCalculationType.PROGRAMMED){
			DayPeriod dayPeriod = calculateDayPeriod();
			logger.info("Calculated day period " + dayPeriod);
			return desiredTemperatureMap.get(dayPeriod);
		} else if (type == StatusCalculationType.STATIC){
			desiredTemperature = getLastTemperature(TemperatureType.DESIRED);
		}		
		return desiredTemperature;		
	}
	
	private DayPeriod calculateDayPeriod() {
		DayPeriod currentDayPeriod = DayPeriod.NIGHT;
		Boolean isInDayPeriod;
		Date date = dataTime.getDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.format(date);
		for(DayPeriod dayPeriod : DayPeriod.values()) {
			String startTime = configurationService.getProperty("START_TIME_" + dayPeriod);
			isInDayPeriod = false;
			try {
				isInDayPeriod = dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse(startTime));
			} catch (ParseException e) {
				logger.error("Error parsing configured start time for day period " + dayPeriod, e);				
			}
			if(isInDayPeriod){
				currentDayPeriod = dayPeriod;
			}			
		}
		return currentDayPeriod;
	}
	
	public void storeTemperature(Temperature temperature) {
		temperatureMap.put(temperature.getType(), temperature);
		logger.info("Temperature stored in runtime map");
		dataBaseService.saveTemperature(temperature);		
	}
	
	public void storeDesiredTemperature(DesiredTemperature desiredTemperature){
		if(desiredTemperature.getDayPeriod() == null){
		storeTemperature(desiredTemperature);
		} else {
		desiredTemperatureMap.put(desiredTemperature.getDayPeriod(), desiredTemperature);
		}		
	}
	
	public DesiredTemperature getDesiredTemperature(DayPeriod dayPeriod) {
		return desiredTemperatureMap.get(dayPeriod);
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
		Date currentTime = dataTime.getDate();
		Date temperatureValidityTime = new Date(currentTime.getTime() - temperatureValidityPeriodMils);		
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
