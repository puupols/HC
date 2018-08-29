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
import pojo.SwitchType;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureControllerTests {
	
	@Mock
	ConfigurationService configurationService;
	
	@Mock
	DataBaseService dataBaseService;
	
	@InjectMocks
	HomeControlService temperatureService;
	
	@Test
	public void StoreHeaterStatus_ReturnsHeaterSwitch() {
		Switch heaterSwitch = new Switch();
		Date date = new Date();
		heaterSwitch.setLogDate(date);
		heaterSwitch.setStatus(SwitchStatus.ON);				
	//	assertEquals(temperatureService.storeSwitch(heaterSwitch), heaterSwitch);		
	}
	
	
	public void getCalculatedHeaterStatus(Double currentTemp, Double desiredTemp, String lastStatus, Double tempTrashold, Boolean shouldBeOn) {
		
		boolean heaterSwitch;
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
		
		heaterSwitch = temperatureService.shouldSwitchBeOn(SwitchType.HEATER);		
		assertEquals(heaterSwitch, shouldBeOn);	
	}
	
	//@Test
	public void getCalculatedHeaterStatusTests() {
				
		Double currentTemp = 21.4;
		Double desiredTemp = 21.0;
		String lastStatus = "ON";
		Double tempTrashold = 0.5;
		boolean shouldBeOn = true;
		//getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, shouldBeOn);
		
		currentTemp = 22.1;
		desiredTemp = 21.0;
		lastStatus = "ON";
		tempTrashold = 0.5;
		shouldBeOn = false;
		//getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, shouldBeOn);
		
		currentTemp = 21.4;
		desiredTemp = 21.0;
		lastStatus = "OFF";
		tempTrashold = 0.5;
		shouldBeOn = false;
		//getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, shouldBeOn);
		
		currentTemp = 20.5;
		desiredTemp = 21.0;
		lastStatus = "OFF";
		tempTrashold = 0.5;
		shouldBeOn = false;
		//getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, shouldBeOn);
		
		currentTemp = 20.4;
		desiredTemp = 21.0;
		lastStatus = "OFF";
		tempTrashold = 0.5;
		shouldBeOn = true;
		//getCalculatedHeaterStatus(currentTemp, desiredTemp, lastStatus, tempTrashold, shouldBeOn);
	}	
}
