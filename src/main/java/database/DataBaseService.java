package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pojo.Temperature;

public class DataBaseService {
	private static Connection getConnection() {
		
		Connection conn = null;		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exercises","root","Ciupicipicss390");
			System.out.println("Connected...");			
		}catch(Exception e) {
			System.out.print(e);
		}
		return conn;
	}
	
	public void saveTemperature(Temperature temperature) {
		Connection conn = getConnection();
		String sql = "INSERT INTO temperature (log_date, value) VALUES (?,?)";
		java.sql.Timestamp tDate = new java.sql.Timestamp(temperature.getLogDate().getTime());
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, tDate);
			ps.setDouble(2, temperature.getValue());
			ps.execute();
			System.out.println("Temperature stored..");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
