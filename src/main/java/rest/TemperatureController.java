package rest;
import controller.TemperatureService;
import pojo.HeaterSwitch;
import pojo.Temperature;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TemperatureController {
	
	TemperatureService temperatureService = TemperatureService.get();
	
	@RequestMapping("/getCurrentData")
	public TemperatureService GetCurrentData() {
		return temperatureService;
	}
	
	
	@RequestMapping("/storeTemperature")
	public Temperature StoreTemperature(@RequestParam(value="temperature") double value) {
		Temperature temperature = new Temperature();
		Date readDate = new Date();		
		temperature.setValue(value);
		temperature.setReadDate(readDate);
		return temperatureService.storeTemperature(temperature);
	}
	
	@RequestMapping("/getCurrentTemperature")
	public Temperature GetCurrentTemperature() {
		return temperatureService.getCurrentTemperature();
	}
	
	@RequestMapping("/getDesiredTemperature")
	public double GetDesiredTemperature() {
		return temperatureService.getDesiredTemperature();
	}
	
	@RequestMapping("/setDesiredTemperature")
	public double SetDesiredTemperature(@RequestParam(value="temperature") double temperature) {
		return temperatureService.setDesiredTemperature(temperature);
	}
	
	@RequestMapping("/getHeaterStatus")
	public HeaterSwitch getHeaterStatus() {
		return temperatureService.getHeaterStatus();
	}
}
