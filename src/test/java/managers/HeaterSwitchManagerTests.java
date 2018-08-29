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
	public void shouldSwitchBeONTests() {
		Mockito.when(temperatureService.isBelowThreshold()).thenReturn(true);
		Mockito.when(temperatureService.isInThreshold()).thenReturn(false);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(true);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(), true);
		
		Mockito.when(temperatureService.isBelowThreshold()).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold()).thenReturn(true);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(true);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(), true);
		
		Mockito.when(temperatureService.isBelowThreshold()).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold()).thenReturn(true);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(false);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(), false);
		
		Mockito.when(temperatureService.isBelowThreshold()).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold()).thenReturn(false);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(true);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(), false);
		
		Mockito.when(temperatureService.isBelowThreshold()).thenReturn(false);
		Mockito.when(temperatureService.isInThreshold()).thenReturn(false);
		Mockito.when(switchService.isSwitchOn(SwitchType.HEATER)).thenReturn(false);
		assertEquals(heaterSwitchManager.shouldSwitchBeOn(), false);
	}

}
