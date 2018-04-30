package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Application.Application;
import components.HintPasswordField;
import components.HintTextField;

public class LoginWindow implements WindowInterface{
	private JFrame frame;
	private JPanel login;
	private JButton logButton;
	private HintTextField nameTextField;
	private HintPasswordField passTextField;
	private JLabel logoLabelText;
	
	public static int LOGIN_WIDTH = 100;
	public static int LOGIN_HEIGHT = 50;

	
	public LoginWindow() {
		frame = new JFrame("Login window");
		Container cont = frame.getContentPane();
		cont.setLayout(new BorderLayout());
		
		/*Create the login layout*/
		login = new JPanel();
		login.setLayout(new GridBagLayout());
		login.setBorder(BorderFactory.createTitledBorder("Login"));
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(8, 4, 0, 4);
		
		nameTextField = new HintTextField("User name", 12);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		login.add(nameTextField, c);
		
		passTextField = new HintPasswordField("Password", 12);
		c.gridy = 1;
		login.add(passTextField, c);
		
		logButton = new JButton("Login");
		logButton.setActionCommand("LOGIN");
		c.gridy = 2;
		/* We need to make the column larger,
		 * and then to align the button at the 
		 * top.*/
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.NORTH;
		login.add(logButton, c);
		
		login.setAlignmentY(Component.TOP_ALIGNMENT);
		
		
		/*Create the logo*/
		logoLabelText = new JLabel("HOME TO GO");
		logoLabelText.setHorizontalAlignment(JLabel.CENTER);
		logoLabelText.setFont(logoLabelText.getFont().deriveFont(42.0f));
		logoLabelText.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		
		cont.add(logoLabelText, BorderLayout.NORTH);
		cont.add(login, BorderLayout.WEST);
		
		frame.setSize(1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Application.setWindow(this);
	}
	
	public void setController(ActionListener c) {
		logButton.addActionListener(c);
	}
	
	public void setVisible(boolean v) {
		frame.setVisible(v);
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
	
	public String getName() {
		return nameTextField.getText();
	}
	
	public String getPass() {
		return passTextField.getText();
	}
	
	public void clear() {
		passTextField.setText("");
		nameTextField.setText("");
	}
}
