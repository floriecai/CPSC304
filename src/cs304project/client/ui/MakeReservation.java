package cs304project.client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;

public class MakeReservation extends JFrame {

	private JPanel contentPane;
	private JTextField email;
	private JTextField ckIn;
	private JTextField ckOut;
	private String[] guestNum;
	private JTextField cNum;
	private JTextField cName;
	private JTextField cDate;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeReservation frame = new MakeReservation();
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
	public MakeReservation() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUserEmail = new JLabel("User email");
		lblUserEmail.setHorizontalAlignment(SwingConstants.CENTER);
		
		ckIn = new JTextField();
		ckIn.setColumns(10);
		
		ckOut = new JTextField();
		ckOut.setColumns(10);
		
		JLabel ckInDate = new JLabel("Check-In Date");
		ckInDate.setHorizontalAlignment(SwingConstants.CENTER);
		

		JLabel lblNewLabel = new JLabel("Check-out Date");
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("25px"),
				ColumnSpec.decode("95px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("80px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("84px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("81px"),
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
				RowSpec.decode("max(19dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(MakeReservation.class.getResource("/cs304project/bg.png")));
		contentPane.add(lblNewLabel_2, "1, 1, 15, 1");
		
		email = new JTextField();
		email.setColumns(10);
		contentPane.add(email, "4, 5, 5, 1, fill, fill");
		
		JLabel lblListingInformation = new JLabel("Listing information");
		lblListingInformation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblListingInformation.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblListingInformation, "11, 5, 4, 1");
		
		JLabel lblHost = new JLabel("Host");
		lblHost.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblHost, "11, 7, 2, 1");
		
		JLabel lblNewLabel_3 = new JLabel("new label");
		contentPane.add(lblNewLabel_3, "14, 7, 2, 1");
		
		JLabel lblGuests = new JLabel("Guests");
		contentPane.add(lblGuests, "2, 8, center, default");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(guestNum));
		comboBox.setSelectedIndex(0);
		comboBox.setMaximumRowCount(10);
		comboBox.setBackground(Color.WHITE);
		contentPane.add(comboBox, "4, 8, fill, default");
		
		JLabel lblCity = new JLabel("City");
		lblCity.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCity, "11, 8, 2, 1");
		
		JLabel label_2 = new JLabel("new label");
		contentPane.add(label_2, "14, 8, 2, 1");
		
		JLabel lblPaymentMethod = new JLabel("Payment Method");
		lblPaymentMethod.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPaymentMethod, "2, 9, 2, 1, center, center");
		
		JRadioButton ccRb = new JRadioButton("Credit Card");
		buttonGroup.add(ccRb);
		contentPane.add(ccRb, "4, 9");
		
		JRadioButton ppRb = new JRadioButton("Paypal");
		buttonGroup.add(ppRb);
		contentPane.add(ppRb, "6, 9");
		
		JLabel lblAdress = new JLabel("Adress");
		lblAdress.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAdress, "11, 9, 2, 1");
		
		JLabel lblNewLabel_4 = new JLabel("New Label");
		contentPane.add(lblNewLabel_4, "14, 9, 2, 1");
		
		JLabel lblCardCompany = new JLabel("Card Company");
		lblCardCompany.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCardCompany, "2, 11");
		
		JRadioButton rdbtnVisa = new JRadioButton("Visa");
		buttonGroup_1.add(rdbtnVisa);
		contentPane.add(rdbtnVisa, "4, 11");
		
		JRadioButton rdbtnMastercard = new JRadioButton("MasterCard");
		buttonGroup_1.add(rdbtnMastercard);
		contentPane.add(rdbtnMastercard, "6, 11");
		contentPane.add(ckInDate, "2, 7, fill, center");
		contentPane.add(ckIn, "4, 7, fill, top");
		contentPane.add(lblNewLabel, "6, 7, center, center");
		contentPane.add(ckOut, "8, 7, left, top");
		contentPane.add(lblUserEmail, "2, 5, center, center");
		
		JLabel lblCostPerDay = new JLabel("Cost per day");
		lblCostPerDay.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCostPerDay, "11, 11, 2, 1");
		
		JLabel label = new JLabel("new label");
		contentPane.add(label, "14, 11, 2, 1");
		
		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblCardNumber.setVisible(false);
		contentPane.add(lblCardNumber, "2, 13, center, default");
		
		
		cNum = new JTextField();
		cNum.setColumns(10);
		contentPane.add(cNum, "4, 13, 4, 1, fill, default");
		
		JLabel lblNewLabel_1 = new JLabel("Total");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1, "11, 13, 2, 1, center, center");
		
		JLabel label_1 = new JLabel("New label");
		contentPane.add(label_1, "14, 13, 2, 1");
		
		JLabel lblCardHolderName = new JLabel("Card Holder Name");
		lblCardHolderName.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblCardHolderName, "2, 15, right, default");
		
		cName = new JTextField();
		cName.setColumns(10);
		contentPane.add(cName, "4, 15, 4, 1, fill, default");
		
		JLabel lblExpiryDate = new JLabel("Expiry Date");
		lblExpiryDate.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblExpiryDate, "2, 17, right, default");
		
		cDate = new JTextField();
		cDate.setColumns(10);
		contentPane.add(cDate, "4, 17, fill, default");
		
		JButton btnCofirm = new JButton("Confirm");
		btnCofirm.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnCofirm, "11, 15, 4, 3, center, center");
		
		if(ccRb.isSelected()){
			lblCardNumber.setVisible(true);
		}

	}
}
