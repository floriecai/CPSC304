package cs304project.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import cs304project.Admin_Queries;
import cs304project.Listing;

import javax.swing.JScrollPane;

public class AdminBoard extends JFrame {

	private JPanel contentPane;
	private String aName;
	private Index index;
	private static AdminBoard frame;
	private static Admin_Queries admin; 
	private static JTable localTable;
	private static Listing listing; 
	DefaultTableModel searchTableModel;
	private static Connection conn;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AdminBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param aName 
	 */
	public AdminBoard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		aName = Admin.getAdminName();
		JLabel lblNewLabel = new JLabel("Welcome " + aName);
		JButton btnViewUsers = new JButton("Users");
		JScrollPane scrollPane = new JScrollPane();
		
		btnViewUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				admin = new Admin_Queries();
				String c[] = {"User Name", "User Email"};
				String[][] userList;
				userList = admin.findUsers();
				searchTableModel = new DefaultTableModel (userList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});
		
		JButton btnNewButton = new JButton("Transactions");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				admin = new Admin_Queries();
				String c[] = {"Transaction ID", "Price", "City", "Time", "Address"};
				listing = new Listing(conn);
				String[][] transactionList;
				transactionList = admin.findTransactions();
				searchTableModel = new DefaultTableModel (transactionList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				index = new Index();
				index.setVisible(true);
			}
		});
		
		JButton btnListings = new JButton("Listings");
		btnListings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				admin = new Admin_Queries();
				String c[] = {"Postal Code", "Price", "City", "Rating"};
				listing = new Listing(conn);
				String[][] listingList;
				listingList = admin.findListings();
				searchTableModel = new DefaultTableModel (listingList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});
		

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(101)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(btnViewUsers)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnNewButton)
									.addGap(193)
									.addComponent(btnListings))
								.addComponent(btnLogOut)))
						.addComponent(lblNewLabel))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addGap(15)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnViewUsers)
						.addComponent(btnListings)
						.addComponent(btnNewButton))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnLogOut))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
