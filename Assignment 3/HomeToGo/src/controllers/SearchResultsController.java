package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Application.Administrator;
import Application.Application;
import Offer.Offer;
import User.RegisteredUser;
import User.UserType;
import views.CreateOfferView;
import views.OfferView;
import views.SearchResultsView;

public class SearchResultsController implements ListSelectionListener,
												ActionListener{

	private SearchResultsView view;

	public SearchResultsController(SearchResultsView view) {
		this.view = view;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		/*
		 * This fires twice, when we press the mouse, and when we release it, so we
		 * avoid the first one in order to prevent possible errors.
		 */
		if(e.getValueIsAdjusting()) {
			return;
		}
		
		/*If the user is not logged, he should not be able
		 * to see the complete offer.*/
		if(view.getMode() == SearchResultsView.NOT_LOGGED) {
			return;
		}

		Offer o = view.getList().getSelectedValue();

		//TODO Si el modo es HOST_CREATED y la oferta esta pendiente
		// de cambiarse, ir a la ventana de crear, con los datos puestos.
		
		OfferView v = new OfferView(o, view.getMode());
		OfferController c = new OfferController(v);
		v.setController(c);
		Application.getWindow().setSecondaryView(v);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "CREATE_OFFER":
			Object o = Application.getInstance().searchLoggedIn();
			if(o instanceof Administrator || ((RegisteredUser)o).getType() == UserType.GUEST) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			CreateOfferView createOffer = new CreateOfferView((RegisteredUser)o);
			CreateOfferController controller = new CreateOfferController(createOffer);
			createOffer.setController(controller);
			Application.getWindow().setSecondaryView(createOffer);
			
			break;
		}
		
	}

}
