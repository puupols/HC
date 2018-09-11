package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.Switch;
import pojo.Temperature;

public class DataBaseService {
	
	private DataSource dataSource;	
	private Logger logger = LoggerFactory.getLogger(DataBaseService.class);
	
	public DataBaseService(DataSource dataSource) {
		this.dataSource = dataSource;		
	}	
	
	public void saveTemperature(Temperature temperature) {
		Connection conn = null;
		String sql = "INSERT INTO temperature (log_date, value, type) VALUES (?,?,?)";
		java.sql.Timestamp tDate = new java.sql.Timestamp(temperature.getLogDate().getTime());
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, tDate);
			ps.setDouble(2, temperature.getValue());
			ps.setString(3, temperature.getType().toString());
			ps.execute();			
			conn.close();
			logger.info("Temperature stored in DB");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error during store temperature in DB: ", e);
		} finally {
			try {
				if(conn != null) {
				conn.close();
				}
			} catch (SQLException e) {
				logger.error("Error closing connection ", e);				
			}
		}	
	}	
	
	public void saveSwitchStatus(Switch receivedSwitch) {
		Connection conn = null;
		String sql = "INSERT INTO switch (log_date, value, type) VALUES (?, ?, ?)";
		java.sql.Timestamp tDate = new java.sql.Timestamp(receivedSwitch.getLogDate().getTime());
		try {	
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, tDate);
			ps.setString(2, receivedSwitch.getStatus().toString());
			ps.setString(3, receivedSwitch.getType().toString());
			ps.execute();			
			logger.info("Switch status stored in DB");
		} catch (SQLException e){
			e.printStackTrace();
			logger.error("Error during store switch status in DB: ", e);
		} finally {
			try {
				if(conn != null) {
				conn.close();
				}
			} catch (SQLException e) {
				logger.error("Error closing connection ", e);				
			}
		}	
	}
}
