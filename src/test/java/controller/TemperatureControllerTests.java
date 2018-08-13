package controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import database.DataBaseService;
import pojo.HeaterSwitch;
import pojo.Temperature;
import pojo.switchStatus;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureControllerTests {
	
	@Mock
	ConfigurationService configurationService;
	
	@Mock
	DataBaseService dataBaseService;
	
	@InjectMocks
	TemperatureService temperatureService;
	
	@Test
	public void StoreHeaterStatus_ReturnsHeaterSwitch() {
		HeaterSwitch heaterSwitch = new HeaterSwitch();
		Date date = new Date();
		heaterSwitch.setLogDate(date);
		heaterSwitch.setStatus(switchStatus.ON);				
		assertEquals(temperatureService.storeHeaterStatus(heaterSwitch), heaterSwitch);		
	}
	
	
	public void getCalculatedHeaterStatus(Double currentTemp, Double desiredTemp, String lastStatus, Double tempTrashold, String calculatedStatus) {
		HeaterSwitch heaterSwitch = new HeaterSwitch();
		Temperature currentTemperature = new Temperature();	
		HeaterSwitch lasetHeaterSwitchStatus = new HeaterSwitch();
				
		currentTemperature.setValue(currentTemp);
		lasetHeaterSwitchStatus.setStatus(switchStatus.valueOf(lastStatus));		
		
		Mockito.when(dataBaseService.getLastTemperature()).thenReturn(currentTemperature);
		Mockito.when(configurationService.getPropertyAsDouble("DESIRED_TEMPERATURE")).thenReturn(desiredTemp);
		Mockito.when(configurationService.getPropertyAsDouble("TEMPERATURE_TRASHOLD")).thenReturn(tempTrashold);
		Mockito.when(dataBaseService.getLastSwitchStatus()).thenReturn(lasetHeaterSwitchStatus);
		
		heaterSwitch = temperatureService.getCalculatedHeaterStatus();		
		assertEquals(heaterSwitch.getStatus(), switchStatus.valueOf(calculatedStatus));	
	}
	
	@Test
	public void getCalculatedHeaterStatusTests() {
		
		Double currentTemp = 21.4;
		Double desiredTemp = 21.0;
		String lastStatus = "ON";
		Double tempTrashold = 0.5;
		String calculatedStatus = "ON";
		getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, calculatedStatus);
		
		currentTemp = 22.1;
		desiredTemp = 21.0;
		lastStatus = "ON";
		tempTrashold = 0.5;
		calculatedStatus = "OFF";
		getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, calculatedStatus);
		
		currentTemp = 21.4;
		desiredTemp = 21.0;
		lastStatus = "OFF";
		tempTrashold = 0.5;
		calculatedStatus = "OFF";
		getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, calculatedStatus);
		
		currentTemp = 20.5;
		desiredTemp = 21.0;
		lastStatus = "OFF";
		tempTrashold = 0.5;
		calculatedStatus = "OFF";
		getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, calculatedStatus);
		
		currentTemp = 20.4;
		desiredTemp = 21.0;
		lastStatus = "OFF";
		tempTrashold = 0.5;
		calculatedStatus = "ON";
		getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, calculatedStatus);
	}	
}
