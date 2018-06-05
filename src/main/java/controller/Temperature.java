package controller;

public class Temperature {
	
	public double currentTemperature;	
	public double desiredTemperature;
	
	public double storeTemperature(double temperature) {
		System.out.println(temperature);
		currentTemperature = temperature;
		return temperature;
	}
	
	public String calculateHeaterStatus() {
		if(desiredTemperature - currentTemperature > 0.5) {
			return "ON";
		} else {
			return "OFF";
		}
	}
		
}
