package cs304project.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.SwingConstants;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.SystemColor;

import cs304project.Connecting;
import cs304project.Listing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	private JPasswordField userPass;
	String cc[] = new String[3];
	float pp[] = new float[3];
	JLabel cityImage0 = new JLabel("test");
	JLabel cityImage1 = new JLabel("test");
	JLabel cityImage2 = new JLabel("test");
	JLabel cc0 = new JLabel("city - country");
	JLabel cc1 = new JLabel("city - country 2");
	JLabel cc2 = new JLabel("city - country 3");
	JLabel price0 = new JLabel("Price");
	JLabel price1 = new JLabel("Price");
	JLabel price2 = new JLabel("Price");

	private static Listing top3; 
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

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				sortByIndexPage(true);
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
				RowSpec.decode("22px"),
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

			public void actionPerformed(ActionEvent e) {
				search.setEnabled(false);
				String c[] = {"Host", "Capacity", "Rating", "Address", "Price"};
				String data[][] = null;
				String city = searchField.getText();
				int rowCount = 0;
				conn = Connecting.getConnection();
				if (searchField.getText().length() < 1 || searchField.getText().contains("Where do you want to go?")) {
					JOptionPane.showMessageDialog(null, "Please enter a location.");
					search.setEnabled(true);
					return;
				}

				String query = "select * from ListingPostedIsIn l, Host h, RegisteredUser r where h.governmentId = l.governmentId and r.email = h.email and l.listingId in " +
						"(select distinct l.listingId from Location loc where l.postalCode = loc.postalCode and loc.city like '%" + city + "%')";
				
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
						data[rs.getRow()-1][0] = rs.getString("name");
						data[rs.getRow()-1][1] = String.valueOf(rs.getInt("Capacity"));
						data[rs.getRow()-1][2] = String.valueOf(rs.getDouble("Rating"));
						data[rs.getRow()-1][3] = rs.getString("Address");
						data[rs.getRow()-1][4] = String.valueOf(rs.getDouble("Price"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				listing = new Listings(c, data);
				listing.setVisible(true);
				search.setEnabled(true);
				setVisible(false);
				search.setEnabled(true);
			}
		});
		panel.add(search, "3, 3, 2, 1, left, fill");
		JLabel user = new JLabel("User ID");
		panel.add(user, "6, 3, right, fill");
		userField = new JTextField();
		userField.setColumns(10);
		panel.add(userField, "7, 3, fill, fill");
		
		JLabel lblNotRegisteredYet = new JLabel("Not registered yet?");
		lblNotRegisteredYet.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblNotRegisteredYet, "2, 4, left, default");
		
		JButton btnSignUp = new JButton("Sign up");
		panel.add(btnSignUp, "3, 4, 2, 1, left, default");
		
		btnSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserRegister registering = new UserRegister();
				registering.setVisible(true);
			}
			
		});

		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword, "6, 4, right, default");

		userPass = new JPasswordField();
		panel.add(userPass, "7, 4, fill, default");
		userPass.setColumns(10);

		JButton logIn = new JButton("Log in");
		logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				String userId = null;
				String userPassword = null;
				logIn.setEnabled(false);
				if(userField.getText().length() > 0 && userPass.getText().length() > 0){
					userId = userField.getText().trim();
					userPassword = userPass.getText().trim();
				} else {
					JOptionPane.showMessageDialog(null, "Username or password is empty");
					logIn.setEnabled(true);
					return;
				}

				try {
					stmt = conn.createStatement();

					String query = "SELECT * FROM RegisteredUser WHERE email LIKE '%"  + userId + "%' AND password LIKE '%" + userPassword + "%'" ;
					System.out.println(query);
					PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = ps.executeQuery();
					String email = null;
					if(rs.next()){
						email = rs.getString("email");
						userName = rs.getString("name");
						usb = new UserBoard(email, userName);
						usb.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Your username or password is incorrect!");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					logIn.setEnabled(true);
				}
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


		price0.setPreferredSize(new Dimension(200, 15));
		price0.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(price0, "2, 13, right, top");


		price1.setHorizontalTextPosition(SwingConstants.CENTER);
		price1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(price1, "4, 13, fill, top");

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
				setVisible(false);
			}
		});
		
		JButton ratingSortBtn = new JButton("Sort by rating");
		panel.add(ratingSortBtn, "2, 14");
		ratingSortBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					sortByIndexPage(true);
			}
			
		});
		JButton cheapBtn = new JButton("Cheap location with great ratings!");
		panel.add(cheapBtn, "4, 14, 2, 1, center, default");
		
		cheapBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					sortByIndexPage(false);
			}
			
		});
		lblStaffArea.setBackground(Color.LIGHT_GRAY);
		lblStaffArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStaffArea.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblStaffArea, "7, 14, right, bottom");
	}
	protected void sortByIndexPage(boolean rating) {
		int count = 0;
		conn = Connecting.getConnection();
		DecimalFormat df = new DecimalFormat("##.##");
		ResultSet rs = null;
		top3 = new Listing(conn);

		if (rating) {
			rs = top3.topThree();
		} else {
			rs = top3.valueSort();

		}
		try {
			while(rs.next() && count < 3 ){
				cc[count] = rs.getString("city").trim() + "-" + rs.getString("country").trim();
				if (rating)
					pp[count] = rs.getFloat("avp");
				else
					pp[count] = rs.getFloat("min");

				count++;
			}
			if (count < 3) {
				cc0.setText("Not enough locations");
				cc1.setText("Not enough locations");
				cc2.setText("Not enough locations");
				price0.setText("--.-- per day");
				price1.setText("--.-- per day");
				price2.setText("--.-- per day");
				cityImage0.setIcon(new ImageIcon(Index.class.getResource("/cs304project/default.jpg")));
				cityImage1.setIcon(new ImageIcon(Index.class.getResource("/cs304project/default.jpg")));
				cityImage2.setIcon(new ImageIcon(Index.class.getResource("/cs304project/default.jpg")));
			}
			
			cc0.setText(cc[0]);
			price0.setText("$" + df.format(pp[0]) + " per day");
			cc1.setText(cc[1]);
			price1.setText("$" + df.format(pp[1]) + " per day");
			cc2.setText(cc[2]);
			price2.setText("$" + df.format(pp[2]) + " per day");
			
			try {
				cityImage0.setIcon(new ImageIcon(Index.class.getResource("/cs304project/" + cc0.getText().substring(0, cc0.getText().indexOf("-")) + ".jpg")));
			} catch (NullPointerException e) {
				cityImage0.setIcon(new ImageIcon(Index.class.getResource("/cs304project/default.jpg")));
			}
			try {
				cityImage1.setIcon(new ImageIcon(Index.class.getResource("/cs304project/" + cc1.getText().substring(0, cc1.getText().indexOf("-")) + ".jpg")));
			} catch (NullPointerException e) {
				cityImage1.setIcon(new ImageIcon(Index.class.getResource("/cs304project/default.jpg")));
			}
			try {
				cityImage2.setIcon(new ImageIcon(Index.class.getResource("/cs304project/" + cc2.getText().substring(0, cc2.getText().indexOf("-")) + ".jpg")));
			} catch (NullPointerException e) {
				cityImage2.setIcon(new ImageIcon(Index.class.getResource("/cs304project/default.jpg")));
			}

			if (!rating) {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				String dropView = "DROP VIEW min_price CASCADE CONSTRAINTS";
				stmt.executeQuery(dropView);
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Get user name
	 */
	public static String  getUser(){
		return userName;
	}

}
