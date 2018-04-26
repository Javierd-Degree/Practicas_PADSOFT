package views;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
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
		panel = new JPanel();
		GroupLayout layout = new GroupLayout(frame);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(searchTextField)
						.addComponent(searchButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)));
		
		
		
		
		searchButton = new JButton();
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
		    
		    panel.setLayout(layout);
	}
	
	public String getSearchtext() {
		return searchTextField.getText();
	}
	
	public int getSearchMode() {
		return searchMode;
	}
	
	public void setSearchMode(int mode) {
		this.searchMode = mode;
		/*TODO*/
		
	}
}


