package rest;
import controller.Temperature;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TemperatureController {
	
	Temperature temperature = new Temperature();
	
	@RequestMapping("/storeTemperature")
	public double StoreTemperature(@RequestParam(value="temperature") double temp) {
		return temperature.storeTemperature(temp);
	}
	
	@RequestMapping("/getCurrentTemperature")
	public double GetCurrentTemperature() {
		return temperature.currentTemperature;
	}
	
	@RequestMapping("/getDesiredTemperature")
	public double GetDesiredTemperature() {
		return temperature.desiredTemperature;
	}
	
	@RequestMapping("/setDesiredTemperature")
	public double SetDesiredTemperature(@RequestParam(value="temperature") double temp) {
		temperature.desiredTemperature = temp;
		return temperature.desiredTemperature;
	}
	
	@RequestMapping("/getHeaterStatus")
	public String getHeaterStatus() {
		return temperature.calculateHeaterStatus();
	}
}
