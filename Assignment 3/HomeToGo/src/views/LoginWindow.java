package views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
<<<<<<< HEAD
import java.awt.GridLayout;
=======
import java.awt.event.ActionListener;
>>>>>>> 76fd4e0c283f469823d676fc1b9f7a1f2e45e9ba

import javax.swing.BorderFactory;
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
		SearchView search = new SearchView(frame, false);
		/*Create the login layout*/
		login = new JPanel();
		login.setLayout(new GridLayout(3, 1, 0, 3));
		login.setBorder(BorderFactory.createEmptyBorder(10, 10, 540, 10)); 
		
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
<<<<<<< HEAD
		cont.add(search.getPanel(), BorderLayout.CENTER);
=======
		cont.add(s.getView(), BorderLayout.CENTER);
>>>>>>> 76fd4e0c283f469823d676fc1b9f7a1f2e45e9ba
		
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
