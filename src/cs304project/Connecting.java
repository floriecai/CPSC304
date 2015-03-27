package cs304project;

import java.sql.*;

public class Connecting {
	
	private static Connection connection;

	public boolean connecting(){
      String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 
      try {
    	  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    	  connection = DriverManager.getConnection(connectURL,"ora_g7q8", "a44586121");

    	  System.out.println("\nConnected to Oracle!");
    	  return true;
      }catch (SQLException ex){
    	  System.out.println("Message: " + ex.getMessage());
    	  return false;
      }
    }

	public static Connection getConnection(){
		try {
			if(!connection.isClosed())
			return connection;
		} catch (SQLException ex1) {
			  System.out.println("Message: " + ex1.getMessage());
		}
		return null;
	}

}
