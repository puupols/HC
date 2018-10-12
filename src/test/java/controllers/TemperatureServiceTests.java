package controllers;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import controller.ConfigurationService;
import controller.TemperatureService;
import database.DataBaseService;
import pojo.DayPeriod;
import pojo.DesiredTemperature;
import pojo.StatusCalculationType;
import pojo.Temperature;
import pojo.TemperatureType;
import util.DataTime;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureServiceTests {

	@Mock 
	DataBaseService databaseService;
	
	@Mock
	ConfigurationService configurationService;
	
	@Mock
	DataTime dataTime;
	
	@InjectMocks
	TemperatureService temperatureService;
	
	@Test
	public void storeAndGetLastTemperature(){
		Temperature temperature = new Temperature();
		Temperature lastTemperature = new Temperature();
		temperature.setValue(21.3);
		temperature.setLogDate(new Date());
		temperature.setType(TemperatureType.MEASURED);
		temperatureService.storeTemperature(temperature);		
		lastTemperature = temperatureService.getLastTemperature(TemperatureType.MEASURED);
		assertEquals(21.3, lastTemperature.getValue(), 0);		
	}
	
	private void storeTemperature(Double value, Date logDate, TemperatureType type) {
		Temperature temperature = new Temperature();
		temperature.setValue(value);
		temperature.setLogDate(logDate);
		temperature.setType(type);
		temperatureService.storeTemperature(temperature);
	}
	
	@Test
	public void storeAndGetProgrammedTemperatures(){
		Mockito.when(configurationService.getProperty("START_TIME_NIGHT")).thenReturn("21:00");
		Mockito.when(configurationService.getProperty("START_TIME_DAY")).thenReturn("08:00");	
		storeDesiredTemperature(DayPeriod.NIGHT, 18.5);
		storeDesiredTemperature(DayPeriod.DAY, 21.5);
		getProgrammedTemperature("2018.12.10 05:01:01", 18.5);
		getProgrammedTemperature("2018.12.10 08:01:01", 21.5);
		getProgrammedTemperature("2018.12.10 20:59:01", 21.5);
		getProgrammedTemperature("2018.12.10 21:01:01", 18.5);
		getProgrammedTemperature("2018.12.10 23:01:01", 18.5);
	}
	
	
	private void getProgrammedTemperature(String stringDate, Double value){			
		Date date = new Date();		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		try{
			date = dateFormat.parse(stringDate);
		} catch(ParseException e){
			e.printStackTrace();
		}			
		Mockito.when(dataTime.getDate()).thenReturn(date);
		assertEquals(temperatureService.getDesiredTemperature(StatusCalculationType.PROGRAMMED).getValue(), value, 0);
		
	}
	
	private void storeDesiredTemperature(DayPeriod dayPeriod, Double temperature){
		DesiredTemperature desiredTemperature = new DesiredTemperature();
		desiredTemperature.setDayPeriod(dayPeriod);
		desiredTemperature.setType(TemperatureType.DESIRED);
		desiredTemperature.setValue(temperature);
		temperatureService.storeDesiredTemperature(desiredTemperature);
	}
	
	@Test
	public void isBelowThreshold(){		
		storeTemperature(19.0, new Date(), TemperatureType.MEASURED);
		storeTemperature(21.0, new Date(), TemperatureType.DESIRED);
		storeTemperature(0.5, new Date(), TemperatureType.THRESHOLD);		
		assertEquals(temperatureService.isBelowThreshold(StatusCalculationType.STATIC), true);				
		storeTemperature(20.6, new Date(), TemperatureType.MEASURED);		
		assertEquals(temperatureService.isBelowThreshold(StatusCalculationType.STATIC), false);
	}
	
	@Test
	public void isInThreshold(){
		storeTemperature(19.0, new Date(), TemperatureType.MEASURED);
		storeTemperature(21.0, new Date(), TemperatureType.DESIRED);
		storeTemperature(0.5, new Date(), TemperatureType.THRESHOLD);				
		assertEquals(temperatureService.isInThreshold(StatusCalculationType.STATIC), false);
		storeTemperature(20.5, new Date(), TemperatureType.MEASURED);		
		assertEquals(temperatureService.isInThreshold(StatusCalculationType.STATIC), true);	
		storeTemperature(20.6, new Date(), TemperatureType.MEASURED);		
		assertEquals(temperatureService.isInThreshold(StatusCalculationType.STATIC), true);		
		storeTemperature(21.4, new Date(), TemperatureType.MEASURED);		
		assertEquals(temperatureService.isInThreshold(StatusCalculationType.STATIC), true);
		storeTemperature(21.5, new Date(), TemperatureType.MEASURED);
		assertEquals(temperatureService.isInThreshold(StatusCalculationType.STATIC), true);
		storeTemperature(21.6, new Date(), TemperatureType.MEASURED);
		assertEquals(temperatureService.isInThreshold(StatusCalculationType.STATIC), false);
	}
	
	@Test
	public void getDefaultValues() {
		Mockito.when(configurationService.getPropertyAsDouble("TEMPERATURE_DESIRED")).thenReturn(21.0);
		Mockito.when(configurationService.getPropertyAsDouble("TEMPERATURE_MEASURED")).thenReturn(22.0);
		Mockito.when(configurationService.getPropertyAsDouble("TEMPERATURE_THRESHOLD")).thenReturn(0.5);
		assertEquals(temperatureService.getLastTemperature(TemperatureType.MEASURED).getValue(), 22.0, 0);
		assertEquals(temperatureService.getLastTemperature(TemperatureType.DESIRED).getValue(), 21.0, 0);
		assertEquals(temperatureService.getLastTemperature(TemperatureType.THRESHOLD).getValue(), 0.5, 0);
	}
}
