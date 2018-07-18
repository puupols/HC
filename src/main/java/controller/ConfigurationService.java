package controller;

import java.io.FileReader;
import java.util.Properties;

public class ConfigurationService {
	
	public static ConfigurationService instance;
	
	public static ConfigurationService getConfigurationService() {
		if(instance == null) {
			instance = new ConfigurationService();
		}
		return instance;
	}	
	
	private Properties properties = new Properties();
	
	private ConfigurationService() {
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
}
