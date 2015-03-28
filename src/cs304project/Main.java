package cs304project;


import cs304project.client.ui.*;

public class Main {
	static Connecting conn;
	
	public static void main(String[] args) {
		Index index = new Index();
		conn = new Connecting();	
		if(conn.connecting()){
			index.setVisible(true);
		}
		
	}

}
