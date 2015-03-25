package cs304project.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs304project.Connecting;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Font;

public class UserLogin extends JFrame {

	private JPanel contentPane;
	private JTextField userField;
	private JLabel lblUser;
	private Connection conn;
	private AdminBoard adb;
	private String adminId, adminName;
	private static String aName;
	private JTextField userName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogin frame = new UserLogin();
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
	public UserLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 399, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("98px"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("max(93dlu;default):grow"),
				ColumnSpec.decode("126px"),},
			new RowSpec[] {
				RowSpec.decode("69px"),
				RowSpec.decode("14px"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("52px"),
				RowSpec.decode("20px"),
				RowSpec.decode("44px"),
				RowSpec.decode("23px"),}));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(UserLogin.class.getResource("/cs304project/bg_small.png")));
		contentPane.add(lblNewLabel, "1, 1, 7, 1");
		
		JLabel lblAdministratorLogin = new JLabel("User Login");
		lblAdministratorLogin.setFont(new Font("Myriad Hebrew", Font.BOLD, 16));
		contentPane.add(lblAdministratorLogin, "1, 3, 6, 1, center, top");
		
		lblUser = new JLabel("User email");
		contentPane.add(lblUser, "2, 5, left, center");
		
		userField = new JTextField();
		userField.setColumns(10);
		contentPane.add(userField, "4, 5, 3, 1, fill, center");
		
		JLabel lblAdminName = new JLabel("Password");
		contentPane.add(lblAdminName, "2, 6");
		
		/*
		 * Admin login 
		 */
		
		userName = new JTextField();
		userName.setColumns(10);
		contentPane.add(userName, "4, 6, 3, 1, fill, default");
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Statement stmt;
				if(userField.getText() != null && userName.getText() != null){
					adminId = userField.getText().trim();
					adminName = userName.getText().trim();
				}
				try {
					conn = Connecting.getConnection();
					stmt = conn.createStatement();
					conn.createStatement();
					String query = "SELECT * FROM admin WHERE adminId LIKE '%"  + adminId + "%' AND name LIKE '%" + adminName + "%'" ;
					System.out.println(query);
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						String test = rs.getString("adminId");
						if(test.contains(adminId)){
			    		   aName = rs.getString("name");
							adb = new AdminBoard();
							adb.setVisible(true);
			    		   break;
						}
			    	}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		contentPane.add(login, "4, 8, fill, top");
	}
	
	public static String getAdminName(){
		return aName;
	}

}