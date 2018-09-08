package database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import controller.ConfigurationService;

public class DataSource {
	
	private static ConfigurationService configurationService;
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;
	
	public DataSource() {
		configurationService = new ConfigurationService();
		config.setJdbcUrl(configurationService.getProperty("DB_CONNECTION_URL"));
		config.setUsername(configurationService.getProperty("DB_USER"));
		config.setPassword(configurationService.getProperty("DB_PASSWORD"));        
        ds = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException{
		return ds.getConnection();
	}

}
