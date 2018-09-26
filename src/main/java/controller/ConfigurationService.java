package controller;

import java.io.FileReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigurationService {
	
	private Properties properties;
	private Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
		
	public ConfigurationService() {
		
		properties = new Properties(getDefaultProperties());
		getPropertiesFromFile();
	}
	
	private Properties getDefaultProperties() {
		Properties defaultProperties = new Properties();
		defaultProperties.setProperty("TEMPERATURE_THRESHOLD", "0.5");
		defaultProperties.setProperty("TEMPERATURE_DESIRED", "21");
		defaultProperties.setProperty("TEMPERATURE_MEASURED", "21");
		defaultProperties.setProperty("TEMPERATURE_VALIDITY_PERIOD_MILISEC", "600000");		
		return defaultProperties;		
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
