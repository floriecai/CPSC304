package cs304project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class Reservation extends Transactions{

	Connection conn; 
	
	public Reservation(Connection con) {
		super(con);
	}
	
	public void generateTransaction(float price, int listingId, String checkIn, String checkOut, int numGuests) {
		PreparedStatement ps;
		String trans = "INSERT into Transaction (price, time, listingId) VALUES (?, ?, ?)";
		
		try {
			int key = Statement.RETURN_GENERATED_KEYS;
			ps = conn.prepareStatement(trans, key);
			ps.setFloat(1, price);
			
			Date now = new Date();
			ps.setTimestamp(2, new Timestamp(now.getTime())); 
			ps.setInt(3, listingId);
			ps.executeUpdate(); 
			ps.close();
			
			this.generateReservation(key, listingId, checkIn, checkOut, numGuests, price); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void generateReservation(int transId, int listingId, String checkIn,
			String checkOut, int numGuests, float price) {
		PreparedStatement ps;
		String res = "INSERT INTO MakesReservation (listingId "
				+ "checkindate, checkoutdate, numberOfGuests, transactionId) "
				+ "VALUES(?,?,?,?,?)";
		
		try {
			ps = conn.prepareStatement(res, Statement.RETURN_GENERATED_KEYS);
			int resId = Statement.RETURN_GENERATED_KEYS;
			
			ps.setInt(1, resId);
			ps.setInt(2, listingId);
			ps.setString(3, checkIn);
			ps.setString(4, checkIn);
			ps.setInt(5, numGuests);
			ps.setFloat(6, price);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return; 
	}
}
