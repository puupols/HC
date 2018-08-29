package managers;


import org.springframework.beans.factory.annotation.Autowired;

import controller.SwitchService;
import controller.TemperatureService;
import pojo.SwitchType;

public class HeaterSwitchManager implements SwitchManager {

	@Autowired
	TemperatureService temperatureService;
	
	@Autowired
	SwitchService switchService;
	
	@Override
	public Boolean shouldSwitchBeOn() {
				
		if(temperatureService.isBelowThreshold()) {
			return true;
		} else if(switchService.isSwitchOn(SwitchType.HEATER) && temperatureService.isInThreshold()){
			return true;			
		} else {
			return false;
		}	
	}

}
