package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Application;
import Offer.Offer;
import User.RegisteredUser;
import views.HostWindow;
import views.LoginWindow;
import views.SearchResultsView;
import views.SearchView;

public class HostController implements ActionListener{
	
	HostWindow window;
	
	public HostController(HostWindow window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "CREATED":
			Object o = Application.getInstance().searchLoggedIn();
			if(!(o instanceof RegisteredUser)) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.");
				return;
			}
			
			List<Offer> userOffers = ((RegisteredUser)o).seeOffers();
			if(userOffers.size() == 0) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"You have not created any offers yet.");
				return;
			}
			
			/*TODO HAY QUE CAMBIARLO PARA QUE NO PUEDA COMPRAR ETC*/
			SearchResultsView v = new SearchResultsView(userOffers, true);
			SearchResultsController c = new SearchResultsController(v);
			v.setController(c);
			Application.getWindow().setSecondaryView(v);
			
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
