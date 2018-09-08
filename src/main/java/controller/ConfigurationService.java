package controller;

import java.io.FileReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigurationService {
	
	private Properties properties = new Properties();
	private Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
	
	public ConfigurationService() {
		getPropertiesFromFile();
	}
		
	private void getPropertiesFromFile() {
		try {
			FileReader reader = new FileReader("config.properties");
			properties.load(reader);
		} catch (Exception e) {
			logger.error("Error reading configuration from file: ", e); 
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
