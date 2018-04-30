package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Application;
import views.LoginWindow;

public class LoginController implements ActionListener{
	private LoginWindow window;
	
	public LoginController(LoginWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "LOGIN":
			String name = window.getName();
			String pass = window.getPass();
			System.out.println("Vamos a loggerar a " + name + ", "+ pass);
			int result = Application.getInstance().login(name, pass);
			
			if(result == Application.NOT_FOUND_ID) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"This user name does not exists in our database.");
				return;
			}else if(result == Application.NO_MATCHED) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Incorrect password.");
				return;
			}else if(result == Application.BANNED_USER) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Sorry, you are currently banned. "
						+ "Contact the administrator in order to solve the problem.");
				return;
			}
			
			/*CAMBIAR DE PANTALLA Y LOGGEARNOS.*/
			
			break;
		}
		
	}

}
