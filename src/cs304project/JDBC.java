package cs304project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
	String userid = "ora_g4w8";
	String password = "a17636127";
	String url = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
	Connection conn = null;
	Statement stment = null; 

	public JDBC () {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			System.out.println("Connecting to database ...");
			conn = (Connection) DriverManager.getConnection(url, userid, password); 

			//SQL statements:
			stment = ((java.sql.Connection) conn).createStatement();
			String sql_stment = null; 
			ResultSet rs = stment.executeQuery(sql_stment); 

			while (rs.next()) {
				// Do some stuff with the results
			}
			rs.close(); 
			stment.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Connection Failed \n");
		}
	}
}

