package cs304project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Listing extends Transactions{

	private int[] listId;

	public Listing(Connection conn) {
		super(conn); 
	}

	public ResultSet topThree() {
		ResultSet rs = null;
		Statement stmt; 

		String query = "SELECT loc.city, loc.country, AVG(list.rating), AVG(list.price) as avp "
				+ "from Location loc, ListingPostedIsIn list "
				+ "where list.postalCode = loc.postalCode "
				+ "group by loc.city, loc.country";

		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs; 
	}
	
	// Add a listing, rating is default NULL
	public void addListing(String listingId, float price, int capacity, String isPrivate,
			String govId, String PC, String addr) {

		PreparedStatement ps; 

		try {
			ps = conn.prepareStatement("INSERT INTO ListingIsPostedIn VALUES (?, ?, ?, "
					+ "?, NULL, ?, ? ,?)"); 

			ps.setString(1, listingId);
			ps.setFloat(2, price);
			ps.setInt(3, capacity); 
			ps.setString(4, listingId);
			ps.setString(5, govId);
			ps.setString(6, PC); 
			ps.setString(7, addr);

			ps.executeUpdate();
			ps.close(); 

			System.out.println("Listing: " + listingId + "was added!");

		} catch (SQLException e) {
			System.out.println("Could not insert because: ");
			e.printStackTrace();
		}
	}

	public void deleteListing(String listingId) {
		PreparedStatement ps; 

		try {
			String toDelete = "DELETE FROM ListingIsPostedIn WHERE listingId = ?";
			ps = conn.prepareStatement(toDelete);

			ps.setString(1,listingId);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
				System.out.println("This listing could not be deleted because it does not exist");

			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}	
	}

	// Given a city, find all listings in that fit the criteria
	public String[][] showAllListings(String city, int capacity, char[] amenities, String cdIn, String cdOut, String sortBy) {
		PreparedStatement ps;
		ResultSet rs = null; 
		String data[][] = null;
		
		String c[] = {"Host", "Capacity", "Rating", "Address", "Price"};
		try {
			String selectAll = "SELECT DISTINCT * FROM ListingPostedIsIn l, AmenitiesIncluded a, Host h, RegisteredUser r WHERE "
					+ "h.governmentId = l.governmentId AND "
					+ "a.listingId = l.listingId AND "
					+ "r.email = h.email AND "
					+ "l.capacity = " + capacity
					+ "  AND a.tv like '%" +  amenities[0] + "%' and a.laundry like '%" + amenities[1] + "%' and a.toiletries like '%" + amenities[2] + "%' and a.kitchen like '%" + amenities[3] +"%' AND"
					+ " l.listingId in"
					+ "(SELECT DISTINCT l.listingId FROM Location loc "
					+ "WHERE l.postalCode = loc.postalCode AND loc.city like '%" + city + "%') "
					+ "AND l.listingId not in"
					+ "(SELECT DISTINCT l.listingId FROM MakesReservation m WHERE l.listingId = m.listingId "
					+ "AND (m.checkindate <= TO_DATE('" + cdIn + "', 'YYYY-MM-DD') AND"
					+ " m.checkoutdate >= TO_DATE('" +  cdOut + "','YYYY-MM-DD'))) " 
					+ "ORDER BY " + sortBy;
			System.out.println(selectAll);
			ps = conn.prepareStatement(selectAll, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			System.out.println(rowCount);
			data = new String[rowCount][c.length];
			listId = new int[rowCount];
			rs.beforeFirst();

			while(rs.next()){
				listId[rs.getRow()-1] = rs.getInt("listingId");
				data[rs.getRow()-1][0] = rs.getString("name");
				data[rs.getRow()-1][1] = String.valueOf(rs.getInt("Capacity"));
				data[rs.getRow()-1][2] = String.valueOf(rs.getDouble("Rating"));
				data[rs.getRow()-1][3] = rs.getString("Address");
				data[rs.getRow()-1][4] = String.valueOf(rs.getDouble("Price"));	 
				System.out.println(rs.getString("name"));
			}		

		} catch (SQLException e) {

		}
		return data;
	}
	
	public int listId(int selection){
		return listId[selection];
		
	}

	// Find cheapest listing's address, city, and postalcode and the price 
	public ResultSet cheapestCityOnAvg() {
		PreparedStatement ps;
		ResultSet rs = null; 

		try {
			String nestedAvg = "(SELECT AVG(LP.rating) " 
					+ "FROM ListingPostedIsIn LP, Location L "
					+ "WHERE LP.postalCode = L.postalCode "
					+ "GROUP BY (LP.city, LP.postalCode)) ";

			String min_price = "CREATE VIEW min_price AS "
					+ "SELECT L.city, LP.postalCode, MIN(LP.price) as min "
					+ "FROM ListingPostedIsIn LP, Location L"
					+ "WHERE LP.postalCode = L.postalCode AND"
					+ "LP.rating >= "
					+ nestedAvg
					+ "GROUP BY (LP.city, LP.postalCode)"; 

			// Created view for min_price
			ps = conn.prepareStatement(min_price); 
			ps.executeUpdate(); 

		} catch (SQLException e) {
			System.out.println("Table already exists: ");
		}

		finally {
			String finalResult = "SELECT LP.address, M.city, M.postalCode, M.min "
					+ "FROM min_price M, ListingPostedIsIn LP "
					+ "WHERE LP.postalCode = M.postalCode ";

			try {
				ps = conn.prepareStatement(finalResult);
				rs = ps.executeQuery(); 

				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;
	}


}
