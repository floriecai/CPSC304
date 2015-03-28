package cs304project.client.ui;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.sql.Connection;

import javax.swing.JScrollPane;

import cs304project.Admin_Queries;
import cs304project.UserQueries;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.SystemColor;

import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class UserBoard extends JFrame {

	private JPanel contentPane;
	private UserQueries uq;
	private static Connection conn;
	private static JTable localTable; 
	private DefaultTableModel searchTableModel;
	private JTextField addressField;
	private JTextField country;
	private JTextField postalC;
	private JTextField cap;
	private JTextField price;
	private Admin_Queries aq;
	private JTextField cityField;
	private JTextField pField;
	private JTextField cField;

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
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		uq = new UserQueries();
		JPanel sidePanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		JPanel insertPanel = new JPanel();
		JPanel updatePanel = new JPanel();
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("132px:grow"),
				ColumnSpec.decode("29px"),
				ColumnSpec.decode("138px"),
				ColumnSpec.decode("73px"),
				ColumnSpec.decode("164px"),
				ColumnSpec.decode("62px"),
				ColumnSpec.decode("140px"),},
				new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("14px:grow"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("23px:grow"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("306px:grow"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));

		JLabel lblWelcome = new JLabel("Welcome,");
		contentPane.add(lblWelcome, "6, 2, 2, 1, right, top");

		JLabel userName = new JLabel(name);
		contentPane.add(userName, "8, 2, center, top");

		JLabel lblNewLabel_1 = new JLabel("Total amount spent");
		contentPane.add(lblNewLabel_1, "6, 3, 2, 1, right, top");
		System.out.println(email);
		JLabel amount = new JLabel(String.valueOf(uq.amount(email)));
		contentPane.add(amount, "8, 3, center, top");
		insertPanel.setVisible(false);
		sidePanel.setVisible(false);
		updatePanel.setVisible(false);
		JButton btnNewButton_1 = new JButton("Past Transactions");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sidePanel.setVisible(false);
				insertPanel.setVisible(false);
				updatePanel.setVisible(false);
				uq = new UserQueries();
				String c[] = {"Transaction", "Price", "Time"};
				String[][] userList;
				userList = uq.usersTransactions(email);
				searchTableModel = new DefaultTableModel (userList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});

		JButton btnNewButton = new JButton("Your listings");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sidePanel.setVisible(true);
				insertPanel.setVisible(false);
				updatePanel.setVisible(false);
				uq = new UserQueries();
				String c[] = {"Listing ID", "Price", "Capacity", "Rating", "Address", "Postal Code"};
				String[][] userList;
				userList = uq.userListings(email);
				searchTableModel = new DefaultTableModel (userList,c);
				localTable = new JTable(searchTableModel);
				scrollPane.setViewportView(localTable);
			}
		});

		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		contentPane.add(btnLogout, "8, 7");


		contentPane.add(updatePanel, "4, 9, 5, 1, fill, fill");
		updatePanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblNewLabel = new JLabel("ListingId");
		updatePanel.add(lblNewLabel, "4, 2, right, default");

		JComboBox comboBox = new JComboBox();

		updatePanel.add(comboBox, "6, 2, left, default");

		JLabel lblNewLabel_2 = new JLabel("Price");
		updatePanel.add(lblNewLabel_2, "4, 4, right, default");

		pField = new JTextField();
		updatePanel.add(pField, "6, 4, left, default");
		pField.setColumns(10);

		JLabel lblCapacity_1 = new JLabel("Capacity");
		updatePanel.add(lblCapacity_1, "4, 6, right, default");

		cField = new JTextField();
		cField.setColumns(10);
		updatePanel.add(cField, "6, 6, left, default");

		JCheckBox chckbxPrivate_1 = new JCheckBox("Private");
		updatePanel.add(chckbxPrivate_1, "4, 10");

		JCheckBox chckbxTv2 = new JCheckBox("TV");
		updatePanel.add(chckbxTv2, "6, 10");

		JCheckBox chckbxInternet_1 = new JCheckBox("Internet");
		updatePanel.add(chckbxInternet_1, "4, 12");

		JCheckBox chckbxLaundry_1 = new JCheckBox("Laundry");
		updatePanel.add(chckbxLaundry_1, "6, 12");

		JCheckBox chckbxKitchen_1 = new JCheckBox("Kitchen");
		updatePanel.add(chckbxKitchen_1, "4, 14");

		JCheckBox chckbxToiletries_1 = new JCheckBox("Toiletries");
		updatePanel.add(chckbxToiletries_1, "6, 14");

		contentPane.add(insertPanel, "4, 9,5,1 fill, fill");
		insertPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(49dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(131dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(146dlu;default)"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblAddress = new JLabel("Address");
		insertPanel.add(lblAddress, "4, 4, 3, 1, center, default");

		addressField = new JTextField();
		insertPanel.add(addressField, "8, 4, 8, 1, fill, default");
		addressField.setColumns(10);

		JLabel lblCountry = new JLabel("Country");
		insertPanel.add(lblCountry, "4, 6, 3, 1, center, default");

		country = new JTextField();
		insertPanel.add(country, "8, 6, 5, 1, fill, default");
		country.setColumns(10);

		JLabel lblPostalCode = new JLabel("Postal Code");
		insertPanel.add(lblPostalCode, "14, 6, right, default");

		postalC = new JTextField();
		insertPanel.add(postalC, "16, 6, left, default");
		postalC.setColumns(10);

		JLabel lblCapacity = new JLabel("Capacity");
		insertPanel.add(lblCapacity, "4, 8, 3, 1, center, default");

		cap = new JTextField();
		insertPanel.add(cap, "8, 8, 4, 1, fill, default");
		cap.setColumns(10);

		JLabel lblPrice = new JLabel("Price");
		insertPanel.add(lblPrice, "14, 8, right, default");

		price = new JTextField();
		price.setColumns(10);
		insertPanel.add(price, "16, 8, left, default");

		JLabel lblCity = new JLabel("City");
		insertPanel.add(lblCity, "14, 10, right, default");

		cityField = new JTextField();
		insertPanel.add(cityField, "16, 10, left, top");
		cityField.setColumns(10);

		JCheckBox chckbxPrivate = new JCheckBox("Private");
		insertPanel.add(chckbxPrivate, "4, 12, left, default");

		JCheckBox chckbxTv = new JCheckBox("TV");
		insertPanel.add(chckbxTv, "10, 12");

		JCheckBox chckbxInternet = new JCheckBox("Internet");
		insertPanel.add(chckbxInternet, "4, 14");

		JCheckBox chckbxKitchen = new JCheckBox("Kitchen");
		insertPanel.add(chckbxKitchen, "10, 14");

		JCheckBox chckbxLaundry = new JCheckBox("Laundry");
		insertPanel.add(chckbxLaundry, "4, 16");

		JCheckBox chckbxToiletries = new JCheckBox("Toiletries");
		insertPanel.add(chckbxToiletries, "10, 16");

		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = addressField.getText();

				String postalCode = postalC.getText();
				String city = cityField.getText();
				String cou = country.getText();
				String internet = "F";
				String is_private = "F";
				String toiletries = "F";
				String tv = "F";
				String kitchen = "F";
				String laundry = "F";
				String governmentId = uq.findUsersGovId(email);
				if(chckbxPrivate.isSelected())
					is_private = "T";
				if(chckbxToiletries.isSelected())
					toiletries  = "T";			
				if(chckbxTv.isSelected())
					tv = "T";		
				if(chckbxKitchen.isSelected())
					kitchen = "T";
				if(chckbxInternet.isSelected())
					internet = "T";
				if(chckbxLaundry.isSelected())
					laundry = "T";
				uq = new UserQueries();
				boolean newLoc = uq.newLoc(postalCode, city, cou);
				if(newLoc){

					if (cap.getText() == "") {
						JOptionPane.showMessageDialog(null, "Please enter maximum capacity of your listing");
						return;
					}			
					else if (price.getText() == "") {
						JOptionPane.showMessageDialog(null, "Please enter a price");
					}

					int capacity = Integer.parseInt(cap.getText());
					double priceL = Double.parseDouble(price.getText());

					boolean newList=  uq.newList(priceL, capacity, is_private, governmentId, uq.getPC(), address, tv, kitchen, toiletries, internet, laundry);
					if(newList)
						JOptionPane.showMessageDialog(null, "Listing inserted");
					else {
						if (priceL <= 0) {
							JOptionPane.showMessageDialog(null, "Failed to insert your listing. Price must be greater than 0");
							return;
						}
						else 
							JOptionPane.showMessageDialog(null, "Could not add listing. Please contact admin");
					}
				} else
					JOptionPane.showMessageDialog(null, "Failed to insert your listing. Incorrect address. Postal Code must be of form XXXXXX (No spaces)");
			}
		});
		insertPanel.add(btnInsert, "16, 22, center, default");
		contentPane.add(btnNewButton, "4, 7, left, top");
		contentPane.add(btnNewButton_1, "6, 7, left, top");


		contentPane.add(sidePanel, "2, 9, fill, fill");

		JButton btnInsertListing = new JButton("Insert Listing");
		btnInsertListing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertPanel.setVisible(true);
				updatePanel.setVisible(false);
			}
		});
		sidePanel.add(btnInsertListing);

		JButton btnDeleteListing = new JButton("Delete Listing");
		btnDeleteListing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertPanel.setVisible(false);
				updatePanel.setVisible(false);
				uq = new UserQueries();
				int listingId = Integer.parseInt(JOptionPane.showInputDialog("Insert the lisitingId of the listing that you want to delete"));
				boolean del = uq.delList(listingId);
				if(del)
					JOptionPane.showMessageDialog(null, "Listing deleted");
				else
					JOptionPane.showMessageDialog(null, "Failed to delete your Listing");
			}
		});
		sidePanel.add(btnDeleteListing);

		JButton btnNewButton_2 = new JButton("Update Listing");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertPanel.setVisible(false);
				updatePanel.setVisible(true);
				uq = new UserQueries();
				uq.userListings(email);
				comboBox.setModel(new DefaultComboBoxModel(uq.getListingId()));
			}

		});

		sidePanel.add(btnNewButton_2);
		contentPane.add(scrollPane, "4, 9, 5, 1, fill, fill");
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String internet = "F";
				String is_private = "F";
				String toiletries = "F";
				String tv = "F";
				String kitchen = "F";
				String laundry = "F";
				int capacity = Integer.parseInt(cField.getText());
				double p = Double.parseDouble(pField.getText());
				if(chckbxPrivate.isSelected())
					is_private = "T";
				if(chckbxToiletries.isSelected())
					toiletries  = "T";			
				if(chckbxTv.isSelected())
					tv = "T";		
				if(chckbxKitchen.isSelected())
					kitchen = "T";
				if(chckbxInternet.isSelected())
					internet = "T";
				if(chckbxLaundry.isSelected())
					laundry = "T";
				uq = new UserQueries();
				int listingId = Integer.parseInt(comboBox.getSelectedItem().toString());
				boolean up = uq.upList(p, capacity, is_private, listingId, tv,  kitchen,  toiletries,  internet,  laundry);
				if(up)
					JOptionPane.showMessageDialog(null, "Listing updated");
				else
					JOptionPane.showMessageDialog(null, "Failed to update your Listing");
				updatePanel.setVisible(false);
			}
		});
		updatePanel.add(btnUpdate, "6, 16, 3, 1, center, default");
	}
}
