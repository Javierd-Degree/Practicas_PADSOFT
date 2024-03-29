package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Application;
import Offer.Offer;
import User.RegisteredUser;
import views.BothWindow;
import views.CreateOfferView;
import views.LoginWindow;
import views.SearchResultsView;
import views.SearchView;

public class BothController implements ActionListener{
	
	BothWindow window;
	
	public BothController(BothWindow window) {
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
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			List<Offer> userOffers = ((RegisteredUser)o).seeOffers();
			if(userOffers.size() == 0) {
				JOptionPane.showMessageDialog(new JFrame("Message"),
						"You have not created any offers yet. Try to create your first one.");
				
				CreateOfferView createOffer = new CreateOfferView((RegisteredUser)o);
				CreateOfferController controller = new CreateOfferController(createOffer);
				createOffer.setController(controller);
				Application.getWindow().setSecondaryView(createOffer);
				return;
			}
			
			SearchResultsView vCreated = new SearchResultsView(userOffers, SearchResultsView.HOST_CREATED);
			SearchResultsController cCreated = new SearchResultsController(vCreated);
			vCreated.setController(cCreated);
			Application.getWindow().setSecondaryView(vCreated);
			
			break;
			
		case "HISTORY":
			Object guest = Application.getInstance().searchLoggedIn();
			if(!(guest instanceof RegisteredUser)) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			List<Offer> userOffersH = ((RegisteredUser)guest).seeHistory();
			if(userOffersH.size() == 0) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"You have not bought nor reserved any offer yet.");
				return;
			}
			
			SearchResultsView vHistory = new SearchResultsView(userOffersH, SearchResultsView.GUEST_HISTORY);
			SearchResultsController cHistory = new SearchResultsController(vHistory);
			vHistory.setController(cHistory);
			Application.getWindow().setSecondaryView(vHistory);
			
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
			break;
			
		case "HOME":
			SearchView s2 = new SearchView(true);
			SearchController controller2 = new SearchController(s2);
			s2.setController(controller2);
			Application.getWindow().setSecondaryView(s2);
			
			break;
			
		}
		
	}

}
