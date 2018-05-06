package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import components.HintTextField;

public class SearchView extends JPanel{

	private static final long serialVersionUID = -7097337999510494878L;
	
	private boolean logged;
	
	private HintTextField searchTextField;
	private JButton searchButton;
	private JRadioButton zipRadioButton;
	private JRadioButton offerRadioButton;
	private JRadioButton dateRadioButton;
	private JRadioButton ratingRadioButton;
	private JRadioButton reservedRadioButton;
	private JRadioButton bookedRadioButton;
	private ButtonGroup group;
	
	public SearchView(boolean loggedUser) {
		super();
		
		this.logged = loggedUser;
		
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Search"));
		GridBagConstraints c = new GridBagConstraints();
		
		searchTextField = new HintTextField("Insert ZIP code", 12);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(searchTextField, c);
		
		searchButton = new JButton("Search");
		searchButton.setActionCommand("SEARCH");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 2;
		c.gridwidth = 1;
		add(searchButton, c);
		
		zipRadioButton = new JRadioButton("ZIP Code");
		zipRadioButton.setActionCommand("ZIP_SEARCH");
		zipRadioButton.setSelected(true);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 1;
		add(zipRadioButton, c);
		
		offerRadioButton = new JRadioButton("Offer type");
		offerRadioButton.setActionCommand("OFFER_SEARCH");
		c.gridx = 1;
		add(offerRadioButton, c);
		
		dateRadioButton = new JRadioButton("Date");
		dateRadioButton.setActionCommand("DATE_SEARCH");
		c.gridx = 2;
		add(dateRadioButton, c);
		
		ratingRadioButton = new JRadioButton("Rating");
		ratingRadioButton.setActionCommand("RATING_SEARCH");
		c.gridx = 0;
		c.gridy = 2;
		add(ratingRadioButton, c);
		
		reservedRadioButton = new JRadioButton("Reserved");
		reservedRadioButton.setActionCommand("RESERVED_SEARCH");
		c.gridx = 1;
		add(reservedRadioButton, c);
		
		bookedRadioButton = new JRadioButton("Booked");
		bookedRadioButton.setActionCommand("BOOKED_SEARCH");
		c.gridx = 2;
		add(bookedRadioButton, c);
		
		//Group the radio buttons.
		group = new ButtonGroup();
		group.add(zipRadioButton);
		group.add(offerRadioButton);
		group.add(dateRadioButton);
		group.add(ratingRadioButton);
		group.add(reservedRadioButton);
		group.add(bookedRadioButton);
		
		if(!loggedUser) {
			ratingRadioButton.setEnabled(false);
			reservedRadioButton.setEnabled(false);
			bookedRadioButton.setEnabled(false);
		}
	}

	public String getSearchText() {
		return searchTextField.getText();
	}
	
	public void setTextHint(String text) {
		searchTextField.setHint(text);
	}
	
	public void enableTextField(Boolean b) {
		searchTextField.setEnabled(b);
	}
	
	public String getSelected() {
		return group.getSelection().getActionCommand();
	}
	
	public boolean getLogged() {
		return this.logged;
	}

	public void setController(ActionListener e) {
		searchButton.addActionListener(e);
		zipRadioButton.addActionListener(e);
		offerRadioButton.addActionListener(e);
		dateRadioButton.addActionListener(e);
		ratingRadioButton.addActionListener(e);
		reservedRadioButton.addActionListener(e);
		bookedRadioButton.addActionListener(e);
	}
}
