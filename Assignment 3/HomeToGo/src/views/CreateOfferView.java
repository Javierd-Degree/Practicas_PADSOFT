package views;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import Comment.ChangeComment;
import Comment.Comment;
import House.House;
import Offer.HolidayOffer;
import Offer.LivingOffer;
import Offer.Offer;
import User.RegisteredUser;
import components.HintTextField;

public class CreateOfferView extends JPanel{
	
	private static final long serialVersionUID = -572301062333016574L;
	
	private JRadioButton holidayRadioButton;
	private JRadioButton livingRadioButton;
	private ButtonGroup group;
	
	private JComboBox<House> houseComboBox;
	private JLabel newHouseLabel;
	private HintTextField houseIdTextField;
	private HintTextField houseCharTextField;
	private HintTextField houseValueCharTextField;
	private JButton houseAddCharButton;
	private JButton addHouseButton;
	
	private HintTextField startDateTextField;
	private HintTextField depositTextField;
	private HintTextField endTextField;
	private HintTextField extraPriceTextField;
	
	private JButton createButton;
	private JButton cancelButton;
	
	private Offer updatingOffer;
	
	public final static String newHouseString = "- New house -";
	
	public CreateOfferView(RegisteredUser host) {
		super();

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Create offer"));
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 10, 10 ,10);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		add(new JPanel(new FlowLayout()), c);
		
		JPanel typePanel = new JPanel(new FlowLayout());
		typePanel.setBorder(BorderFactory.createTitledBorder("Offer type"));
		holidayRadioButton = new JRadioButton("Holiday offer.");
		holidayRadioButton.setActionCommand("HOLIDAY");
		livingRadioButton = new JRadioButton("Living offer.");
		livingRadioButton.setActionCommand("LIVING");
		livingRadioButton.setSelected(true);
		group = new ButtonGroup();
		group.add(holidayRadioButton);
		group.add(livingRadioButton);
		typePanel.add(holidayRadioButton);
		typePanel.add(livingRadioButton);
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.VERTICAL;
		add(typePanel, c);
		
		JPanel housePanel = new JPanel(new GridLayout(0, 1));
		housePanel.setBorder(BorderFactory.createTitledBorder("House"));
		housePanel.add(new JLabel("Select house:"));
		List<House> houses = new ArrayList<>(host.getCreatedHouses());
		houses.add(new House(newHouseString));
		houseComboBox = new JComboBox<House>(houses.toArray(new House[houses.size()]));
		houseComboBox.setActionCommand("HOUSE_SELECTED");
		housePanel.add(houseComboBox);
		
		
		newHouseLabel = new JLabel("Create new house:");
		housePanel.add(newHouseLabel);
		houseIdTextField = new HintTextField("House id", 12);
		houseCharTextField = new HintTextField("Characteristic", 12);
		houseValueCharTextField = new HintTextField("Value", 12);
		JPanel charPanel = new JPanel(new FlowLayout());
		charPanel.add(houseCharTextField);
		charPanel.add(houseValueCharTextField);
		houseAddCharButton = new JButton("Add characteristic.");
		houseAddCharButton.setActionCommand("ADD_CHARACTERISTIC");
		addHouseButton = new JButton("Create");
		addHouseButton.setActionCommand("CREATE_HOUSE");
		housePanel.add(houseIdTextField);
		housePanel.add(charPanel);
		housePanel.add(houseAddCharButton);
		housePanel.add(addHouseButton);
		
		if(!((House)houseComboBox.getSelectedItem()).getId().equals(newHouseString)) {
			housePanelMode(false);
		}
		
		c.gridx = 2;
		add(housePanel, c);
		
		startDateTextField = new HintTextField("Start date: dd/mm/yyyy", 10);
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(startDateTextField, c);
		
		endTextField = new HintTextField("Number of months", 10);
		c.gridx = 2;
		add(endTextField, c);
		
		extraPriceTextField = new HintTextField("Price per month", 10);
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		add(extraPriceTextField, c);
		
		depositTextField = new HintTextField("Deposit", 10);
		c.gridx = 2;
		c.gridy = 3;
		add(depositTextField, c);
		
		createButton = new JButton("Create");
		createButton.setActionCommand("CREATE_OFFER");
		cancelButton = new JButton("Cancel offer");
		cancelButton.setActionCommand("CANCEL_OFFER");
		cancelButton.setVisible(false);
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(cancelButton);
		buttons.add(createButton);
		
