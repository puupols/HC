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
		Boolean shouldSwitchBeOn = true;
		
		if(temperatureService.isTemperatresValid()) {			
			return true;
		}
		
		if(temperatureService.isBelowThreshold()) {
			shouldSwitchBeOn = true;
		} else if(switchService.isSwitchOn(SwitchType.HEATER) && temperatureService.isInThreshold()){
			shouldSwitchBeOn = true;			
		} else {
			shouldSwitchBeOn = false;
		}
		System.out.println("Should heater switch be on: " + shouldSwitchBeOn);
		return shouldSwitchBeOn;
	}

}
