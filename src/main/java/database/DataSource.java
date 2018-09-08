package database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import controller.ConfigurationService;

public class DataSource {
	
	private ConfigurationService configurationService;
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;
	
	public DataSource(ConfigurationService configurationService) {
		this.configurationService = configurationService;
		config.setJdbcUrl(this.configurationService.getProperty("DB_CONNECTION_URL"));
		config.setUsername(this.configurationService.getProperty("DB_USER"));
		config.setPassword(this.configurationService.getProperty("DB_PASSWORD"));        
        ds = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException{
		return ds.getConnection();
	}

}
