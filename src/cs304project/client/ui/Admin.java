package cs304project.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Statement;

import cs304project.Admin_Queries;
import cs304project.Connecting;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.Font;

public class Admin extends JFrame {

	private JPanel contentPane;
	private JTextField userField;
	private JLabel lblUser;
	private static Connection conn;
	private AdminBoard adb;
	private String adminId, adminPassword;
	private static String aName;
	private JTextField userName;

	private static Admin_Queries admin; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin();
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
	public Admin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 399, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("98px"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("10px"),
				ColumnSpec.decode("max(43dlu;default):grow"),
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
		lblNewLabel.setIcon(new ImageIcon(Admin.class.getResource("/cs304project/bg_small.png")));
		contentPane.add(lblNewLabel, "1, 1, 5, 1");

		JLabel lblAdministratorLogin = new JLabel("Administrator Login");
		lblAdministratorLogin.setFont(new Font("Myriad Hebrew", Font.BOLD, 16));
		contentPane.add(lblAdministratorLogin, "1, 3, 5, 1, center, top");

		lblUser = new JLabel("Admin ID");
		contentPane.add(lblUser, "2, 5, left, center");

		userField = new JTextField();
		userField.setColumns(10);
		contentPane.add(userField, "4, 5, left, center");

		JLabel lblAdminName = new JLabel("Admin Name");
		contentPane.add(lblAdminName, "2, 6");

		userName = new JTextField();
		userName.setColumns(10);
		contentPane.add(userName, "4, 6, fill, default");

		JButton login = new JButton("Login");

		/*
		 * Admin login 
		 */
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(userField.getText() != null && userName.getText() != null){
					adminId = userField.getText().trim();
					adminPassword = userName.getText().trim();
				}
				admin = new Admin_Queries(); 

				aName = admin.findAdminLogin(adminId, adminPassword);
				if (!(aName.equals("404"))) {
					adb = new AdminBoard();
					adb.setVisible(true);
					System.out.println("Successful login");	
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Failed login!");
				}


			}
		});
		contentPane.add(login, "2, 8, 3, 1, fill, top");
	}
	public static String getAdminName(){
		return aName;
	}

}
