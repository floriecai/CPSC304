package cs304project;

import java.sql.Connection;
import java.sql.Date;
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
		String inactive =  "Select R.email "
				+ "FROM RegisteredUser R "
				+ "MINUS "
				+ "(Select distinct H.email "
				+ "FROM ListingPostedIsIn L, Host H "
				+ "WHERE l.governmentId = H.governmentId UNION "
				+ "Select t.email from TransactionIdAndEmail t)";
		
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
				usersInactive[rs.getRow()-1][0] = rs.getString("email");
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
		String listings = "SELECT T.transactionId, T.price, T.time, L.city, LP.address FROM Transaction T, ListingPostedIsIn LP, Location L "
				+ "where LP.listingId = T.listingId AND L.postalCode = LP.postalCode AND T.time = TRUNC(SYSDATE)";
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
				transactionTuples[rs.getRow()-1][1] = String.valueOf(rs.getFloat("price"));
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
	
	public String findMinOrMaxAvgTransaction(String agg) {
		PreparedStatement ps;
		String result = ""; 
		
		try {
			String aggregation = "SELECT time, avg(price) "
					+ "from transaction " 
					+ "group by time " 
					+ "having avg(price) = " 
					+ "(SELECT " + agg + " (avg(price)) FROM transaction group by time)";
					
					
			ps = conn.prepareStatement(aggregation);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				String day = rs.getString("time");
				result = day.substring(0, 11);
				result = result + ": ";
				result += rs.getString("avg(price)");
				System.out.println(result);
				return result;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String findAvgTransactionsEachDay() {
		String s = "CREATE VIEW daily_transactions AS "
				+ " SELECT T.time, SUM(price) as sum "
				+ "FROM Transaction T " 
				+ "GROUP BY T.time ";
		try {
			PreparedStatement ps = conn.prepareStatement(s);
			String avg = "";
			ps.executeUpdate(); 
			ps.close();
			
			String ss = "select AVG(DT.sum) as avg"
					+ "from daily_transactions DT";
			ps = conn.prepareStatement(s);
			
			ResultSet rs = ps.executeQuery(); 
			if (rs.next()) {
				avg = rs.getString("avg"); 
			}
			return avg; 
		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	public String[][] findTransactionsByDate() {
		ResultSet rs = null;
		String listings = "SELECT sum(T.price) as total, T.time FROM Transaction T, ListingPostedIsIn LP, Location L where LP.listingId = T.listingId AND L.postalCode = LP.postalCode group by time";
		String[][] transactionTuples = null;

		try {
			PreparedStatement ps= conn.prepareStatement(listings, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			
			rs.last();
			int rowCount = rs.getRow();
			transactionTuples = new String[rowCount][2];
			rs.beforeFirst();
			
			while(rs.next()){
				transactionTuples[rs.getRow()-1][0] = rs.getString("time");
				transactionTuples[rs.getRow()-1][1] = "$" + String.valueOf(rs.getFloat("total"));
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return transactionTuples;
	}
	
	public boolean upRate(int listingId, double rate) {
		String instList = "UPDATE ListingPostedIsIn "
				+ "SET rating = ?"
				+ "WHERE listingId = ?";
		try {
			PreparedStatement ps= conn.prepareStatement(instList, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setDouble(1, rate);
			ps.setInt(2, listingId);
			
			ps.executeUpdate();
			conn.commit();
			ps.close();
		return true;
		}catch (SQLException e) {
			e.printStackTrace();
		return false;
		}
	}
	
}
