package injector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.ConfigurationService;
import controller.HomeControlService;
import controller.SwitchService;
import controller.TemperatureService;
import database.DataBaseService;

@Configuration
public class AppConfig {
	
	@Bean
	public ConfigurationService configurationService() {
		return new ConfigurationService();
	}
	
	@Bean
	public DataBaseService dataBaseService() {
		return new DataBaseService(configurationService());
	}
	
	@Bean 
	public TemperatureService temperatureService() {
		return new TemperatureService(dataBaseService(), configurationService());
	}
	
	@Bean
	public SwitchService switchService() {
		return new SwitchService(dataBaseService());
	}
	
	@Bean
	public HomeControlService homeControlService() {
		return new HomeControlService(temperatureService(), switchService());
	}
}
