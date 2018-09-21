package managers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import controller.SwitchService;
import controller.TemperatureService;
import pojo.StatusCalculationType;
import pojo.SwitchType;

public class HeaterSwitchManager implements SwitchManager {

	@Autowired
	private TemperatureService temperatureService;
	@Autowired
	private SwitchService switchService;
	
	private Logger logger = LoggerFactory.getLogger(HeaterSwitchManager.class);
		
	@Override
	public Boolean shouldSwitchBeOn(StatusCalculationType statusCalculationType) {
		Boolean shouldSwitchBeOn = false;
		
		if(!temperatureService.isTemperatresValid() || temperatureService.isBelowThreshold(statusCalculationType) || 
				switchService.isSwitchOn(SwitchType.HEATER) && temperatureService.isInThreshold(statusCalculationType)) {
			shouldSwitchBeOn = true;
		}
		
		if(statusCalculationType == StatusCalculationType.MANUAL_ON){
			shouldSwitchBeOn = true;
		} else if (statusCalculationType == StatusCalculationType.MANUAL_OFF){
			shouldSwitchBeOn = false;
		}
		
		logger.info("Should heater switch be on: " + shouldSwitchBeOn);
		return shouldSwitchBeOn;
	}
}
