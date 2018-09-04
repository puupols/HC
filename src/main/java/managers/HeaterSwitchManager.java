package managers;


import org.springframework.beans.factory.annotation.Autowired;

import controller.SwitchService;
import controller.TemperatureService;
import pojo.SwitchType;

public class HeaterSwitchManager implements SwitchManager {

	@Autowired
	private TemperatureService temperatureService;
	@Autowired
	private SwitchService switchService;
		
	@Override
	public Boolean shouldSwitchBeOn() {
		Boolean shouldSwitchBeOn = false;
		
		if(!temperatureService.isTemperatresValid() || temperatureService.isBelowThreshold() || 
				switchService.isSwitchOn(SwitchType.HEATER) && temperatureService.isInThreshold()) {
			shouldSwitchBeOn = true;
		}	
		
		System.out.println("Should heater switch be on: " + shouldSwitchBeOn);
		return shouldSwitchBeOn;
	}
}
