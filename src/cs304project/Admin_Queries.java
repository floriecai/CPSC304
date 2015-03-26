package cs304project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs304project.client.ui.AdminBoard;

public class Admin_Queries {
	
	private static Connection conn; 
	private static int admin;
	
	public Admin_Queries() {
		//super(conn); 
	}
	
	public ResultSet allRegisteredUsers() {
		String all = "SELECT * FROM RegisteredUsers";
		
		PreparedStatement ps;
		ResultSet rs = null; 
		
		try {
			
			ps = conn.prepareStatement(all);
			rs = ps.executeQuery(); 

			ps.close(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return rs;
	}
	
	public String[][] allVerifiedHosts() { 
		ResultSet rs = null;
		String[][] usersVerified = null;
		
		String verified = "SELECT R.name, R.email " 
				+ "FROM Host H, Verifies V, RegisteredUser R "
				+ "WHERE H.governmentId = V.governmentId AND H.email = R.email";
		
		try {
			PreparedStatement ps = conn.prepareStatement(verified, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery(); 
			rs.last();
			int rowCount = rs.getRow();
			usersVerified = new String[rowCount][2];
			rs.beforeFirst();
			
			while(rs.next()){
				usersVerified[rs.getRow()-1][0] = rs.getString("name");
				usersVerified[rs.getRow()-1][1] = rs.getString("email");
			}
			ps.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usersVerified;
	}
	
	//Find a registered user given an email (for login purposes)
	
	public ResultSet findUserByName(String email) {
		
		String user = "SELECT * FROM RegisteredUser R WHERE R.name = ?";
		PreparedStatement ps;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(user);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			ps.close(); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return rs;
		
	}
	
	//SHOULD ONLY RETURN 1 ADMIN 
	public String findAdminLogin(String adminId, String password) {
		String admins = "SELECT DISTINCT * FROM admin WHERE adminId = "  + adminId + " AND password LIKE '%" + password + "%'" ;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		List<String> args = null;
		conn = Connecting.getConnection();
		try {
			ps = conn.prepareStatement(admins);
			rs = ps.executeQuery();
			try {
				if(rs.next()){
					String adminIdName = rs.getString("adminId");
					if(adminIdName.contains(adminId)){
						admin = rs.getInt("adminId");
						return rs.getString("name");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "404";
	}
	
	// An inactive user is defined as having both:
	// 1) No bookings
	// 2) No listings
	// return Inactive users name and email
	
	// inactive string = All registered users NOT IN Reservations or PostedListings 
	public String[][] findInactiveUsers() {
		String[][] usersInactive = null;
		String inactive =  "SELECT R.name, R.email "
				+ "FROM RegisteredUser R "
				+ "WHERE NOT EXISTS "
				+ "(SELECT H.email "
				+ " FROM MakesReservation M, Transaction T, Host H, ListingPostedIsIn L "
				+ "WHERE T.transactionId = M.transactionId AND "
				+ "L.governmentId = H.governmentId AND "
				+ "L.listingId = M.listingId AND "
				+ "L.listingId = T.listingId AND "
				+ "R.email = H.email)";
		
		ResultSet rs = null;

		try {
			PreparedStatement ps = conn.prepareStatement(inactive, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery(); 
			rs.last();
			int rowCount = rs.getRow();
			usersInactive = new String[rowCount][2];
			rs.beforeFirst();
			while(rs.next()){
				usersInactive[rs.getRow()-1][0] = rs.getString("name");
				usersInactive[rs.getRow()-1][1] = rs.getString("email");
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return usersInactive;
	}
	
	// Verifies an user, inserting his governmentID into Verified table
	public boolean verifyUser(int adminId, String governmentId) {

		ResultSet rs = null;

		PreparedStatement ps; 

		try {
			ps = conn.prepareStatement("INSERT INTO Verifies VALUES (?, ?)"); 

			ps.setInt(1, adminId);
			ps.setString(2, governmentId);
			ps.executeUpdate();
			ps.close(); 

			System.out.println("A new user was verified!");
			return true;
		} catch (SQLException e) {
			System.out.println("Could not insert because: ");
			e.printStackTrace();
		}
		return false;
	}

	//Admin helper to look at all users ever registered
	public String[][] findUsers() {
		ResultSet rs = null;
		String users = "SELECT * FROM RegisteredUser";
		String[][] usersTuples = null;

		try {
			PreparedStatement ps= conn.prepareStatement(users, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			usersTuples = new String[rowCount][2];
			rs.beforeFirst();
			
			while(rs.next()){
				usersTuples[rs.getRow()-1][0] = rs.getString("name");
				usersTuples[rs.getRow()-1][1] = rs.getString("email");
			}
			ps.close();
			return usersTuples;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		// TODO Auto-generated method stub
		return usersTuples;
	}
	
	// Finds an user by his email and name and returns his govId
	public String findUsersGovId(String email) {
		ResultSet rs = null;
		String users = "SELECT h.governmentId FROM Host h WHERE h.email = ?";
		String usersTuples = null;

		try {
			PreparedStatement ps= conn.prepareStatement(users, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				usersTuples = rs.getString("governmentId");
				System.out.println(usersTuples);
			}
			ps.close();
			return usersTuples;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		// TODO Auto-generated method stub
		return usersTuples;
	}
	
	// Admin helper to get all listings ever made
	public String[][] findListings() {
		ResultSet rs = null;
		String listings = "SELECT * FROM ListingPostedIsIn LP, Location L WHERE L.postalCode = LP.postalCode";
		String[][] listingTuple = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(listings, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			listingTuple = new String[rowCount][4];
			rs.beforeFirst();
			
			while(rs.next()){
				listingTuple[rs.getRow()-1][0] = rs.getString("postalCode").trim();
				listingTuple[rs.getRow()-1][1] = rs.getString("price");
				listingTuple[rs.getRow()-1][2] = rs.getString("city");
				listingTuple[rs.getRow()-1][3] = rs.getString("rating");
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return listingTuple;
	}
	
	
	//Admin transaction query helper
	public String[][] findTransactions() {
		ResultSet rs = null;
		String listings = "SELECT T.transactionId, T.price, T.time, L.city, LP.address FROM Transaction T, ListingPostedIsIn LP, Location L where LP.listingId = T.listingId AND L.postalCode = LP.postalCode";
		String[][] transactionTuples = null;

		try {
			PreparedStatement ps= conn.prepareStatement(listings, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			
			rs.last();
			int rowCount = rs.getRow();
			transactionTuples = new String[rowCount][5];
			rs.beforeFirst();
			
			while(rs.next()){
				transactionTuples[rs.getRow()-1][0] = rs.getString("transactionId");
				transactionTuples[rs.getRow()-1][1] = rs.getString("price");
				transactionTuples[rs.getRow()-1][2] = rs.getString("city");
				transactionTuples[rs.getRow()-1][3] = rs.getString("time");
				transactionTuples[rs.getRow()-1][4] = rs.getString("address");
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return transactionTuples;
	}
	
	public int getAdminId(){
		return admin;
	}
	public String[][] findTransactionsToday() {
		ResultSet rs = null;
		String listings = "SELECT T.transactionId, T.price, T.time, L.city, LP.address FROM Transaction T, ListingPostedIsIn LP, Location L where LP.listingId = T.listingId AND L.postalCode = LP.postalCode";
		String[][] transactionTuples = null;

		try {
			PreparedStatement ps= conn.prepareStatement(listings, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			
			rs.last();
			int rowCount = rs.getRow();
			transactionTuples = new String[rowCount][5];
			rs.beforeFirst();
			
			while(rs.next()){
				transactionTuples[rs.getRow()-1][0] = rs.getString("transactionId");
				transactionTuples[rs.getRow()-1][1] = rs.getString("price");
				transactionTuples[rs.getRow()-1][2] = rs.getString("city");
				transactionTuples[rs.getRow()-1][3] = rs.getString("time");
				transactionTuples[rs.getRow()-1][4] = rs.getString("address");
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return transactionTuples;
	}
	
	public String[][] findTransactionsByDate() {
		ResultSet rs = null;
		String listings = "SELECT T.transactionId, T.price, T.time, L.city, LP.address FROM Transaction T, ListingPostedIsIn LP, Location L where LP.listingId = T.listingId AND L.postalCode = LP.postalCode";
		String[][] transactionTuples = null;

		try {
			PreparedStatement ps= conn.prepareStatement(listings, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			
			rs.last();
			int rowCount = rs.getRow();
			transactionTuples = new String[rowCount][5];
			rs.beforeFirst();
			
			while(rs.next()){
				transactionTuples[rs.getRow()-1][0] = rs.getString("transactionId");
				transactionTuples[rs.getRow()-1][1] = rs.getString("price");
				transactionTuples[rs.getRow()-1][2] = rs.getString("city");
				transactionTuples[rs.getRow()-1][3] = rs.getString("time");
				transactionTuples[rs.getRow()-1][4] = rs.getString("address");
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return transactionTuples;
	}
	
}