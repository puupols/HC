package controller;

import java.io.FileReader;
import java.util.Properties;

public class ConfigurationService {
	private Properties properties = new Properties();
	
	public Properties getProperties() {		
		try {
		FileReader reader = new FileReader("config.properties");
		properties.load(reader);
		} catch (Exception e) {
			System.out.println(e);
		}		
	return properties;
	}
}
