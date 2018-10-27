package managers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import controller.SwitchService;
import controller.TemperatureService;
import pojo.StatusCalculationType;
import pojo.SwitchType;

@RunWith(MockitoJUnitRunner.class)
public class HeaterSwitchManagerTests {
	
	@Mock
	TemperatureService temperatureService;
	@Mock
	SwitchService switchService;
	@InjectMocks
	HeaterSwitchManager heaterSwitchManager;
	
	@Test
	public void belowThreshold() {
		Mockito.when(temperatureService.isTemperatresValid()).thenReturn(true);
		Mockito.when(temperatureService.isBelowThreshold(StatusCalculationType.STATIC)).thenReturn(true);
		Mockito.when(temperatureService.isInThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(true);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(StatusCalculationType.STATIC), true);
	}
	
	@Test
	public void inThresholdSwitchOn() {
		Mockito.when(temperatureService.isTemperatresValid()).thenReturn(true);
		Mockito.when(temperatureService.isBelowThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold(StatusCalculationType.STATIC)).thenReturn(true);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(true);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(StatusCalculationType.STATIC), true);
	}
	
	@Test
	public void inThresholdSwichOff() {
		Mockito.when(temperatureService.isTemperatresValid()).thenReturn(true);
		Mockito.when(temperatureService.isBelowThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold(StatusCalculationType.STATIC)).thenReturn(true);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(false);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(StatusCalculationType.STATIC), false);
	}
	
	@Test
	public void aboveThrasholdSwitchOn() {
		Mockito.when(temperatureService.isTemperatresValid()).thenReturn(true);
		Mockito.when(temperatureService.isBelowThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(true);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(StatusCalculationType.STATIC), false);
	}
	
	@Test
	public void aboveTresholdSwitchOffTemperatureValid() {
		Mockito.when(temperatureService.isTemperatresValid()).thenReturn(true);
		Mockito.when(temperatureService.isBelowThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(false);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(StatusCalculationType.STATIC), false);
	}	
		
	@Test
	public void aboveTresholdSwitchOffTemperatureNotValid() {		
		Mockito.when(temperatureService.isTemperatresValid()).thenReturn(false);
		Mockito.when(temperatureService.isBelowThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold(StatusCalculationType.STATIC)).thenReturn(false);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(false);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(StatusCalculationType.STATIC), true);
	}

}
