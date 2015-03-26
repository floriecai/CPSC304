package cs304project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

public class Reservation extends Transactions{

	Connection conn; 

	public Reservation(Connection con) {
		super(con);
	}

	public int generateTransaction(double price, int listingId) {
		PreparedStatement ps;
		String trans = "INSERT into Transaction (price, time, listingId, email) VALUES (?, ?, ?)";
		int key = -1; //DEFAULT
		try {
			key = Statement.RETURN_GENERATED_KEYS;
			ps = conn.prepareStatement(trans, key);
			ps.setDouble(1, price);

			java.util.Date date = new java.util.Date(); 
			long t = date.getTime();
			java.sql.Date now = new java.sql.Date(t);
			ps.setTimestamp(2, new Timestamp(now.getTime())); 
			ps.setInt(3, listingId);
			
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}

	public String generateReservation(int transId, int listingId, Date checkIn,
			Date checkOut, int numGuests) {
		PreparedStatement ps;
		String res = "INSERT INTO MakesReservation (listingId "
				+ "checkindate, checkoutdate, numberOfGuests, transactionId) "
				+ "VALUES (?,?,?,?,?)";

		try {
			ps = conn.prepareStatement(res, Statement.RETURN_GENERATED_KEYS);
			int resId = Statement.RETURN_GENERATED_KEYS;

			ps.setInt(1, listingId);
			ps.setDate(2, checkIn);
			ps.setDate(3, checkOut);
			ps.setInt(4, numGuests);
			ps.setInt(5, transId);
			
			ps.executeUpdate(); 
			ps.close(); 

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "Successfully created reservation!";
	}

	public void deleteReservation(int transId, int listingId, Date checkin, Date checkout) {
		String toDelete = "DELETE FROM MakesReservation WHERE listingId = ? AND transactionId = ? "
				+ "AND checkindate LIKE ? AND checkoutdate LIKE ?";

		PreparedStatement ps;

		try {
			ps = conn.prepareStatement(toDelete); 
			ps.setInt(1, listingId);
			ps.setInt(2, transId);
			ps.setDate(3, checkin);
			ps.setDate(4, checkout);

			ps.executeUpdate(); 
			ps.close(); 


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertTransIdAndEmail(int transId, String email) {
		String toInsert = "INSERT INTO TransactionIdAndEmail (transactionId, email) VALUES (?,?)";
		PreparedStatement ps; 
	
		try {
			ps = conn.prepareStatement(toInsert); 
			
			ps.setInt(1, transId); 
			ps.setString(2, email);
			
			ps.executeUpdate();
			ps.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	// TODO WHAT IS GOING ON WITH THE SCHEMA??? 
	public List<String> reservationsOfUser(String email) {
		String reservations = "SELECT "
				+ "FROM MakesReservation ";
		ResultSet rs = null;

		PreparedStatement ps; 
		return null;
	}
	
	// Paypal transactions 
	public void paypalTransaction(int transId, String email) {
		String paypal = "INSERT INTO PayPalTransaction (transactionId, email) VALUES (?, ?)";
		PreparedStatement ps; 
		try {
			ps = conn.prepareStatement(paypal);
			ps.setInt(1, transId);
			ps.setString(2, email); 
			ps.executeUpdate();
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void creditCardInfo(String cardNo, String company, String name, Date expiry) {
		String ccInfo = "INSERT INTO CreditCardInfo (cardNumber, company, cardHolderName, expiryDate) "
				+ "VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps; 
		try {
			ps = conn.prepareStatement(ccInfo);
			ps.setString(1, cardNo); 
			ps.setString(2, company); 
			ps.setString(3, name);
			ps.setDate(4, expiry);
			
			ps.executeUpdate();
			ps.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
	}
	
	// Creditcard Transactions
	public void CreditCardTransaction(int transId, String cardNo) {
		String cc = "INSERT INTO CreditCardTransaction(transactionId, cardNumber) VALUES (?,?) ";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(cc);
			ps.setInt(1, transId);
			ps.setString(2, cardNo); 
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
