package cs304project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Reservation extends Transactions{

	Connection conn; 
	
	public Reservation(Connection con) {
		super(con);
	}
	
	public void generateTransaction(float price, String time, int listingId) {
		PreparedStatement ps;
		String trans = "INSERT into Transaction (price, time, listingId) VALUES (?, ?, ?)";
		
		try {
			ps = conn.prepareStatement(trans, Statement.RETURN_GENERATED_KEYS);
			ps.setFloat(1, price);
			ps.setString(2, time); 
			ps.setInt(3, listingId);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	}
}
