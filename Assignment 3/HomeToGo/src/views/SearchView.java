package views;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import components.HintTextField;

public class SearchView {

	private JPanel panel;
	private HintTextField searchTextField;
	private JButton searchButton;
	private JRadioButton zipRadioButton;

	private int searchMode;

	public SearchView(JFrame frame, boolean loggedUser) {
		this.searchMode = 0;
<<<<<<< HEAD
		this.panel = new JPanel();
		BorderLayout layout = new BorderLayout();
		searchButton = new JButton("Search");
		searchTextField = new HintTextField("Insert ZIP code", 12);
		
		JRadioButton zipRadioButton = new JRadioButton("zip");
		 zipRadioButton.setMnemonic(KeyEvent.VK_B);
		 zipRadioButton.setActionCommand("zip");
		 zipRadioButton.setSelected(true);
		 
		    //Group the radio buttons.
		    ButtonGroup group = new ButtonGroup();
		    group.add(zipRadioButton);

		    //Register a listener for the radio buttons.
		    //zipRadioButton.addActionListener(this);
		    
		this.panel.setLayout(layout);
		this.panel.add(searchTextField, BorderLayout.CENTER);
		this.panel.add(searchButton, BorderLayout.EAST);
		this.panel.add(zipRadioButton, BorderLayout.SOUTH);
		this.panel.setBorder(BorderFactory.createEmptyBorder(200, 150, 385, 150));
=======
		panel = new JPanel();
		/**GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		searchButton = new JButton();
		searchTextField = new HintTextField("Insert ZIP code", 12);

		JRadioButton zipRadioButton = new JRadioButton("zip");
		zipRadioButton.setMnemonic(KeyEvent.VK_B);
		zipRadioButton.setActionCommand("zip");
		zipRadioButton.setSelected(true);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(zipRadioButton);

		// Register a listener for the radio buttons.
		// zipRadioButton.addActionListener(this);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(searchTextField)
						.addComponent(searchButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)));*/
		
		
		 GroupLayout layout = new GroupLayout(panel);
		    panel.setLayout(layout);

		    JLabel one = new JLabel("one");
		    JLabel two = new JLabel("two");
		    JLabel three = new JLabel("three");
		    JLabel four = new JLabel("four");
		    JLabel five = new JLabel("five");
		    JLabel six = new JLabel("six");
		    
		    layout.setHorizontalGroup(layout
		    	    .createParallelGroup(GroupLayout.Alignment.LEADING)
		    	    .addGroup(layout.createSequentialGroup()
		    	        .addComponent(one, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    	        .addComponent(two, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    	        .addComponent(three, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		    	    .addGroup(layout.createSequentialGroup()
		    	        .addComponent(four, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    	        .addComponent(five, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		    	    .addComponent(six, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		    	layout.setVerticalGroup(layout.createSequentialGroup()
		    	    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    	        .addComponent(one).addComponent(two).addComponent(three))
		    	    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    	        .addComponent(four).addComponent(five))
		    	    .addComponent(six));
		
		/**panel.setLayout(new FlowLayout());
		searchButton = new JButton("Search");
		searchTextField = new HintTextField("Insert ZIP code", 12);
		panel.add(searchButton);
		panel.add(searchTextField);*/
	}

	public JPanel getView() {
		return this.panel;
>>>>>>> 76fd4e0c283f469823d676fc1b9f7a1f2e45e9ba
	}

	public String getSearchText() {
		return searchTextField.getText();
	}
<<<<<<< HEAD
	
	public JPanel getPanel() {
		return this.panel;
	}
	
=======

>>>>>>> 76fd4e0c283f469823d676fc1b9f7a1f2e45e9ba
	public int getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(int mode) {
		this.searchMode = mode;
		/* TODO */

	}
}
