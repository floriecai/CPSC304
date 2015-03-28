package cs304project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQueries {
	private static Connection conn; 
	private static String pc;
	public static String[] listingIds;
	
	public float amount(String email){
		
		ResultSet rs = null;
		float transactionTotal = 0;
		String transactions = "SELECT TE.email, sum(t.price) as total "
				+ "FROM Transaction T, TransactionIdAndEmail TE "
				+ "WHERE T.transactionId = TE.transactionId AND TE.email LIKE '%" + email +"%' " 
				+ "GROUP BY TE.email";
		try {
			conn = Connecting.getConnection();
			System.out.println(transactions);
			PreparedStatement ps = conn.prepareStatement(transactions, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			if(rs.next() && email.contains(rs.getString("email"))){
				transactionTotal = rs.getFloat("total");
				System.out.println(transactionTotal);
			}else{
				transactionTotal = 0;
				System.out.println(transactionTotal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return transactionTotal;
	}
	
	public String[][] usersTransactions(String email) {
		PreparedStatement ps;
		conn = Connecting.getConnection();
		String transactions = "select *"
		+ "from transaction t, makesreservation mr, transactionidandemail te "
		+ "where t.transactionid = te.transactionId and "
		+ "t.transactionid = mr.transactionid and "
		+ "te.email like ? "
		+ "order by t.time";
		String[][] transactionTuples = null;
		try {
			ps = conn.prepareStatement(transactions, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
		transactionTuples = new String[rowCount][3];
			rs.beforeFirst();
			while (rs.next()) {
				transactionTuples[rs.getRow() - 1][0] = rs.getString("transactionId");
				transactionTuples[rs.getRow() - 1][1] = "$" + rs.getString("price");
				transactionTuples[rs.getRow() - 1][2] = rs.getString("time");
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return transactionTuples;
		}
	
	public String[][] userListings(String email){
		ResultSet rs = null;
		String users = "SELECT * FROM RegisteredUser r, ListingPostedIsIn l, Host h "
				+ "WHERE r.email = ? and r.email = h.email and l.governmentId = h.governmentId";
		String[][] listingTuples = null;
		try {
			PreparedStatement ps= conn.prepareStatement(users, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, email);
			rs = ps.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			listingTuples = new String[rowCount][6];
			listingIds = new String[rowCount];
			rs.beforeFirst();
			
			while(rs.next()){			
				listingIds[rs.getRow()-1] = String.valueOf(rs.getInt("listingId"));
				listingTuples[rs.getRow()-1][0] = String.valueOf(rs.getInt("listingId"));
				listingTuples[rs.getRow()-1][1] = rs.getString("price");
				listingTuples[rs.getRow()-1][2] = rs.getString("capacity");
				listingTuples[rs.getRow()-1][3] = rs.getString("rating");
				listingTuples[rs.getRow()-1][4] = rs.getString("address");
				listingTuples[rs.getRow()-1][5] = rs.getString("postalCode");
			}
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return listingTuples;
	}
	
	
	public boolean newList(double price, int capacity, String is_private, String governmentId, String postalCode, String address,
			String tv, String kitchen, String toiletries, String internet, String laundry){

		double rating = 1;
		int listingId = 0;
		String instList = "INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) "
				+ "VALUES (listing_seq.nextval, ?, ?, ?, ?, ? ,?, ?)";

		try {
			PreparedStatement ps= conn.prepareStatement(instList, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setDouble(1, price);
			ps.setInt(2, capacity);
			ps.setString(3, is_private);
			ps.setDouble(4, rating);
			ps.setString(5, governmentId);
			ps.setString(6, postalCode);
			ps.setString(7, address);
			ps.executeUpdate();
			conn.commit();
			
			String l = "Select listingId from ListingPostedIsIn";
			System.out.println("Ok");
			ps= conn.prepareStatement(l, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery(l);
			if(rs.last()){
				listingId = rs.getInt("listingId");
			}
			System.out.println(listingId);
			String instAment = "INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) "
					+ "values (amenities_seq.nextval, ?, ?, ?, ?, ?, ?)";
			ps= conn.prepareStatement(instAment, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, listingId);
			ps.setString(2, tv);
			ps.setString(3, kitchen);
			ps.setString(4, internet);
			ps.setString(5, laundry);
			ps.setString(6, toiletries);
			ps.executeUpdate();
			conn.commit();
			ps.close();
			return true;
		} catch (SQLException e) {
			// handle in UserBoard
			return false;
		} 
	}
	
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
		return usersTuples;
	}
	
	public boolean delList(int listingId){
		ResultSet rs = null;
		String delList =" delete FROM ListingPostedIsIn WHERE listingId = ?";
		try {
			PreparedStatement ps= conn.prepareStatement(delList, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, listingId);
			ps.executeUpdate();
			conn.commit();
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 

	}

	public boolean newLoc(String postalCode, String city, String country) {
		PreparedStatement ps;
		String selLoc = "Select postalCode from Location"; 
		try {
			ps = conn.prepareStatement(selLoc, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
				pc = postalCode;
				String instLoc = "INSERT INTO Location (postalCode, city, country) "
						+ "VALUES (? ,?, ?)";
				ps = conn.prepareStatement(instLoc, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ps.setString(1, pc);
				ps.setString(2, city);
				ps.setString(3, country);
				ps.executeUpdate();
				conn.commit();
				ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getPC(){
		return pc;
	}

	public boolean upList(double price, int capacity, String is_private, int listingId, String tv, String kitchen, String toiletries, String internet, String laundry) {
		String instList = "UPDATE ListingPostedIsIn "
				+ "SET price = ?, capacity = ?, private = ?"
				+ "WHERE listingId = ?";

		try {
			PreparedStatement ps= conn.prepareStatement(instList, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setDouble(1, price);
			ps.setInt(2, capacity);
			ps.setString(3, is_private);
			ps.setInt(4, listingId);
			ps.executeUpdate();
			conn.commit();
			String instAment = "UPDATE AmenitiesIncluded  "
					+ "SET tv  = ?, kitchen = ?, internet = ?, laundry = ?, toiletries = ?"
					+ "WHERE listingId = ?";
			ps= conn.prepareStatement(instAment, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, tv);
			ps.setString(2, kitchen);
			ps.setString(3, internet);
			ps.setString(4, laundry);
			ps.setString(5, toiletries);
			ps.setInt(6, listingId);
			ps.executeUpdate();
			conn.commit();
			ps.close();
		return true;
		}catch (SQLException e) {
			e.printStackTrace();
		return false;
		}
	}
	
	public static String[] getListingId(){
		return listingIds;
	}
}
