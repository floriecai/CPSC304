package cs304project.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs304project.Connecting;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.Font;

import javax.swing.SwingConstants;

public class UserRegister extends JFrame {

	private JPanel contentPane;
	private JTextField userField;
	private JLabel lblUser;
	private Connection conn;
	private AdminBoard adb;
	private JPasswordField userPass;
	private String userEmail;
	private String userPassword;
	private String userName;
	private boolean logged;
	private MakeReservation mk;
	private JLabel lblUserName;
	private JTextField name;
	private JLabel lblPassword;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRegister frame = new UserRegister();
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
	public UserRegister() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 302);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("98px"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("max(72dlu;default)"),
				ColumnSpec.decode("70px"),},
			new RowSpec[] {
				RowSpec.decode("69px"),
				RowSpec.decode("14px"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("52px"),
				RowSpec.decode("20px"),
				RowSpec.decode("44px"),
				RowSpec.decode("23px"),}));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UserRegister.class.getResource("/cs304project/bg_small.png")));
		contentPane.add(lblNewLabel, "1, 1, 7, 1");
		
		JLabel lblAdministratorLogin = new JLabel("Register");
		lblAdministratorLogin.setFont(new Font("Myriad Hebrew", Font.BOLD, 16));
		contentPane.add(lblAdministratorLogin, "1, 3, 7, 1, center, top");
		
		lblUser = new JLabel("User email");
		contentPane.add(lblUser, "2, 5, left, center");
		
		userField = new JTextField();
		userField.setColumns(10);
		contentPane.add(userField, "4, 5, 3, 1, fill, center");
		
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Statement stmt;
				register.setEnabled(false);
				if(userField.getText().length() > 0 && userPass.getPassword().toString().length() > 0 && name.getText().length() > 0){
					if (!userField.getText().contains("@")) {
						JOptionPane.showMessageDialog(null, "Please, an email.");
						register.setEnabled(true);
						return;
					}
					userEmail = userField.getText().trim();
					userPassword = userPass.getText();
					userName = name.getText().trim();
					//setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Username or password is empty");
					register.setEnabled(true);
					return;
				}
				try {
					conn = Connecting.getConnection();
					
					/*
					 * Query!!
					 */
					String query = "SELECT email FROM RegisteredUser WHERE email like '%" + userEmail + "%'";
					System.out.println(query);
					PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
	                        ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = ps.executeQuery();
					if(rs.next()){
						logged = false;
						lblUser.setForeground(Color.red);
						JOptionPane.showMessageDialog(null, "Email Taken");

						register.setEnabled(true);
					} else {
						String insertion = "INSERT INTO RegisteredUser VALUES ('"  + userEmail + "', '" + userName +  "', '" + userPassword + "')";
						ps = conn.prepareStatement(insertion, ResultSet.TYPE_SCROLL_SENSITIVE,
		                        ResultSet.CONCUR_UPDATABLE);
						ps.executeUpdate();
						ps.close();
						JOptionPane.showMessageDialog(null, "Thanks for your registration!");
						setVisible(false);
						}
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Registration failed!");
					register.setEnabled(true);
				}
			}
		});
		
		lblUserName = new JLabel("User name");
		contentPane.add(lblUserName, "2, 6, right, default");
		
		name = new JTextField();
		contentPane.add(name, "4, 6, 3, 1, fill, default");
		name.setColumns(10);
		
		lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, "2, 7, right, default");
		
		userPass = new JPasswordField();
		userPass.setColumns(10);
		contentPane.add(userPass, "4, 7, 3, 1, fill, default");
		contentPane.add(register, "4, 8, fill, top");
	}
	
	public String getUserEmail() {
		return userEmail; 
	}
}
