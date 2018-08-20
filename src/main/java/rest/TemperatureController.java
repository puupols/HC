package rest;
import controller.HomeControlService;
import injector.AppConfig;
import pojo.SwitchStatus;
import pojo.SwitchType;
import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {
	ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	HomeControlService temperatureService = context.getBean(HomeControlService.class);	
	
		
	@RequestMapping("/storeTemperature")
	public Temperature storeTemperature(@RequestParam(value="temperature") double value, @RequestParam(value="type") String type) {
		Temperature temperature = new Temperature();
		Date logDate = new Date();		
		temperature.setValue(value);
		temperature.setLogDate(logDate);
		try {
		temperature.setType(TemperatureType.valueOf(type));
		} catch (IllegalArgumentException e) {
			System.out.println("Type" + type + "not found in enum");
			return null;
		}
		return temperatureService.storeTemperature(temperature);
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
			System.out.println("Type" + type + "or value " + value + " not found in enum");
			return null;
		}				
		return temperatureService.storeSwitch(receivedSwitch);
	}
	
	@RequestMapping("/getLastTemperature")
	public Temperature getLastTemperature(@RequestParam(value="type") String type) {
		TemperatureType temperatureType = null;
		try {
		temperatureType = TemperatureType.valueOf(type);
		} catch (IllegalArgumentException e) {
			System.out.println("Type " + type + " not found in enum");
			return null;
		}
		return temperatureService.getLastTemperature(temperatureType);
	}
	
	@RequestMapping("/shouldSwitchBeOn")
	public boolean shouldSwitchBeOn(@RequestParam(value="type") String type) {
		SwitchType switchType = null;
		try {
			switchType = SwitchType.valueOf(type);
		} catch (IllegalArgumentException e) {
			System.out.println("Type " + type + " not found in enum");
			//ToDo return something else
			return false;
		}
		return temperatureService.shouldSwitchBeOn(switchType);
	}
	
	@RequestMapping("/getLastSwitch")
	public Switch getLastSwitch(@RequestParam(value = "type") String type) {
		
		SwitchType switchType = null;		
		try {
			switchType = SwitchType.valueOf(type);			
		}catch (IllegalArgumentException e) {
			System.out.println("Type " + type + " not found in enum");
			return null;
		}
		return temperatureService.getLastSwitch(switchType);
	}
}
