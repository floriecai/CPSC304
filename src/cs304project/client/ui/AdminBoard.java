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
import javax.swing.JOptionPane;
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
import javax.swing.JCheckBox;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

public class AdminBoard extends JFrame {

	private JPanel contentPane;
	private String aName;
	private Index index;
	private static AdminBoard frame;
	private static Admin_Queries admin; 
	private static JTable localTable;
	private static Listing listing; 
	DefaultTableModel searchTableModel;
	private int adminId;
	private static Connection conn;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	


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
		setResizable(false);
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
		JPanel panelUser = new JPanel();
		JPanel panelTransactions = new JPanel();
		panelTransactions.setVisible(false);
		panelUser.setVisible(false);
		
		btnViewUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				admin = new Admin_Queries();
				String c[] = {"User Name", "User Email"};
				String[][] userList;
				userList = admin.findUsers();
				searchTableModel = new DefaultTableModel (userList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
				panelUser.setVisible(true);
				panelTransactions.setVisible(false);
			}
		});
		
		JButton btnNewButton = new JButton("Transactions");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelUser.setVisible(false);
				panelTransactions.setVisible(true);
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
				setVisible(false);			}
		});
		
		JButton btnListings = new JButton("Listings");
		btnListings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelUser.setVisible(false);
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
		panelUser.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("61px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("60px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JRadioButton rdbtnVerified = new JRadioButton("Verified");
		rdbtnVerified.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnVerified.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelUser.setVisible(true);
				admin = new Admin_Queries();
				String c[] = {"User Name", "User Email"};
				String[][] userList;
				userList = admin.allVerifiedHosts();
				searchTableModel = new DefaultTableModel (userList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});
		buttonGroup.add(rdbtnVerified);
		panelUser.add(rdbtnVerified, "2, 2, 3, 1");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Inactive");
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelUser.setVisible(true);
				admin = new Admin_Queries();
				String c[] = {"User Name", "User Email"};
				String[][] userList;
				userList = admin.findInactiveUsers();
				searchTableModel = new DefaultTableModel (userList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		panelUser.add(rdbtnNewRadioButton, "2, 4, 3, 1");
		
		JButton btnNewButton_1 = new JButton("Verify an User");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				admin = new Admin_Queries();
				String email = JOptionPane.showInputDialog("Insert the user email");
				String id = admin.findUsersGovId(email);
				System.out.println(id + " " + email);
				if(admin.verifyUser(admin.getAdminId(), id))
					JOptionPane.showMessageDialog(null, "A new user was verified");
				else
					JOptionPane.showMessageDialog(null, "An error occurred and the user was not verified");
			}
		});
		panelUser.add(btnNewButton_1, "2, 6, 3, 1");
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("138px:grow"),
				ColumnSpec.decode("16px"),
				ColumnSpec.decode("90px"),
				ColumnSpec.decode("167px"),
				ColumnSpec.decode("93px"),
				ColumnSpec.decode("159px"),
				ColumnSpec.decode("100px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				RowSpec.decode("22px:grow"),
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("347px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		contentPane.add(lblNewLabel, "8, 2, center, top");
		
	
		contentPane.add(panelTransactions, "2, 6, fill, fill");
		
		JButton btnNewButton_2 = new JButton("Check today");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				admin = new Admin_Queries();
				String c[] = {"Transaction ID", "Price", "City", "Time", "Address"};
				listing = new Listing(conn);
				String[][] transactionList;
				transactionList = admin.findTransactionsToday();
				searchTableModel = new DefaultTableModel (transactionList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});
		panelTransactions.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Group by date");
		panelTransactions.add(btnNewButton_3);
		contentPane.add(btnViewUsers, "4, 4, left, top");
		contentPane.add(btnNewButton, "6, 4, 2, 1, left, top");
		contentPane.add(btnListings, "8, 4, right, top");
		contentPane.add(panelUser, "2, 6, fill, fill");
		contentPane.add(scrollPane, "4, 6, 5, 1, fill, fill");
		contentPane.add(btnLogOut, "8, 8, right, top");
	}
}