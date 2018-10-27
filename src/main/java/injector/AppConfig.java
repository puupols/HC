package injector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.ConfigurationService;
import controller.HomeControlService;
import controller.SwitchService;
import controller.TemperatureService;
import database.DataBaseService;
import database.DataSource;
import managers.HeaterSwitchManager;
import managers.SwitchManagerFactory;
import util.DataTime;
import util.DataTimeImpl;

@Configuration
public class AppConfig {
	
	@Bean
	public ConfigurationService configurationService() {
		return new ConfigurationService();
	}
	
	@Bean
	public DataBaseService dataBaseService() {
		return new DataBaseService(dataSource());
	}
	
	@Bean 
	public TemperatureService temperatureService() {
		return new TemperatureService(dataBaseService(), configurationService(), dataTime());
	}
	
	@Bean
	public SwitchService switchService() {
		return new SwitchService(dataBaseService(), configurationService());
	}
	
	@Bean
	public SwitchManagerFactory switchManagerFactory(){
		return new SwitchManagerFactory();
	}
	
	@Bean
	public HeaterSwitchManager heaterSwitchManager() {
		return new HeaterSwitchManager();
	}
	
	@Bean
	public HomeControlService homeControlService() {
		return new HomeControlService(temperatureService(), switchService(), switchManagerFactory());
	}
	
	@Bean
	public DataSource dataSource() {
		return new DataSource(configurationService());
	}
	
	@Bean
	public DataTime dataTime(){
		return new DataTimeImpl();
	}
}
