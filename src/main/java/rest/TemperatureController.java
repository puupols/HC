package rest;
import controller.TemperatureService;
import pojo.HeaterSwitch;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TemperatureController {
	
	@Autowired
	private temperatureRepository temperatureRepository;
	
	TemperatureService temperatureService = TemperatureService.get();
	
    @RequestMapping("/getCurrentData")
    public TemperatureService GetCurrentData() {
        return temperatureService;
    }    

	@RequestMapping("/storeTemperature")
	public @ResponseBody Temperature StoreTemperature(@RequestParam(value="temperature") double value) {
		Temperature temperature = new Temperature();
		Date logDate = new Date();		
		temperature.setValue(value);
		temperature.setLogDate(logDate);
		temperatureRepository.save(temperature);	
		return temperatureService.storeTemperature(temperature);
			
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
	
	@RequestMapping("/getHeaterStatus")
	public HeaterSwitch getHeaterStatus() {
		return temperatureService.getHeaterStatus();
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
