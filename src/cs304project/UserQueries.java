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
		String transactions = "SELECT r. email, sum(t.price) as total "
				+ "FROM RegisteredUser R, Transaction T, TransactionIdAndEmail TE "
				+ "WHERE R.email = TE.email AND T.transactionId = TE.transactionId "
				+ "GROUP BY r.email";
		
		try {
			conn = Connecting.getConnection();
			System.out.println(transactions);
			PreparedStatement ps = conn.prepareStatement(transactions, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs = ps.executeQuery();
			if(rs.next() && email.contains(rs.getString("email"))){
				transactionTotal = rs.getFloat("total");
			} else {
				transactionTotal = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return transactionTotal;
	}
	
	public String[][] usersTransactions(String email) {
		PreparedStatement ps; 
		conn = Connecting.getConnection(); 
		
		String transactions = "select t.transactionId, t.price, t.time "
				+ "from transaction t, makesreservation mr, transactionidandemail te "
				+ "where t.transactionid = te.transactionId and "
				+ "t.transactionid = mr.transactionid and "
				+ "te.email like '%floriecai@hotmail.com%' " 
				+ "order by t.time";
		String[][] transactionTuples = null;
		
		try {
			ps = conn.prepareStatement(transactions, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery(); 
			
			rs.last();
			int rowCount = rs.getRow();
			transactionTuples = new String[rowCount][3];
			rs.beforeFirst(); 
			
			while (rs.next()) {
				transactionTuples[rs.getRow() - 1][0] = rs.getString("transactionId");
				transactionTuples[rs.getRow() - 1][1] = rs.getString("price");
				transactionTuples[rs.getRow() - 1][2] = rs.getString("time"); 
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		
		return transactionTuples; 
	}
	
}
