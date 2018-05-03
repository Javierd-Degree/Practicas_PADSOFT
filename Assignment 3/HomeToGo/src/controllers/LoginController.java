package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Administrator;
import Application.Application;
import User.RegisteredUser;
import User.UserType;
import views.AdminWindow;
import views.BothWindow;
import views.GuestWindow;
import views.HostWindow;
import views.LoginWindow;
import views.SearchView;

public class LoginController implements ActionListener{
	private LoginWindow window;
	
	public LoginController(LoginWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "LOGIN":
			String name = window.getName().replaceAll(" ", "");
			String pass = window.getPass();

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
			Application.getWindow().setVisible(false);
			Application.getWindow().delete();
			Object logged = Application.getInstance().searchLoggedIn();
			if(logged instanceof Administrator) {
	
				AdminWindow admin = new AdminWindow();
				AdminController cont = new AdminController(admin);
				admin.setController(cont);
				
				SearchView s = new SearchView(false);
				SearchController controller = new SearchController(s);
				s.setController(controller);
				admin.setSecondaryView(s);
				
				admin.setVisible(true);
				return;
			}
			
			RegisteredUser user = (RegisteredUser) logged;
			
			if(user.getType() == UserType.GUEST) {
				GuestWindow guest = new GuestWindow(user);
				GuestController cont = new GuestController(guest);
				guest.setController(cont);
				
				SearchView s = new SearchView(true);
				SearchController controller = new SearchController(s);
				s.setController(controller);
				guest.setSecondaryView(s);
				
				guest.setVisible(true);
			}else if(user.getType() == UserType.HOST){
				HostWindow host = new HostWindow(user);
				HostController cont = new HostController(host);
				host.setController(cont);
				
				SearchView s = new SearchView(false);
				SearchController controller = new SearchController(s);
				s.setController(controller);
				host.setSecondaryView(s);
				
				host.setVisible(true);
			}else if(user.getType() == UserType.BOTH) {
				BothWindow both = new BothWindow(user);
				BothController cont = new BothController(both);
				both.setController(cont);
				
				SearchView s = new SearchView(true);
				SearchController controller = new SearchController(s);
				s.setController(controller);
				both.setSecondaryView(s);
				
				both.setVisible(true);
			}
			
			
			break;
			
		case "HOME":
			SearchView s = new SearchView(false);
			SearchController controller = new SearchController(s);
			s.setController(controller);
			Application.getWindow().setSecondaryView(s);
			
			break;
		}
		
	}

}
