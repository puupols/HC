package controller;

import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;

import pojo.SwitchType;



public class HomeControlService {
	
	private TemperatureService temperatureService;
	private SwitchService switchService;
	
	public HomeControlService(TemperatureService temperatureService, SwitchService switchService) {		
		this.temperatureService = temperatureService;
		this.switchService = switchService;			
	}
			
	public Temperature storeTemperature(Temperature temperature) {		
		temperatureService.storeTemperature(temperature);		
		return temperature;
	}
	
	public boolean shouldSwitchBeOn(SwitchType switchType) {			
		if(temperatureService.isBelowThreshold()) {
			return true;
		} else if(switchService.isSwitchOn(SwitchType.HEATER) && temperatureService.isInThreshold()){
			return true;			
		} else {
			return false;
		}
	}
	
	public Switch storeSwitch(Switch receivedSwitch) {
		switchService.storeSwitch(receivedSwitch);		
		return receivedSwitch;
	}
	
	public Temperature getLastTemperature(TemperatureType temperatureType) {
		return temperatureService.getLastTemperature(temperatureType);	
	}
	
	public Switch getLastSwitch(SwitchType switchType) {
		return switchService.getLastSwitch(switchType);	
	}		
	
}
