package controller;

import java.io.FileReader;
import java.util.Properties;

public class ConfigurationService {
	
	private Properties properties = new Properties();
	
	public ConfigurationService() {
		getPropertiesFromFile();
	}
		
	private void getPropertiesFromFile() {
		try {
			FileReader reader = new FileReader("config.properties");
			properties.load(reader);
		} catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	public Double getPropertyAsDouble(String name) {
		return Double.parseDouble(properties.getProperty(name));
	}
	
	public Integer getPropertyAsInteger(String name) {
		return Integer.parseInt(properties.getProperty(name));
	}
}
