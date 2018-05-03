package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Application.Application;
import User.RegisteredUser;

public class BothWindow implements WindowInterface{
	private JFrame frame;
	private JPanel login;
	private JButton homeButton;
	private JButton createdButton;
	private JButton historyButton;
	private JButton logoutButton;
	private JLabel nameLabelText;
	private JLabel userImageLabel;
	private JLabel logoLabelText;

	RegisteredUser user;
	
	public BothWindow(RegisteredUser user) {
		frame = new JFrame("User window");
		Container cont = frame.getContentPane();
		cont.setLayout(new BorderLayout());
		
		this.user = user;
		
		/*Create the login layout*/
		login = new JPanel();
		login.setLayout(new GridBagLayout());
		login.setBorder(BorderFactory.createTitledBorder("User"));
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(8, 4, 0, 4);
		
		
		userImageLabel = new JLabel();
		userImageLabel.setIcon(new ImageIcon(
                "userImage.png"));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		login.add(userImageLabel, c);
		
		nameLabelText = new JLabel(user.getName());
		c.gridy = 1;
		c.anchor = GridBagConstraints.NORTH;
		login.add(nameLabelText, c);
		
		
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 4));
		historyButton = new JButton("History");
		historyButton.setActionCommand("HISTORY");
		createdButton = new JButton("Created");
		createdButton.setActionCommand("CREATED");
		logoutButton = new JButton("Logout");
		logoutButton.setActionCommand("LOGOUT");
		
		buttonPanel.add(createdButton);
		buttonPanel.add(historyButton);
		buttonPanel.add(logoutButton);
		
		c.gridy = 2;
		/* We need to make the column larger,
		 * and then to align the button at the 
		 * top.*/
		c.weighty = 1.0;
		c.insets = new Insets(0, 4, 8, 4);
		c.anchor = GridBagConstraints.SOUTH;
		login.add(buttonPanel, c);
		
		login.setAlignmentY(Component.TOP_ALIGNMENT);
		
		
		/*Create the logo*/
		JPanel topPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		homeButton = new JButton("Home");
		homeButton.setActionCommand("HOME");
		logoLabelText = new JLabel("HOME TO GO");
		logoLabelText.setHorizontalAlignment(JLabel.CENTER);
		logoLabelText.setFont(logoLabelText.getFont().deriveFont(42.0f));
		logoLabelText.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		c2.gridx = 0;
		c2.gridy = 0;
		c2.insets = new Insets(0, 44, 0, 0);
		topPanel.add(homeButton, c2);
		c2.insets = new Insets(0, 0, 0, 0);
		c2.gridx = 1;
		c2.weightx = 1.0;
		c2.anchor = GridBagConstraints.NORTH;
		topPanel.add(logoLabelText, c2);
		
		
		cont.add(topPanel, BorderLayout.NORTH);
		cont.add(login, BorderLayout.WEST);
		
		frame.setSize(1500, 844);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Application.setWindow(this);
	}
	
	public void setController(ActionListener c) {
		createdButton.addActionListener(c);
		historyButton.addActionListener(c);
		logoutButton.addActionListener(c);
		homeButton.addActionListener(c);
	}
	
	public void setVisible(boolean v) {
		frame.setVisible(v);
	}
	
	public void delete() {
		this.frame.dispose();
	}
	
	public void setSecondaryView(JPanel view) {
		BorderLayout layout = (BorderLayout) frame.getContentPane().getLayout();
		JPanel current = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
		if(current != null) {
			frame.getContentPane().remove(current);
		}
		
		frame.getContentPane().add(view, BorderLayout.CENTER);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}
	
	public JPanel getSecondaryView() {
		BorderLayout layout = (BorderLayout) frame.getContentPane().getLayout();
		return (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
	}
}
