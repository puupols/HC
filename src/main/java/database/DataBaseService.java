package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import controller.ConfigurationService;
import pojo.Switch;
import pojo.Temperature;

public class DataBaseService {
	
	private ConfigurationService configurationService;
	private Connection conn;
	public DataBaseService(ConfigurationService configurationService) {
		this.configurationService = configurationService;		
		conn = getConnection();
	}	
		
	
	
	private Connection getConnection() {	
		String dbUrl = configurationService.getProperty("DB_CONNECTION_URL");
		String userName = configurationService.getProperty("DB_USER");
		String password  = configurationService.getProperty("DB_PASSWORD");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl,userName,password);
			System.out.println("Connected...");			
		}catch(Exception e) {
			System.out.print(e);
		}
		return conn;
	}
	
	public void saveTemperature(Temperature temperature) {		
		String sql = "INSERT INTO temperature (log_date, value, type) VALUES (?,?,?)";
		java.sql.Timestamp tDate = new java.sql.Timestamp(temperature.getLogDate().getTime());
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, tDate);
			ps.setDouble(2, temperature.getValue());
			ps.setString(3, temperature.getType().toString());
			ps.execute();
			System.out.println("Temperature stored..");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public void saveSwitchStatus(Switch heaterSwitch) {
		String sql = "INSERT INTO switch (log_date, value) VALUES (?, ?)";
		java.sql.Timestamp tDate = new java.sql.Timestamp(heaterSwitch.getLogDate().getTime());
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, tDate);
			ps.setString(2, heaterSwitch.getStatus().toString());
			ps.execute();
			System.out.println("Switch status saved");
		} catch (SQLException e){
			e.printStackTrace();
		}		
	}
}
