package rest;
import controller.HomeControlService;
import injector.AppConfig;
import pojo.SwitchStatus;
import pojo.SwitchType;
import pojo.DayPeriod;
import pojo.DesiredTemperature;
import pojo.StatusCalculationType;
import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {
	ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	HomeControlService homeControlService = context.getBean(HomeControlService.class);	
	
	private Logger logger = LoggerFactory.getLogger(TemperatureController.class);
		
	@RequestMapping("/storeTemperature")
	public Temperature storeTemperature(@RequestParam(value="temperature") double value, @RequestParam(value="type") String type) {
		Temperature temperature = new Temperature();
		Date logDate = new Date();		
		temperature.setValue(value);
		temperature.setLogDate(logDate);
		try {
		temperature.setType(TemperatureType.valueOf(type));
		} catch (IllegalArgumentException e) {
			logger.error("Type " + type + " not found in enum", e);			
			return null;
		}
		return homeControlService.storeTemperature(temperature);
	}
	
	@RequestMapping("/storeDesiredTemperature")
	public DesiredTemperature storeDesiredTemperature(@RequestParam(value="temperature") double value, @RequestParam(value="dayPeriod") String dayPeriod){
		DesiredTemperature desiredTemperature = new DesiredTemperature();
		Date logDate = new Date();
		desiredTemperature.setValue(value);
		desiredTemperature.setType(TemperatureType.DESIRED);
		desiredTemperature.setLogDate(logDate);
		try{
			desiredTemperature.setDayPeriod(DayPeriod.valueOf(dayPeriod));
		} catch (IllegalArgumentException e) {
			logger.error("Day period " + dayPeriod + " not found in enum", e);
		}
		return homeControlService.storeDesiredTemperature(desiredTemperature);
	}
	
	@RequestMapping("/storeSwitch")
	public Switch storeSwitch(@RequestParam(value="status") String value, @RequestParam(value="type") String type) {
		Switch receivedSwitch = new Switch();
		Date logDate = new Date();		
		receivedSwitch.setLogDate(logDate);
		try {
			receivedSwitch.setStatus(SwitchStatus.valueOf(value));
			receivedSwitch.setType(SwitchType.valueOf(type));
		} catch (IllegalArgumentException e){
			logger.error("Type" + type + "or value " + value + " not found in enum", e);
			return null;
		}				
		return homeControlService.storeSwitch(receivedSwitch);
	}
	@RequestMapping("/storeSwitchStatusCalculationType")
	public Switch storeSwitchCalculationType(@RequestParam(value="type") String type, @RequestParam(value="statusCalculationType") String statusCalculationType){
		Switch receivedSwitch = new Switch();
		try{
			receivedSwitch.setStatusCalculationType(StatusCalculationType.valueOf(statusCalculationType));
		} catch (IllegalArgumentException e){
			logger.error("Status calculation type " + statusCalculationType + " not found in enum");
			return null;
		}
		try{
			receivedSwitch.setType(SwitchType.valueOf(type));
		} catch (IllegalArgumentException e){
			logger.error("Type " + statusCalculationType + " not found in enum");
			return null;
		}
		return homeControlService.storeSwitchStatusCalculationType(receivedSwitch);
	}
	
	@RequestMapping("/getLastTemperature")
	public Temperature getLastTemperature(@RequestParam(value="type") String type) {
		TemperatureType temperatureType = null;
		try {
		temperatureType = TemperatureType.valueOf(type);
		} catch (IllegalArgumentException e) {
			logger.error("Type " + type + " not found in enum", e);
			return null;
		}
		return homeControlService.getLastTemperature(temperatureType);
	}
	
	@RequestMapping("/shouldSwitchBeOn")
	public boolean shouldSwitchBeOn(@RequestParam(value="type") String type) {
		SwitchType switchType = null;
		try {
			switchType = SwitchType.valueOf(type);
		} catch (IllegalArgumentException e) {
			logger.error("Type " + type + " not found in enum", e);
			//ToDo return something else
			return false;
		}
		return homeControlService.shouldSwitchBeOn(switchType);
	}
	
	@RequestMapping("/getLastSwitch")
	public Switch getLastSwitch(@RequestParam(value = "type") String type) {
		
		SwitchType switchType = null;		
		try {
			switchType = SwitchType.valueOf(type);			
		}catch (IllegalArgumentException e) {
			logger.error("Type " + type + " not found in enum", e);
			return null;
		}
		return homeControlService.getLastSwitch(switchType);
	}
}
