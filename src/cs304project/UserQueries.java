package cs304project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQueries {
	private static Connection conn; 
	
	public float amount(String email){
		
		ResultSet rs = null;
		float transactionTotal = 0;
		String transactions = "SELECT r.email, sum(t.price) as total FROM Transaction t, Host h, ListingPostedIsIn l, RegisteredUser r "
				+ "WHERE r.email = h.email and t.listingId = l.listingId and l.governmentId = h.governmentId group by r.email";
		
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
	
}
