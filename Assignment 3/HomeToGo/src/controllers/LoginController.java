package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.LoginWindow;

public class LoginController implements ActionListener{
	private LoginWindow window;
	
	public LoginController(LoginWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "Login":
			String name = window.getName();
			String pass = window.getPass();
			System.out.println("VAmos a loggerar a " + name + ", "+ pass);
			break;
		}
		
	}

}
