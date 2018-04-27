package views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.HintPasswordField;
import components.HintTextField;

public class LoginWindow {
	private JFrame frame;
	private JPanel login;
	private JButton logButton;
	private HintTextField nameTextField;
	private HintPasswordField passTextField;
	private JLabel logoLabelText;
	
	private SearchView s;
	
	public static int LOGIN_WIDTH = 100;
	public static int LOGIN_HEIGHT = 50;

	
	public LoginWindow() {
		frame = new JFrame("Login window");
		Container cont = frame.getContentPane();
		cont.setLayout(new BorderLayout());
		
		/*Create the login layout*/
		login = new JPanel();
		login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
		
		logButton = new JButton("Login");
		nameTextField = new HintTextField("User name", 12);
		passTextField = new HintPasswordField("Password", 12);
		login.add(nameTextField);
		login.add(passTextField);
		login.add(logButton);
		
		
		/*Create the logo*/
		//logoLabelText = new JLabel("<html><centre><span style='font-size:20px'> HOME TO GO </span></centre></html>");
		logoLabelText = new JLabel("HOME TO GO");
		logoLabelText.setHorizontalAlignment(JLabel.CENTER);
		logoLabelText.setFont(new Font("Comic Sans MS", 50, 50));
		 
		s = new SearchView(frame, false);
		
		cont.add(logoLabelText, BorderLayout.NORTH);
		cont.add(login, BorderLayout.WEST);
		cont.add(s.getView(), BorderLayout.CENTER);
		
		frame.setSize(1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setController(ActionListener c) {
		logButton.addActionListener(c);
	}
	
	public void setVisible(boolean v) {
		frame.setVisible(v);
	}
	
	/**public void setControlador(ActionListener c) {
		_accept.addActionListener(c);
		_cancel.addActionListener(c);
	}*/
	
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
	
	public String getSearchText() {
		return s.getSearchText();
	}
}
