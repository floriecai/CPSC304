package cs304project.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.UIManager;

import cs304project.Connecting;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Label;
import java.awt.Dimension;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class Index extends JFrame {

	private JPanel contentPane;
	private JTextField userField;
	private static JTextField searchField;
	private static Connection conn;
	private Listings listing;
	private static String userName;
	private UserBoard usb;
	private static Statement stmt;
	private JTextField textField;
	private String userEmail;
	private String userPassword;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
					frame.setVisible(true);
					conn = Connecting.getConnection();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Index() {
		setBackground(Color.WHITE);
		setResizable(false);

		String cc[] = new String[4];
		JLabel cityImage0 = new JLabel("test");
		JLabel cityImage1 = new JLabel("test");
		JLabel cityImage2 = new JLabel("test");
		JLabel cc0 = new JLabel("city - country");
		JLabel cc1 = new JLabel("city - country 2");
		JLabel cc2 = new JLabel("city - country 3");
		

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				int count = 0;
				
				/*
				 * Query!!!
				 * Return the top 3 locations with best rating and the avg price of their listings - Need to change the query to get price and avg rating
				 */
				String query = "SELECT loc.city, loc.country from Location loc, ListingPostedIsIn list where list.postalCode = loc.postalCode order by list.rating desc ";
				System.out.println(query);
				conn = Connecting.getConnection();
				try {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(query);			
				while(rs.next() && count < 3 ){
					cc[count] = rs.getString("city").trim() + "-" + rs.getString("country").trim();
					count++;
					}
				cc0.setText(cc[0]);
				cc1.setText(cc[1]);
				cc2.setText(cc[2]);

				cityImage0.setIcon(new ImageIcon(Index.class.getResource("/cs304project/" + cc0.getText().substring(0, cc0.getText().indexOf("-")) + ".jpg")));
				cityImage1.setIcon(new ImageIcon(Index.class.getResource("/cs304project/" + cc1.getText().substring(0, cc1.getText().indexOf("-")) + ".jpg")));
				cityImage2.setIcon(new ImageIcon(Index.class.getResource("/cs304project/" + cc2.getText().substring(0, cc2.getText().indexOf("-")) + ".jpg")));
		    	}
	 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		
		setTitle("Ourhome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 648);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));	
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.window);
		contentPane.add(panel, BorderLayout.NORTH);	
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("13px"),
				ColumnSpec.decode("center:default"),
				ColumnSpec.decode("40dlu"),
				ColumnSpec.decode("201px"),
				ColumnSpec.decode("31px"),
				ColumnSpec.decode("68px:grow"),
				ColumnSpec.decode("200px"),
				ColumnSpec.decode("202px"),},
			new RowSpec[] {
				RowSpec.decode("128px"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("19px"),
				RowSpec.decode("46px"),
				RowSpec.decode("27px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("150px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
				RowSpec.decode("112px"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("17px"),}));
		
		searchField = new JTextField();
		searchField.setSize(new Dimension(200, 20));
		searchField.setMaximumSize(new Dimension(200, 20));
		searchField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchField.setText("");
			}
		});
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Index.class.getResource("/cs304project/bg.png")));
		panel.add(lblNewLabel, "2, 1, 6, 1");
		searchField.setText("Where do you want to go?");
		searchField.setColumns(10);
		panel.add(searchField, "2, 3, fill, fill");
		
		JButton search = new JButton("Search");
		search.setAlignmentX(Component.CENTER_ALIGNMENT);
		search.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 * Get the results of the search executed by the user, returning all available listings 
			 * in a specific city
			 */
			public void actionPerformed(ActionEvent e) {
				String c[] = {"Host", "Capacity", "Rating", "Address", "Price"};
				String data[][] = null;
				String city = searchField.getText();
				int rowCount = 0;
				conn = Connecting.getConnection();
				
				/*
				 * Query!!!
				 */
				String query = "select * from ListingPostedIsIn l, Host h, RegisteredUser r where h.governmentId = l.governmentId and r.email = h.email and l.listingId in " +
						"(select distinct l.listingId from Location loc where l.postalCode = loc.postalCode and loc.city like '%" + city + "%')";
				System.out.println(query);
				
				try {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(query);	
				rs.last();
				rowCount = rs.getRow();	
				System.out.println("count " + rowCount);
				data = new String[rowCount][c.length];	
				rs.beforeFirst();
				
				 while(rs.next()){
					 data[rs.getRow()-1][0] = rs.getString("userName");
					 data[rs.getRow()-1][1] = String.valueOf(rs.getInt("Capacity"));
					 data[rs.getRow()-1][2] = String.valueOf(rs.getDouble("Rating"));
					 data[rs.getRow()-1][3] = rs.getString("Address");
					 data[rs.getRow()-1][4] = String.valueOf(rs.getDouble("Price"));
				 }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				listing = new Listings(c, data);
				listing.setVisible(true);
				setVisible(false);
			}
		});
		panel.add(search, "3, 3, 2, 1, left, fill");
		JLabel user = new JLabel("User ID");
		panel.add(user, "6, 3, right, fill");
		userField = new JTextField();
		userField.setColumns(10);
		panel.add(userField, "7, 3, fill, fill");
		
		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword, "6, 4, right, default");
		
		textField = new JTextField();
		panel.add(textField, "7, 4, fill, default");
		textField.setColumns(10);
		
		JButton logIn = new JButton("Log in");
		logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
		logIn.addActionListener(new ActionListener() {
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 * Registered user login - not full implemented yet
			 */
			public void actionPerformed(ActionEvent arg0) {
				String userId = null;
				if(userField.getText() != null){
					userId = userField.getText().trim();
				}
				try {
					stmt = conn.createStatement();
					
					/*
					 * Query!! It's not corre
					 */
					String query = "SELECT * FROM RegisteredUser WHERE email LIKE '%"  + userEmail + "%' AND password LIKE '%" + userPassword.toString() + "%'" ;
					System.out.println(query);
					PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
	                        ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = ps.executeQuery();
					while(rs.next()){
						String test = rs.getString("email");
						if(rs.next()){
			    		   userName = rs.getString("name");
						}
						usb = new UserBoard();
						usb.setVisible(true);
			    	}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				UserBoard ub = new UserBoard();
				ub.setVisible(true);
			}
		});
		panel.add(logIn, "7, 5, right, top");
		
		JLabel lblNicePlacesTo = new JLabel("Nice Places to Visit");
		lblNicePlacesTo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNicePlacesTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNicePlacesTo.setFont(new Font("Myriad Hebrew", Font.BOLD, 40));
		panel.add(lblNicePlacesTo, "1, 7, 7, 1, center, top");
	
		cityImage0.setPreferredSize(new Dimension(200, 150));
		cityImage0.setMinimumSize(new Dimension(200, 150));
		cityImage0.setHorizontalTextPosition(SwingConstants.CENTER);
		cityImage0.setHorizontalAlignment(SwingConstants.CENTER);
		cityImage0.setMaximumSize(new Dimension(200, 150));
		cityImage0.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(cityImage0, "2, 9, right, top");
		
		cityImage1.setPreferredSize(new Dimension(200, 150));
		cityImage1.setHorizontalTextPosition(SwingConstants.CENTER);
		cityImage1.setHorizontalAlignment(SwingConstants.CENTER);
		cityImage1.setMaximumSize(new Dimension(200, 150));
		cityImage1.setAlignmentX(0.5f);
		panel.add(cityImage1, "4, 9, right, top");
		
		cityImage2.setMinimumSize(new Dimension(200, 150));
		cityImage2.setPreferredSize(new Dimension(200, 150));
		cityImage2.setMaximumSize(new Dimension(200, 150));
		cityImage2.setHorizontalAlignment(SwingConstants.CENTER);
		cityImage2.setHorizontalTextPosition(SwingConstants.CENTER);
		cityImage2.setAlignmentX(0.5f);
		panel.add(cityImage2, "7, 9, left, top");
		
		cc0.setFont(new Font("Myriad Hebrew", Font.BOLD, 14));
		cc0.setHorizontalTextPosition(SwingConstants.CENTER);
		cc0.setPreferredSize(new Dimension(200, 15));
		cc0.setMinimumSize(new Dimension(70, 14));
		cc0.setMaximumSize(new Dimension(70, 14));
		
		cc0.setHorizontalAlignment(SwingConstants.CENTER);
		cc0.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(cc0, "2, 11, right, bottom");
		
		cc1.setFont(new Font("Myriad Hebrew", Font.BOLD, 14));
		cc1.setHorizontalTextPosition(SwingConstants.CENTER);
		cc1.setHorizontalAlignment(SwingConstants.CENTER);
		cc1.setPreferredSize(new Dimension(200, 15));
		cc1.setMinimumSize(new Dimension(200, 15));
		cc1.setMaximumSize(new Dimension(200, 15));
		cc1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(cc1, "4, 11, fill, top");
		
		cc2.setFont(new Font("Myriad Hebrew", Font.BOLD, 14));
		cc2.setHorizontalTextPosition(SwingConstants.CENTER);
		cc2.setHorizontalAlignment(SwingConstants.CENTER);
		cc2.setMinimumSize(new Dimension(70, 14));
		cc2.setMaximumSize(new Dimension(70, 14));
		cc2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(cc2, "7, 11, fill, top");
		
		JLabel price0 = new JLabel("Price");
		price0.setPreferredSize(new Dimension(200, 15));
		price0.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(price0, "2, 13, right, top");
		
		JLabel price1 = new JLabel("Price");
		price1.setHorizontalTextPosition(SwingConstants.CENTER);
		price1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(price1, "4, 13, fill, top");
		JLabel price2 = new JLabel("Price");
		price2.setHorizontalTextPosition(SwingConstants.CENTER);
		price2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(price2, "7, 13, fill, top");
		
		JLabel lblStaffArea = new JLabel("Administrators Area");
		lblStaffArea.setHorizontalTextPosition(SwingConstants.CENTER);
		lblStaffArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Admin admin = new Admin();
				admin.setVisible(true);
				
			}
		});
		lblStaffArea.setBackground(Color.LIGHT_GRAY);
		lblStaffArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStaffArea.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblStaffArea, "7, 14, right, bottom");
	}
	/*
	 * Get user name
	 */
	public static String  getUser(){
		return userName;
	}
	
}
