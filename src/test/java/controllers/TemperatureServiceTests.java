package controllers;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import controller.ConfigurationService;
import controller.TemperatureService;
import database.DataBaseService;
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
	
	@Test
	public void isBelowThreshold(){
		Temperature measuredTemperature = new Temperature();
		Temperature desiredTemperature = new Temperature();
		Temperature thresholdTemperature = new Temperature();
		
		measuredTemperature.setValue(19.0);
		measuredTemperature.setLogDate(new Date());
		measuredTemperature.setType(TemperatureType.MEASURED);
		
		desiredTemperature.setValue(21.0);
		desiredTemperature.setLogDate(new Date());
		desiredTemperature.setType(TemperatureType.DESIRED);
		
		thresholdTemperature.setValue(0.5);
		thresholdTemperature.setLogDate(new Date());
		thresholdTemperature.setType(TemperatureType.THRESHOLD);
		
		temperatureService.storeTemperature(measuredTemperature);
		temperatureService.storeTemperature(desiredTemperature);
		temperatureService.storeTemperature(thresholdTemperature);
		
		assertEquals(temperatureService.isBelowThreshold(), true);
		
		measuredTemperature.setValue(20.6);
		temperatureService.storeTemperature(measuredTemperature);
		
		assertEquals(temperatureService.isBelowThreshold(), false);
	}
	
	@Test
	public void isInThreshold(){
		Temperature measuredTemperature = new Temperature();
		Temperature desiredTemperature = new Temperature();
		Temperature thresholdTemperature = new Temperature();
		
		measuredTemperature.setValue(19.0);
		measuredTemperature.setLogDate(new Date());
		measuredTemperature.setType(TemperatureType.MEASURED);
		
		desiredTemperature.setValue(21.0);
		desiredTemperature.setLogDate(new Date());
		desiredTemperature.setType(TemperatureType.DESIRED);
		
		thresholdTemperature.setValue(0.5);
		thresholdTemperature.setLogDate(new Date());
		thresholdTemperature.setType(TemperatureType.THRESHOLD);
		
		temperatureService.storeTemperature(measuredTemperature);
		temperatureService.storeTemperature(desiredTemperature);
		temperatureService.storeTemperature(thresholdTemperature);
		
		assertEquals(temperatureService.isInThreshold(), false);
		
		measuredTemperature.setValue(20.6);
		temperatureService.storeTemperature(measuredTemperature);
		assertEquals(temperatureService.isInThreshold(), true);
		
		measuredTemperature.setValue(21.4);
		temperatureService.storeTemperature(measuredTemperature);
		assertEquals(temperatureService.isInThreshold(), true);
		
		measuredTemperature.setValue(21.6);
		temperatureService.storeTemperature(measuredTemperature);
		assertEquals(temperatureService.isInThreshold(), false);
	}
}
