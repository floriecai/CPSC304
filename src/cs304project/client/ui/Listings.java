package cs304project.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
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
	private JTextField txtCheckInDate;
	private JTextField txtCheckOutDate;
	private JTextField txtCity;
	private static Connection conn;
	private static Statement stmt;
	private JTable table;
	private String c[] = {"Host", "Capacity", "Rating", "Address", "Price"};
	String data[][];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Listings frame = new Listings(null, null);
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				/*String city = Index.getSearch();
				int rowCount = 0;
				conn = Connecting.getConnection();
				String query = "select * from listingPostedIsIn l, AmenitiesIncluded list where l.listingId in" +
						"(select l.listingId from location loc where l.postalCode = loc.postalCode and loc.city like '%" + city + "%')";
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
					 System.out.println(rs.getRow());
					 data[rs.getRow()-1][0] = "John";
					 data[rs.getRow()-1][1] = String.valueOf(rs.getInt("Capacity"));
					 data[rs.getRow()-1][2] = String.valueOf(rs.getDouble("Rating"));
					 data[rs.getRow()-1][3] = rs.getString("Address");
					 data[rs.getRow()-1][4] = String.valueOf(rs.getDouble("Price"));
				 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
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
				int count = 0;
			
				String query = "select loc.city, loc.country, price from location loc, listingPostedIsIn list where list.postalCode = loc.postalCode order by list.rating desc ";
				System.out.println(query);
				try {
				stmt = conn.createStatement();
				//ResultSet rs = stmt.executeQuery(query);
				
				/*while(rs.next()) {
					
					count++;
					}*/
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		
		JComboBox comboBox = new JComboBox();
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
		txtCity.setText("City");
		txtCity.setColumns(10);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Rating", "Price: Low to High", "Price: High to Low"}));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setMaximumRowCount(10);
		comboBox_1.setBackground(Color.WHITE);
		
		JLabel lblNewLabel_1 = new JLabel("Sort By:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		JLabel lblNewLabel_2 = new JLabel("Amenities");
		lblNewLabel_2.setToolTipText("");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JCheckBox kitBox = new JCheckBox("Kitchen");
		kitBox.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox tvBox = new JCheckBox("Television");
		tvBox.setHorizontalAlignment(SwingConstants.LEFT);
		
		JCheckBox intBox = new JCheckBox("Internet");
		
		JCheckBox laudryBox = new JCheckBox("Laundry");
		
		JCheckBox tolBox = new JCheckBox("Toiletries");
		
		JScrollPane scrollPane = new JScrollPane();
		//JScrollPane scrollPane = new JScrollPane(table);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 774, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(tolBox)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(laudryBox, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewLabel_2, 0, 0, Short.MAX_VALUE)
									.addComponent(tvBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(intBox, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
									.addComponent(kitBox, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
								.addGap(18))))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtCity, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtCheckInDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtCheckOutDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
							.addComponent(search))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(13)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(8)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(txtCity, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtCheckInDate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtCheckOutDate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(5)
								.addComponent(search, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(1)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(kitBox)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tvBox)
							.addGap(3)
							.addComponent(intBox)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(laudryBox)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tolBox)
							.addGap(23))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addComponent(scrollPane)
							.addContainerGap())))
		);
		
		table = new JTable(r, c);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(false);
		contentPane.setLayout(gl_contentPane);
	}
}
