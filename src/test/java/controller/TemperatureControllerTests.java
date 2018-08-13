package controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import database.DataBaseService;
import pojo.HeaterSwitch;
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

}
