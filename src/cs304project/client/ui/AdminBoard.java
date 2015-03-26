package cs304project.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import cs304project.Admin_Queries;

public class AdminBoard extends JFrame {

	private JPanel contentPane;
	private String aName;
	private Index index;
	private static AdminBoard frame;
	private static Admin_Queries admin; 


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
		setBounds(100, 100, 450, 198);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		aName = Admin.getAdminName();
		JLabel lblNewLabel = new JLabel("Welcome " + aName);
		JButton btnViewUsers = new JButton("Users");
		btnViewUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				admin = new Admin_Queries();
				
				List<String> userList = new ArrayList<String>();
				userList = admin.findUsers();
				for (String s : userList) {
					System.out.println(s);
				}
			}
		});
		
		JButton btnNewButton = new JButton("Transactions");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			admin = new Admin_Queries(); 
			
			List<String> transactionList = new ArrayList<String>(); 
			transactionList = admin.findTransactions(); 
			for (String s: transactionList)
				System.out.println(s);
			
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
				
				List<String> listingList = new ArrayList<String>();
				listingList = admin.findListings();
				for (String s : listingList) {
					System.out.println(s);
				}
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addComponent(btnViewUsers)
							.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
							.addComponent(btnNewButton)
							.addGap(76)
							.addComponent(btnListings))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(357, Short.MAX_VALUE)
							.addComponent(lblNewLabel))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(345, Short.MAX_VALUE)
							.addComponent(btnLogOut)))
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
					.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
					.addComponent(btnLogOut)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

}
