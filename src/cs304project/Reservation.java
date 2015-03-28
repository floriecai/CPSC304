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

		String trans = "INSERT INTO Transaction (transactionId, price, time, listingId) VALUES (trans_seq.nextval, ?, ?, ?)";
		int key = -1; //DEFAULT
		try {
			
			conn = Connecting.getConnection(); 
			
			if (conn != null) {
				ps = conn.prepareStatement(trans);
				ps.setDouble(1, price);

				java.util.Date date = new java.util.Date(); 
				long t = date.getTime();
				java.sql.Date now = new java.sql.Date(t);
				ps.setTimestamp(2, new Timestamp(now.getTime())); 
				ps.setInt(3, listingId);

				String s = "SELECT MAX(transactionId) as transactionId FROM Transaction";
				Statement statement = conn.createStatement(); 
				
				ResultSet rs = statement.executeQuery(s); 
				
				if (rs.next()) 
					key = rs.getInt("transactionId"); 
			
				ps.executeUpdate();

				statement.close(); 
				ps.close();
			}
			else System.out.println("conn is null");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(key);
		return key;
	}

	public String generateReservation(int transId, int listingId, Date checkIn,
			Date checkOut, int numGuests) {
		PreparedStatement ps;
		String res = "INSERT INTO MakesReservation (reservationId, listingId, "
				+ "checkindate, checkoutdate, numberOfGuests, transactionId) "
				+ "VALUES (resv_seq.nextval, ?,?,?,?,?)";

		try {
			ps = conn.prepareStatement(res);

			ps.setInt(1, listingId);
			ps.setDate(2, checkIn);
			ps.setDate(3, checkOut);
			ps.setInt(4, numGuests);
			ps.setInt(5, transId);
			
			ps.executeUpdate(); 
			ps.close();
						
			return "Sucessful reservation!";

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
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
			e.printStackTrace();
		} 
	}
	
	// Paypal transactions 
	public void paypalTransaction(int transId, String email) {
		String paypal = "INSERT INTO PayPalTransaction (transactionId, email) VALUES (?, ?)";
		PreparedStatement ps; 
		
		conn = Connecting.getConnection(); 
		
		try {
			ps = conn.prepareStatement(paypal);
			ps.setInt(1, transId);
			ps.setString(2, email); 
			
			
			System.out.println(transId);
			System.out.println(email);
			ps.executeUpdate();
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void creditCardInfo(String cardNo, String company, String name, String expiry) {
		String ccInfo = "INSERT INTO CreditCardInfo (cardNumber, company, cardHolderName, expirationDate) "
				+ "VALUES (?, ?, ?, ?)";
		System.out.println(cardNo + " " + company + " " + name + " " + expiry);
		conn = Connecting.getConnection(); 
		
		PreparedStatement ps; 
		try {
			ps = conn.prepareStatement(ccInfo);
			ps.setString(1, cardNo); 
			ps.setString(2, company); 
			ps.setString(3, name);
			ps.setString(4, expiry);
			
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
