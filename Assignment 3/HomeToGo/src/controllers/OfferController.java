package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Application;
import Exceptions.NotAvailableOfferException;
import Offer.Offer;
import User.RegisteredUser;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import views.LoginWindow;
import views.OfferView;
import views.SearchView;

public class OfferController implements ActionListener {

	private OfferView offerView;

	public OfferController(OfferView view) {
		this.offerView = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Offer offer = offerView.getOffer();
		Object logged = Application.getInstance().searchLoggedIn();

		switch (arg0.getActionCommand()) {

		case "PAY":
			if (!(logged instanceof RegisteredUser)) {
				errorMessage("An administrator cannot buy offers.");
				break;
			}

			try {
				offer.buyOffer((RegisteredUser) logged, "Buy offer");
			} catch (FailedInternetConnectionException e1) {
				errorMessage("No internet connection available.");
			} catch (NotAvailableOfferException e1) {
				errorMessage("You cannot buy this offer.");
			} catch (InvalidCardNumberException e1) {
				errorMessage("Not valid credit card. You are now unlogged and banned "
						+ "until the administrator changes it.");
				
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
				
			} catch (OrderRejectedException e1) {
				errorMessage("You cannot reserve this offer.");
			}

			break;

		case "RESERVE":
			if (!(logged instanceof RegisteredUser)) {
				errorMessage("An administrator cannot reserve offers.");
				break;
			}

			try {
				offer.reserveOffer((RegisteredUser) logged);
			} catch (NotAvailableOfferException e) {
				errorMessage("You cannot reserve this offer.");
			}
			break;

		case "COMMENT":
			if (!(logged instanceof RegisteredUser)) {
				errorMessage("An administrator cannot comment offers.");
				break;
			}

			String comment = JOptionPane.showInputDialog(offerView,
					"Write your comment. It'll be displayed once you enter this window again.");
			if (comment == null || comment.equals("")) {
				break;
			}

			offer.postComment((RegisteredUser) logged, comment);
			break;

		case "RATE":
			if (!(logged instanceof RegisteredUser)) {
				errorMessage("An administrator cannot rate offers.");
				break;
			}

			String rating = JOptionPane.showInputDialog(offerView,
					"Write your rating as an integer from 0 to 5. It'll be displayed once you enter this window again.");
			if (rating == null || rating.equals("")) {
				break;
			}
			
			if(!isValidRating(rating)) {
				errorMessage("The rating must be an integer between 0 and 5.");
				break;
			}

			break;
		}

	}

	public void errorMessage(String text) {
		JFrame frame = new JFrame("Error");

		JOptionPane.showMessageDialog(frame, text);
	}

	public boolean isValidRating(String rating) {
		try {
			int i = Integer.parseInt(rating);
			return i <= 5 && i >= 0;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
