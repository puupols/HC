package controllers;

import static org.junit.Assert.assertEquals;

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
import pojo.StatusCalculationType;
import pojo.Temperature;
import pojo.TemperatureType;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureServiceTests {

	@Mock 
	DataBaseService databaseService;
	
	@Mock
	ConfigurationService configurationService;
	
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
