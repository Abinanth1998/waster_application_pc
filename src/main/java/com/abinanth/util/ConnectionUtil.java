package com.abinanth.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {
	private ConnectionUtil() {

	}

//	private static String driverClass = System.getenv("spring.datasource.driver-class-name");
//	private static String url = System.getenv("spring.datasource.url");
//	private static String username = System.getenv("spring.datasource.username");
//	private static String password = System.getenv("spring.datasource.password");

	//private static String driverClass = System.getenv("spring.datasource.driver-class-name");
	private static String url = System.getenv("jdbc:postgresql://localhost:5432/app ");
	private static String username = System.getenv("port");
	private static String password = System.getenv("admin");
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		//Class.forName(driverClass);
		Connection connection = DriverManager.getConnection(url, username, password);

		return connection;
	}

	public static void close(Statement st, Connection con) {
		try {
			if (con != null && st != null) {
				con.close();
				st.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
