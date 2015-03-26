package cs304project.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.sql.Connection;

import javax.swing.JScrollPane;

import cs304project.Listing;
import cs304project.UserQueries;

public class UserBoard extends JFrame {

	private JPanel contentPane;
	private UserQueries uq;
	private static Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserBoard frame = new UserBoard(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserBoard(String email, String name) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		System.out.println(email + name);
		JButton btnLogOut = new JButton("Log out");
		
		JLabel lblNewLabel_1 = new JLabel("Total Amount");
		uq = new UserQueries();
		JLabel amount = new JLabel(String.valueOf(uq.amount(email)));
		
		JLabel lblWelcome = new JLabel("Welcome,");
		
		JLabel userName = new JLabel(name);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(453, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(amount)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblWelcome)
							.addGap(28)
							.addComponent(userName)
							.addContainerGap())))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(67)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(514, Short.MAX_VALUE)
					.addComponent(btnLogOut)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWelcome)
						.addComponent(userName))
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(amount))
					.addGap(44)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnLogOut))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
