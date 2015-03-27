package cs304project.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import cs304project.Connecting;
import cs304project.Listing;
import cs304project.Reservation;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MakeReservation extends JFrame {

	private JPanel contentPane;
	private JTextField ckIn;
	private JTextField ckOut;
	private String[] guestNum;
	private JTextField cNum;
	private JTextField cName;
	private JTextField cDate;
	private static Connection conn;
	private Listings list;
	private boolean cc;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	private static String email; 
	private double totalPrice; 



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeReservation frame = new MakeReservation(email);
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
	public MakeReservation(String given) {
		JPanel cardInfo = new JPanel();
		cardInfo.setVisible(false);
		setResizable(false);
		JLabel total = null;
		JLabel hostName = null;
		JLabel city = null;
		JLabel address = null;
		JLabel price = null;

		/*
		 * Query!
		 * Check the selected place by its listingId and returns all the info related with it (all listing, location, host)
		 */
		DecimalFormat df = new DecimalFormat("##.##");
		String query = "select distinct * from ListingPostedIsIn l, Host h, RegisteredUser r, Location loc where l.governmentId = h.governmentId "
				+ "and l.postalCode = loc.postalCode and h.email = r.email and l.listingId = " + list.getSelectedId();
		System.out.println(query);
		try {
			conn = Connecting.getConnection();
			PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			long days = (Listings.getCOut().getTime() - Listings.getCIn().getTime())/(1000*60*60*24) ;
			if(rs.next()){
				hostName = new JLabel(rs.getString("name"));
				city = new JLabel(rs.getString("city"));
				address = new JLabel(rs.getString("address"));
				totalPrice = days * rs.getDouble("price");
				total = new JLabel (df.format(totalPrice));
				price = new JLabel(String.valueOf(rs.getDouble("price")));
				guestNum = new String[rs.getInt("capacity")];
				for(int i = 0; i < guestNum.length; i++){
					guestNum[i] = i+1 + " Guests";
				}
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("25px"),
				ColumnSpec.decode("95px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("102px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("84px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("81px:grow"),
				ColumnSpec.decode("max(59dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("46px"),
				ColumnSpec.decode("41px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(73dlu;default)"),
				ColumnSpec.decode("85px"),},
				new RowSpec[] {
				RowSpec.decode("104px"),
				RowSpec.decode("31px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(14dlu;default)"),
				RowSpec.decode("19px"),
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				RowSpec.decode("21px"),
				RowSpec.decode("max(19dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(21dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("33px"),}));

		JRadioButton ccRb = new JRadioButton("Credit Card");
		ccRb.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseClicked(MouseEvent arg0) {
				cardInfo.setVisible(true);
				cc = true;
			}
		});
		JRadioButton ppRb = new JRadioButton("Paypal");
		ppRb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardInfo.setVisible(false);
				cc = false;
			}
		});

		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(MakeReservation.class.getResource("/cs304project/bg.png")));
		contentPane.add(lblNewLabel_2, "1, 1, 15, 1");

		JLabel lblConfirmYourRequest = new JLabel("Request Confirmation");
		lblConfirmYourRequest.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblConfirmYourRequest, "1, 2, 15, 1, center, default");

		JLabel ckInDate = new JLabel("Check-In Date");
		ckInDate.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(ckInDate, "2, 5, fill, center");

		ckIn = new JTextField(Listings.getCIn().toString());
		ckIn.setColumns(10);
		contentPane.add(ckIn, "4, 5, fill, top");

		JLabel lblNewLabel = new JLabel("Check-out Date");
		contentPane.add(lblNewLabel, "6, 5, center, center");

		ckOut = new JTextField(Listings.getCOut().toString());
		ckOut.setColumns(10);
		contentPane.add(ckOut, "8, 5, left, top");

		JLabel lblListingInformation = new JLabel("Listing information");
		lblListingInformation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblListingInformation.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblListingInformation, "11, 5, 4, 1");

		JLabel lblGuests = new JLabel("Guests");
		contentPane.add(lblGuests, "2, 7, center, default");

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(guestNum));
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(10);
		comboBox.setBackground(Color.WHITE);
		contentPane.add(comboBox, "4, 7, fill, default");

		JLabel lblHost = new JLabel("Host");
		lblHost.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblHost, "11, 7, 2, 1");

		contentPane.add(hostName, "14, 7, 2, 1");

		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCity, "11, 8, 2, 1");

		contentPane.add(city, "14, 8, 2, 1");

		JLabel lblPaymentMethod = new JLabel("Payment Method");
		contentPane.add(lblPaymentMethod, "2, 9");
		lblPaymentMethod.setHorizontalAlignment(SwingConstants.CENTER);

		contentPane.add(ccRb, "4, 9");

		buttonGroup.add(ccRb);


		contentPane.add(ppRb, "6, 9");
		buttonGroup.add(ppRb);

		contentPane.add(cardInfo, "2, 10, 8, 10, fill, top");
		cardInfo.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("81px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("15px"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("23px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("35px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("17px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("81px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("112px"),},
				new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("9px"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblCardCompany = new JLabel("Card Company");
		cardInfo.add(lblCardCompany, "2, 4, 4, 1, center, center");
		lblCardCompany.setHorizontalAlignment(SwingConstants.CENTER);

		JRadioButton rdbtnMastercard = new JRadioButton("MasterCard");
		cardInfo.add(rdbtnMastercard, "6, 4, 4, 1, left, top");
		buttonGroup_1.add(rdbtnMastercard);

		JRadioButton rdbtnVisa = new JRadioButton("Visa");
		cardInfo.add(rdbtnVisa, "14, 4, left, top");
		buttonGroup_1.add(rdbtnVisa);

		JLabel lblNewLabel_3 = new JLabel("Card Number");
		cardInfo.add(lblNewLabel_3, "2, 6, 4, 1, center, default");

		cNum = new JTextField();
		cardInfo.add(cNum, "6, 6, 9, 1, fill, top");
		cNum.setColumns(10);

		JLabel lblCardHolderName = new JLabel("Card Holder Name");
		cardInfo.add(lblCardHolderName, "2, 8, 4, 1, center, default");
		lblCardHolderName.setHorizontalAlignment(SwingConstants.RIGHT);

		cName = new JTextField();
		cardInfo.add(cName, "6, 8, 9, 1");
		cName.setColumns(10);

		JLabel lblExpiryDate = new JLabel("Expiry Date");
		cardInfo.add(lblExpiryDate, "2, 10, 4, 1, center, default");
		lblExpiryDate.setHorizontalAlignment(SwingConstants.RIGHT);

		cDate = new JTextField();
		cardInfo.add(cDate, "6, 10, 9, 1");
		cDate.setColumns(10);

		JLabel lblAdress = new JLabel("Adress");
		lblAdress.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAdress, "11, 9, 2, 1");

		contentPane.add(address, "14, 9, 2, 1");

		JLabel lblCostPerDay = new JLabel("Cost per day");
		lblCostPerDay.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCostPerDay, "11, 11, 2, 1");

		contentPane.add(price, "14, 11, 2, 1");

		JLabel lblNewLabel_1 = new JLabel("Total");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1, "11, 13, 2, 1, center, center");

		contentPane.add(total, "14, 13, 2, 1, default, top");

		JButton btnCofirm = new JButton("Confirm");
		this.email = given;
		btnCofirm.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Insert the reservation and transaction to their tables
				 * 
				 * STEP 1: ADD TO TRANSACTION 
				 * STEP 2: ADD TO APPROPRIATE PAYMENT METHOD
				 * STEP 3: ADD TO MAKESRESERVATION
				 * STEP 4: ADD TO TRANSACTIONID&EMAIL TABLE
				 */
				conn = Connecting.getConnection();
				Reservation reservation = new Reservation(conn);
				String s = given; 

				// STEP 1
				int transId = reservation.generateTransaction(totalPrice, list.getSelectedId());

				// Either add to creditcard or paypal tables
				// STEP 2
				if (cc) {
					String company;
					if (rdbtnMastercard.isSelected()) {
						company = "Mastercard";
					} else {
						company = "Visa";
					}

					String date = cDate.getText();
					System.out.println(cDate);
					System.out.println(company);
					System.out.println(cName.getText());

					if (date != null)
						Date.valueOf(date); 
					reservation.creditCardInfo(cNum.getText(), company, cName.getText(), date);
				} else { 
					reservation.paypalTransaction(transId, given); 
				}

				// STEP 3
				String res = reservation.generateReservation(transId, list.getSelectedId(), list.getCIn(), list.getCOut(), comboBox.getSelectedIndex() + 1);

				// STEP 4
				reservation.insertTransIdAndEmail(transId, given);

				if (!res.equals("")) {
					Index index = new Index(); 
					index.setVisible(true); 
					System.out.println(res);

					JOptionPane.showMessageDialog(null, "Your reservation was completed! Thank you!");

				} else {
					JOptionPane.showMessageDialog(null, "Reservation not completed!");
				}

			}
		});
		btnCofirm.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnCofirm, "12, 17, 3, 1, center, center");
	}
}
