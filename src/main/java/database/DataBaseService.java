package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.ConfigurationService;
import pojo.Switch;
import pojo.Temperature;
import pojo.TemperatureType;
import pojo.SwitchStatus;

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
	
	public Temperature getLastTemperature(TemperatureType type) {		
		Temperature temperature = new Temperature();
		String sql = "SELECT * FROM temperature where type = ? order by log_date desc limit 1;";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, type.toString());
				ResultSet result = ps.executeQuery();
				while (result.next()) {					
					temperature.setValue(result.getDouble("value"));
					temperature.setLogDate(result.getTimestamp("log_date"));
					temperature.setType(TemperatureType.valueOf(result.getString("type")));
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
		return temperature;
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
	
	public Switch getLastSwitchStatus() {
		Switch lastHeaterSwitch = new Switch();
		String sql = "SELECT * FROM switch order by log_date desc limit 1;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				lastHeaterSwitch.setLogDate(result.getTimestamp("log_date"));
				lastHeaterSwitch.setStatus(SwitchStatus.valueOf(result.getString("value")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastHeaterSwitch;
	}
}
