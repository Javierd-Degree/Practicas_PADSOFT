package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Administrator;
import Application.Application;
import Exceptions.NotAvailableOfferException;
import views.AdminOfferCellView;
import views.LoginWindow;
import views.SearchView;

public class AdminOfferCellController implements ActionListener{
	
	private AdminOfferCellView view;
	
	public AdminOfferCellController(AdminOfferCellView view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object logged = Application.getInstance().searchLoggedIn();
		if (!(logged instanceof Administrator)) {
			/*There is a user logged in, so we need to logout.*/
			JOptionPane.showMessageDialog(new JFrame("Error"), 
					"A user cannot unblock users.");
			
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
			return;
		}
		
		switch(e.getActionCommand()) {
		case "APPROVE":
			try {
				view.getOffer().approveOffer();
			} catch (NotAvailableOfferException e1) {
				JOptionPane.showMessageDialog(new JFrame("Error"), 
						"Sorry, this offer cannot be approved.");
			}
			
			break;
			
		case "ASK_CHANGES":
			String comment = JOptionPane.showInputDialog(view,
					"Write your comment.");
			
			if (comment == null || comment.equals("")) {
				return;
			}
			
			try {
				view.getOffer().askForChanges(comment);
			} catch (NotAvailableOfferException e1) {
				JOptionPane.showMessageDialog(new JFrame("Error"), 
						"Sorry, you cannot ask for changes on this offer.");
			}
			
			break;
			
		case "DENY":
			try {
				view.getOffer().denyOffer();
			} catch (NotAvailableOfferException e1) {
				JOptionPane.showMessageDialog(new JFrame("Error"), 
						"Sorry, this offer cannot be denied.");;
			}
			break;
		
		}
		
		/*Once the offer is denied, approved, or asked for changes, we
		 * can remove it from the list.*/
		view.getParent().remove(view);
		view.getParent().validate();
		view.getParent().repaint();
		
	}


}
