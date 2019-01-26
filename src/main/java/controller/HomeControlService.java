package controller;

import managers.SwitchManager;
import managers.SwitchManagerFactory;
import pojo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeControlService {
	
	private TemperatureService temperatureService;
	private SwitchService switchService;
	private SwitchManagerFactory switchManagerFactory;
	private ReportingService reportingService;
	
	public HomeControlService(TemperatureService temperatureService, SwitchService switchService, 
			SwitchManagerFactory switchManagerFactory, ReportingService reportingService) {
		this.temperatureService = temperatureService;
		this.switchService = switchService;
		this.switchManagerFactory = switchManagerFactory;
		this.reportingService = reportingService;
	}
			
	public Temperature storeTemperature(Temperature temperature) {		
		temperatureService.storeTemperature(temperature);		
		return temperature;
	}
	
	public DesiredTemperature storeDesiredTemperature(DesiredTemperature desiredTemperature){
		temperatureService.storeDesiredTemperature(desiredTemperature);
		return desiredTemperature;
	}
	
	public DesiredTemperature getDesiredTemperature(DayPeriod dayPeriod) {
		return temperatureService.getDesiredTemperature(dayPeriod);
	}
	
	public boolean shouldSwitchBeOn(SwitchType switchType) {		
		SwitchManager switchManager = switchManagerFactory.getSwitchManager(switchType);			
		return switchManager.shouldSwitchBeOn(switchService.getLastSwitch(switchType).getStatusCalculationType()); 
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
	
	public Switch storeSwitchStatusCalculationType(Switch receivedSwitch){
		return switchService.storeSwitchStatusCalculationType(receivedSwitch);
	}

	public List<SwitchOnTime> getSwitchOnTime(){
		return reportingService.getSwitchOnTime();
	}
	
}
