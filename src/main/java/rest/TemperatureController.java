package rest;
import controller.TemperatureService;
import pojo.switchStatus;
import pojo.HeaterSwitch;
import pojo.Temperature;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {
	
	TemperatureService temperatureService = TemperatureService.get();
		
	@RequestMapping("/storeTemperature")
	public Temperature StoreTemperature(@RequestParam(value="temperature") double value) {
		Temperature temperature = new Temperature();
		Date logDate = new Date();		
		temperature.setValue(value);
		temperature.setLogDate(logDate);		
		return temperatureService.storeTemperature(temperature);
	}
	
	@RequestMapping("/storeHeaterStatus")
	public HeaterSwitch storeHeaterStatus(@RequestParam(value="heaterStatus") String value) {
		HeaterSwitch heaterSwitch = new HeaterSwitch();
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
	
	@RequestMapping("/getCurrentTemperature")
	public Temperature GetCurrentTemperature() {		
		return temperatureService.getCurrentTemperature();
	}
	
	@RequestMapping("/getDesiredTemperature")
	public Temperature GetDesiredTemperature() {
		return temperatureService.getDesiredTemperature();
	}
	
	@RequestMapping("/setDesiredTemperature")
	public Temperature SetDesiredTemperature(@RequestParam(value="temperature") double value) {
		Temperature temperature = new Temperature();
		Date date = new Date();
		temperature.setLogDate(date);
		temperature.setValue(value);		
		return temperatureService.setDesiredTemperature(temperature);
	}
	
	@RequestMapping("/calculateHeaterStatus")
	public HeaterSwitch getHeaterStatus() {
		return temperatureService.calculateHeaterStatus();
	}
	
	@RequestMapping("/setTemperatureThreshold")
	public Temperature SetTemperatureThreshold(@RequestParam(value="temperature") double value) {
		Temperature temperature = new Temperature();
		Date date = new Date();
		temperature.setLogDate(date);
		temperature.setValue(value);		
		return temperatureService.setTemperatureThershold(temperature);
	}
}
