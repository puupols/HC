package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pojo.Switch;
import pojo.Temperature;

public class DataBaseService {
	
	private DataSource dataSource;
	private Connection conn;
	
	public DataBaseService(DataSource dataSource) {
		this.dataSource = dataSource;
	}	
	
	public void saveTemperature(Temperature temperature) {		
		String sql = "INSERT INTO temperature (log_date, value, type) VALUES (?,?,?)";
		java.sql.Timestamp tDate = new java.sql.Timestamp(temperature.getLogDate().getTime());
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, tDate);
			ps.setDouble(2, temperature.getValue());
			ps.setString(3, temperature.getType().toString());
			ps.execute();
			System.out.println("Temperature stored..");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}	
	
	public void saveSwitchStatus(Switch receivedSwitch) {
		String sql = "INSERT INTO switch (log_date, value, type) VALUES (?, ?, ?)";
		java.sql.Timestamp tDate = new java.sql.Timestamp(receivedSwitch.getLogDate().getTime());
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, tDate);
			ps.setString(2, receivedSwitch.getStatus().toString());
			ps.setString(3, receivedSwitch.getType().toString());
			ps.execute();
			conn.close();
			System.out.println("Switch status saved");
		} catch (SQLException e){
			e.printStackTrace();
		}		
	}
}
