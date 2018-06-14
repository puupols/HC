package controller;

import java.util.Date;

import pojo.HeaterSwitch;
import pojo.Temperature;

public class TemperatureService {
	
	private static double currentTemperature = 21;	
	private static double desiredTemperature = 21;
	private static double temperatureThershold = 0.5;	
	private static Date lastTemperatureReading;
	
	public static TemperatureService instance;
	
	public static TemperatureService get() {		
			if(instance == null) {
				instance = new TemperatureService(); 
			}		
		return instance;
	}
	
	public Temperature storeTemperature(Temperature temperature) {		
		currentTemperature = temperature.getValue();
		lastTemperatureReading = temperature.getReadDate();
		return temperature;
	}
	
	public HeaterSwitch getHeaterStatus() {
		
		HeaterSwitch heaterSwitch = new HeaterSwitch();
		Date calcDate = new Date();		
		heaterSwitch.setCalcDate(calcDate);
		
		if(desiredTemperature - currentTemperature > temperatureThershold) {
			heaterSwitch.setStatus("ON");
			return heaterSwitch;
		} else if(heaterSwitch.getStatus() == "ON" && currentTemperature - desiredTemperature < temperatureThershold){
			heaterSwitch.setStatus("ON");
			return heaterSwitch;
		} else {
			heaterSwitch.setStatus("OFF");
			return heaterSwitch;
		}
	}
	
	public Temperature getCurrentTemperature() {
		Temperature temperature = new Temperature();
		temperature.setValue(currentTemperature);
		temperature.setReadDate(lastTemperatureReading);
		return temperature;
	}
	
	public double getDesiredTemperature() {
		return desiredTemperature;
	}
	
	public Date getLastTemperatureReading() {
		return lastTemperatureReading;
	}
		
	public double setDesiredTemperature(double temperature) {
		desiredTemperature = temperature;
		return desiredTemperature;
	}		
}