		c.gridx = 3;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.SOUTHEAST;
		add(buttons, c);
	}
	
	
	/*If the offer is already created but it needs some changes, we use this screen.*/
	public CreateOfferView(RegisteredUser user, Offer offer) {
		this(user);

		/*We use the first cell to show the admin comments.*/
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 10, 10 ,10);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.0;
		c.weighty = 0;
		
		JTextArea adminComments = new JTextArea();
		adminComments.setOpaque(false);
		adminComments.setEditable(false);
		String comments =  "";
		for(Comment comment: offer.getComments()) {
			if(comment instanceof ChangeComment) {
				comments += "-" + comment.getComment() + "\n";
			}
		}
		
		adminComments.setText(comments);
		JPanel adminCommentsPanel = new JPanel(new FlowLayout());
		adminCommentsPanel.add(adminComments);
		adminCommentsPanel.setBorder(BorderFactory.createTitledBorder("Administrator comments"));
		if(offer.getStatus() == Offer.TO_CHANGE) {
			add(adminCommentsPanel, c);
		}
		
		
		this.updatingOffer = offer;
		startDateTextField.setText(offer.getStartDate().format(DateTimeFormatter.ofPattern("d/MM/yyyy")));
		depositTextField.setText(""+offer.getDeposit());
		houseComboBox.setSelectedItem(offer.getHouse());
		if(offer instanceof LivingOffer) {
			setOfferType(livingRadioButton.getActionCommand());
			livingRadioButton.setSelected(true);
			livingRadioButton.setEnabled(false);
			holidayRadioButton.setEnabled(false);
			
			endTextField.setText(((LivingOffer) offer).getNumberMonths()+"");
			extraPriceTextField.setText(((LivingOffer) offer).getPricePerMonth()+"");
		}else if(offer instanceof HolidayOffer) {
			setOfferType(holidayRadioButton.getActionCommand());
			holidayRadioButton.setSelected(true);
			livingRadioButton.setEnabled(false);
			holidayRadioButton.setEnabled(false);
			
			endTextField.setText(((HolidayOffer) offer).getEndDate().format(DateTimeFormatter.ofPattern("d/MM/yyyy")));
			extraPriceTextField.setText(((HolidayOffer) offer).getTotalPrice()+"");
		}
		
		createButton.setText("Update");
		createButton.setActionCommand("UPDATE_OFFER");
		cancelButton.setVisible(true);
	}
	
	public void setOfferType(String command) {
		extraPriceTextField.setText("");
		endTextField.setText("");
		if(command.equals(livingRadioButton.getActionCommand())) {
			endTextField.setHint("Number of months");
			extraPriceTextField.setHint("Price per month");
			
		}else if(command.equals(holidayRadioButton.getActionCommand())){
			endTextField.setHint("End date: dd/mm/yyyy");
			extraPriceTextField.setHint("Total price");
		}
	}
	
	public void setController(ActionListener c) {
		houseComboBox.addActionListener(c);
		holidayRadioButton.addActionListener(c);
		livingRadioButton.addActionListener(c);
		addHouseButton.addActionListener(c);
		houseAddCharButton.addActionListener(c);
		createButton.addActionListener(c);
		cancelButton.addActionListener(c);
	}
	
	public void clearCharacteristic() {
		this.houseCharTextField.setText("");
		this.houseValueCharTextField.setText("");
	}
	
	public void clearHouse() {
		clearCharacteristic();
		this.houseIdTextField.setText("");
	}
	
	public void housePanelMode(boolean b) {
		addHouseButton.setEnabled(b);
		houseIdTextField.setEnabled(b);
		if(b) {
			newHouseLabel.setText("Create new house:");
		}else {
			newHouseLabel.setText("Add characteristics to selected house.");
		}
	}
	
	public boolean getHousePanelMode() {
		return addHouseButton.isEnabled();
	}
	
	public Offer getOffer() {
		return this.updatingOffer;
	}
	
	public void addHouseToList(House h) {
		houseComboBox.addItem(h);
		houseComboBox.setSelectedItem(h);
	}
	
	public String getSelected() {
		return group.getSelection().getActionCommand();
	}
	
	public House getHouse() {
		return (House) houseComboBox.getSelectedItem();
	}
	
	public String getHouseId() {
		return this.houseIdTextField.getText();
	}
	
	public String getHouseChar() {
		return this.houseCharTextField.getText();
	}
	
	public String getHouseCharVaue() {
		return this.houseValueCharTextField.getText();
	}
	
	public String getDeposit() {
		return this.depositTextField.getText();
	}
	
	public String getEnd() {
		return this.endTextField.getText();
	}
	
	public String getExtraPrice() {
		return this.extraPriceTextField.getText();
	}
	
	public String getStartDate() {
		return this.startDateTextField.getText();
	}


}
