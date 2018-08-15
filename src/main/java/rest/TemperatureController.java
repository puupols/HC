package rest;
import controller.TemperatureService;
import injector.AppConfig;
import pojo.switchStatus;
import pojo.Switch;
import pojo.Temperature;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {
	ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	TemperatureService temperatureService = context.getBean(TemperatureService.class);	
	
		
	@RequestMapping("/storeTemperature")
	public Temperature storeTemperature(@RequestParam(value="temperature") double value) {
		Temperature temperature = new Temperature();
		Date logDate = new Date();		
		temperature.setValue(value);
		temperature.setLogDate(logDate);		
		return temperatureService.storeTemperature(temperature);
	}
	
	@RequestMapping("/storeHeaterStatus")
	public Switch storeHeaterStatus(@RequestParam(value="heaterStatus") String value) {
		Switch heaterSwitch = new Switch();
		Date logDate = new Date();
		try {
		heaterSwitch.setStatus(switchStatus.valueOf(value));
		} catch (IllegalArgumentException e){
			System.out.println("Switch status " + value + " not found in enum");
			return null;
		}
		heaterSwitch.setLogDate(logDate);		
		return temperatureService.storeHeaterStatus(heaterSwitch);
	}
	
	@RequestMapping("/getLastTemperature")
	public Temperature getLastTemperature() {		
		return temperatureService.getLastTemperature();
	}
	
	@RequestMapping("/getDesiredTemperature")
	public Temperature getDesiredTemperature() {
		return temperatureService.getDesiredTemperature();
	}	
	
	@RequestMapping("/getCalculatedHeaterStatus")
	public Switch getCalculatedHeaterStatus() {
		return temperatureService.getCalculatedHeaterStatus();
	}
}
