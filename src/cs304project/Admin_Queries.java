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
	
	public ResultSet allVerifiedHosts() {
		PreparedStatement ps; 
		ResultSet rs = null;
		
		String verified = "SELECT H.email, R.name"
				+ "FROM Verifies V, Host H, RegisteredUsers R"
				+ "WHERE V.governmentId = H.governmentId";
		
		try {
			ps = conn.prepareStatement(verified);
			rs = ps.executeQuery(); 
			
			ps.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;	
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
	public ResultSet findInactiveUsers() {
		String inactive =  "SELECT R.name, R.email "
				+ "FROM RegisteredUser R "
				+ "WHERE NOT EXISTS "
				+ "(SELECT H.email "
				+ " FROM MakesReservation M, Transaction T, Host H, ListingPostedIsIn L, "
				+ "WHERE T.transactionId = M.transactionId AND "
				+ "L.governmentId = H.governmentId AND "
				+ "L.listingId = M.listingId AND "
				+ "L.listingId = T.listingId AND "
				+ "R.email = H.email)";
		
		ResultSet rs = null;

		try {
			PreparedStatement ps= conn.prepareStatement(inactive);
			rs = ps.executeQuery();
			
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}

	//Admin helper to look at all users ever registered
	public List<String> findUsers() {
		ResultSet rs = null;
		String users = "SELECT * FROM RegisteredUser";
		List<String> usersTuples = new ArrayList<String>();

		try {
			PreparedStatement ps= conn.prepareStatement(users);
			rs = ps.executeQuery();
			while(rs.next()){
				String userName = rs.getString("name");
				String email = rs.getString("email");
				String tuple = userName + "," + email;
				usersTuples.add(tuple);
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
	public List<String> findListings() {
		ResultSet rs = null;
		String listings = "SELECT * FROM ListingPostedIsIn LP, Location L WHERE L.postalCode = LP.postalCode";
		List<String> listingTuples = new ArrayList<String>();

		try {
			PreparedStatement ps= conn.prepareStatement(listings);
			rs = ps.executeQuery();
			while(rs.next()){
				String postalCode = rs.getString("postalCode").trim();
				String price = rs.getString("price");
				String city = rs.getString("city");
				String rating = rs.getString("rating");
				String tuple = city + "," + postalCode + "," + price + "," + rating;
				listingTuples.add(tuple);
			}
			ps.close();
			return listingTuples;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return listingTuples;
	}
	
	
	//Admin transaction query helper
	public List<String> findTransactions() {
		ResultSet rs = null;
		String listings = "SELECT T.transactionId, T.price, T.time, L.city, LP.address FROM Transaction T, ListingPostedIsIn LP, Location L where LP.listingId = T.listingId AND L.postalCode = LP.postalCode";
		List<String> transactionTuples = new ArrayList<String>();

		try {
			PreparedStatement ps= conn.prepareStatement(listings);
			rs = ps.executeQuery();
			while(rs.next()){
				String transactionId = rs.getString("transactionId");
				String price = rs.getString("price");
				String city = rs.getString("city");
				String time = rs.getString("time");
				String address = rs.getString("address");
				String tuple = transactionId + "," + address + "," + city + "," + time + "," + price;
				transactionTuples.add(tuple);
			}
			ps.close();
			return transactionTuples;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return transactionTuples;
	}
}
