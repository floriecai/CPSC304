package cs304project;

import java.sql.Connection;

public abstract class Transactions {
	Connection conn;
	public Transactions(Connection conn) {
		this.conn = conn;
	}
}
