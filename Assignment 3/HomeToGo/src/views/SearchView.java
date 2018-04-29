package views;

import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import components.HintTextField;

public class SearchView {

	private JFrame frame;
	private JPanel panel;
	private HintTextField searchTextField;
	private JButton searchButton;
	private JRadioButton zipRadioButton;
	
	private int searchMode;
	
	public SearchView(JFrame frame, boolean loggedUser) {
		this.frame = frame;
		this.searchMode = 0;
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
	}
	
	public String getSearchtext() {
		return searchTextField.getText();
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public int getSearchMode() {
		return searchMode;
	}
	
	public void setSearchMode(int mode) {
		this.searchMode = mode;
		/*TODO*/
		
	}
}


