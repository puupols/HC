package controller;

import managers.SwitchManager;
import managers.SwitchManagerFactory;
import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;

import pojo.SwitchType;



public class HomeControlService {
	
	private TemperatureService temperatureService;
	private SwitchService switchService;
	private SwitchManagerFactory switchManagerFactory;
	
	public HomeControlService(TemperatureService temperatureService, SwitchService switchService, 
			SwitchManagerFactory switchManagerFactory) {		
		this.temperatureService = temperatureService;
		this.switchService = switchService;
		this.switchManagerFactory = switchManagerFactory;
	}
			
	public Temperature storeTemperature(Temperature temperature) {		
		temperatureService.storeTemperature(temperature);		
		return temperature;
	}
	
	public boolean shouldSwitchBeOn(SwitchType switchType) {		
		SwitchManager switchManager = switchManagerFactory.getSwitchManager(switchType);			
		return switchManager.shouldSwitchBeOn(); 
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
