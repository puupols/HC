package controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import database.DataBaseService;
import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;
import pojo.SwitchStatus;

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
		Switch heaterSwitch = new Switch();
		Date date = new Date();
		heaterSwitch.setLogDate(date);
		heaterSwitch.setStatus(SwitchStatus.ON);				
		assertEquals(temperatureService.storeSwitch(heaterSwitch), heaterSwitch);		
	}
	
	
	public void getCalculatedHeaterStatus(Double currentTemp, Double desiredTemp, String lastStatus, Double tempTrashold, String calculatedStatus) {
		
		Switch heaterSwitch = new Switch();
		Temperature currentTemperature = new Temperature();	
		Temperature desiredTemperature = new Temperature();
		Temperature temperatureThreshold = new Temperature();
		Switch lastHeaterSwitchStatus = new Switch();		
		
		currentTemperature.setType(TemperatureType.MEASURED);				
		currentTemperature.setValue(currentTemp);
		desiredTemperature.setType(TemperatureType.DESIRED);
		desiredTemperature.setValue(desiredTemp);
		temperatureThreshold.setType(TemperatureType.THRESHOLD);
		temperatureThreshold.setValue(tempTrashold);
		lastHeaterSwitchStatus.setStatus(SwitchStatus.valueOf(lastStatus));		
		
		temperatureService.storeTemperature(currentTemperature);
		temperatureService.storeSwitch(lastHeaterSwitchStatus);
		temperatureService.storeTemperature(desiredTemperature);
		temperatureService.storeTemperature(temperatureThreshold);		
		
		heaterSwitch = temperatureService.getCalculatedHeaterSwitch();		
		assertEquals(heaterSwitch.getStatus(), SwitchStatus.valueOf(calculatedStatus));	
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
