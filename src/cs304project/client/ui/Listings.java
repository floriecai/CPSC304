package cs304project.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import cs304project.Connecting;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class Listings extends JFrame {

	private JPanel contentPane;
	private static Listings frame;
	private static JTextField txtCheckInDate;
	private static JTextField txtCheckOutDate;
	private JTextField txtCity;
	private static Connection conn;
	private static Statement stmt;
	private JTable table;
	private static JTable localTable;
	private String c[] = {"Host", "Capacity", "Rating", "Address", "Price"};
	private String data[][];
	private Listings list;
	private JRadioButton[] selecting;
	private static String[] listId;
	private UserLogin userLogin;
	DefaultTableModel searchTableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame = new Listings(null, null);
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
	 * @param r 
	 * @param c 
	 */
	public Listings(String[] c, String[][] r) {
		setResizable(false);
		JCheckBox checkBox = new JCheckBox("Toiletries");
		JCheckBox checkBox_1 = new JCheckBox("Laundry");
		JCheckBox checkBox_2 = new JCheckBox("Television");
		JCheckBox checkBox_3 = new JCheckBox("Kitchen");
		JCheckBox[] checkBoxes = {checkBox_2, checkBox_1, checkBox, checkBox_3};
		char[] amenities = new char[checkBoxes.length];
		checkBoxes[0].setHorizontalAlignment(SwingConstants.LEFT);
		JCheckBox intBox = new JCheckBox("Internet");
		JLabel amnTxt = new JLabel("Amenities");
		JLabel lblNewLabel_1 = new JLabel("Sort By:");
		JScrollPane scrollPane = new JScrollPane();
		JComboBox comboBox = new JComboBox();
		JComboBox sortBox = new JComboBox();
		
		searchTableModel = new DefaultTableModel (r,c);
		table = new JTable(searchTableModel);
		table.setRowSelectionAllowed(false);
		scrollPane.setViewportView(table);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Listings.class.getResource("/cs304project/bg.png")));
		
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String c[] = {"Host", "Capacity", "Rating", "Address", "Price"};
				String data[][] = null;
				String city = txtCity.getText();
				String cdIn = txtCheckInDate.getText();
				String cdOut = txtCheckOutDate.getText();
				
				for(int i = 0; i < checkBoxes.length; i++){
					if(checkBoxes[i].isSelected()){
						amenities[i] = 'y';
					} else
						amenities[i] = 'n';
				}

				int rowCount = 0;
				conn = Connecting.getConnection();
				
				/*
				 * Query!
				 */
				String query = "select distinct * from ListingPostedIsIn l, AmenitiesIncluded a, Host h, RegisteredUser r where "
						+ "h.governmentId = l.governmentId and a.listingId = l.listingId and r.email = h.email and l.capacity = " + (comboBox.getSelectedIndex() + 1)
						+ "  and a.tv like '%" +  amenities[0] + "%' and a.laundry like '%" + amenities[1] + "%' and a.toiletries like '%" + amenities[2] + "%' and a.kitchen like '%" + amenities[3] +"%' and l.listingId in"
						+ "(select distinct l.listingId from Location loc where l.postalCode = loc.postalCode and loc.city like '%" + city + "%') and l.listingId not in"
						+ "(select distinct l.listingId from MakesReservation m where l.listingId = m.listingId and (m.checkindate <= TO_DATE('" + cdIn + "', 'YYYY-MM-DD') and m.checkoutdate >= TO_DATE('" +  cdOut + "','YYYY-MM-DD'))) " 
						+ "order by " + sortBox.getSelectedItem();
				System.out.println(query);
				try {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(query);
				rs.last();
				rowCount = rs.getRow();
				data = new String[rowCount][c.length];
				listId = new String[rowCount];
				rs.beforeFirst();
				
				 while(rs.next()){
					 listId[rs.getRow()-1] = rs.getString("listingId");
					 data[rs.getRow()-1][0] = rs.getString("userName");
					 data[rs.getRow()-1][1] = String.valueOf(rs.getInt("Capacity"));
					 data[rs.getRow()-1][2] = String.valueOf(rs.getDouble("Rating"));
					 data[rs.getRow()-1][3] = rs.getString("Address");
					 data[rs.getRow()-1][4] = String.valueOf(rs.getDouble("Price"));	 
				 }		
				 
				 searchTableModel = new DefaultTableModel (data,c);
				 localTable = new JTable(searchTableModel);
				 table.setRowSelectionAllowed(true);
				 table.setCellSelectionEnabled(false);
				 scrollPane.setViewportView(localTable);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});

		txtCheckInDate = new JTextField();
		txtCheckInDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCheckInDate.setText("");
			}
		});
		txtCheckInDate.setText("Check In ");
		txtCheckInDate.setColumns(10);
		
		txtCheckOutDate = new JTextField();
		txtCheckOutDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtCheckOutDate.setText("");
			}
		});
		txtCheckOutDate.setText("Check Out ");
		txtCheckOutDate.setColumns(10);
		

		comboBox.setBackground(Color.WHITE);
		comboBox.setMaximumRowCount(10);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1 Guest", "2 Guests", "3 Guests", "4 Guests", "5 Guests", "6 Guests", "7 Guests", "8 Guests", "9 Guests", "10 Guests"}));
		comboBox.setSelectedIndex(0);
		
		txtCity = new JTextField();
		txtCity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtCity.setText("");
			}
		});
		
		JButton book = new JButton("Book!");
		book.setHorizontalAlignment(SwingConstants.LEFT);
		book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					userLogin = new UserLogin();
					userLogin.setVisible(true);
			}
		});
		txtCity.setText("City");
		txtCity.setColumns(10);
		

		sortBox.setModel(new DefaultComboBoxModel(new String[] {"Rating", "Price"}));
		sortBox.setSelectedIndex(0);
		sortBox.setMaximumRowCount(10);
		sortBox.setBackground(Color.WHITE);
		
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		amnTxt.setToolTipText("");
		amnTxt.setHorizontalAlignment(SwingConstants.CENTER);
		amnTxt.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		checkBoxes[3].setHorizontalAlignment(SwingConstants.LEFT);
		

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 774, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBox_3, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
										.addComponent(checkBox_2)
										.addComponent(intBox, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
										.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(book)
											.addComponent(checkBox))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(amnTxt, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)))
							.addGap(28)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 663, GroupLayout.PREFERRED_SIZE)))
					.addGap(10))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(124)
					.addComponent(txtCity, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(txtCheckInDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(txtCheckOutDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(sortBox, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(search)
					.addGap(46))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addGap(25)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(txtCity, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(txtCheckInDate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(txtCheckOutDate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(search, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblNewLabel_1)
								.addGap(1)
								.addComponent(sortBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(amnTxt, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(checkBox_3)
							.addComponent(checkBox_2)
							.addGap(3)
							.addComponent(intBox)
							.addComponent(checkBox_1)
							.addGap(3)
							.addComponent(checkBox)
							.addGap(42)
							.addComponent(book))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public static String getSelectedId(){
			return listId[localTable.getSelectedRow()];
	}
	
	public static Date getCIn(){
		return 	Date.valueOf(txtCheckInDate.getText());
	}
	
	public static Date getCOut(){
		return 	Date.valueOf(txtCheckOutDate.getText());
	}
	
}
