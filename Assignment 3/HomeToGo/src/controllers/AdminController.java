package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Administrator;
import Application.Application;
import views.AdminPanelView;
import views.AdminWindow;
import views.LoginWindow;
import views.SearchView;

public class AdminController implements ActionListener{
	
	AdminWindow window;
	
	public AdminController(AdminWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "CONTROL":
			Object o = Application.getInstance().searchLoggedIn();
			if(!(o instanceof Administrator)) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			/*TODO ControlPanelView*/
			AdminPanelView controlPanel = new AdminPanelView();
			AdminPanelController panelCont = new AdminPanelController(controlPanel);
			controlPanel.setControler(panelCont);
			Application.getWindow().setSecondaryView(controlPanel);
			
			break;
		case "LOGOUT":
			Application.getInstance().logout();
			Application.getWindow().setVisible(false);
			Application.getWindow().delete();
			
			LoginWindow login = new LoginWindow();
			LoginController cont = new LoginController(login);
			login.setController(cont);
			
			SearchView s = new SearchView(false);
			SearchController controller = new SearchController(s);
			s.setController(controller);
			login.setSecondaryView(s);
			
			login.setVisible(true);
			
		}
		
	}

}
