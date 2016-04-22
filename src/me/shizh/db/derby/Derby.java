package me.shizh.db.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.shizh.common.util.PathUtil;

public class Derby {
	
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	
	private static Connection conn = null;
	
	public static void loadDriver() {
		try {
			Class.forName(driver).newInstance();
			System.out.println("Loaded the appr opriate driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn(){
		if(conn == null){
			loadDriver();
			String protocol = "jdbc:derby:";
			String dbName = PathUtil.getResourcesPath()+"data/derby_data/zen_wuji";
			try {
				conn  = DriverManager.getConnection(protocol + dbName+ ";create=true");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

}