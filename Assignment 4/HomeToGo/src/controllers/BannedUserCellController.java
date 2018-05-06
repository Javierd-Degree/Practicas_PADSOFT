package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Administrator;
import Application.Application;
import views.BannedUserCellView;
import views.LoginWindow;
import views.SearchView;

public class BannedUserCellController implements ActionListener{

	private BannedUserCellView view;
	
	public BannedUserCellController(BannedUserCellView view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object logged = Application.getInstance().searchLoggedIn();
		switch(e.getActionCommand()) {
		case "UNBLOCK":
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
			
			String creditCard = view.getCreditCard();
			if(creditCard == null || creditCard.replaceAll(" ", "").equals("")) {
				JOptionPane.showMessageDialog(new JFrame("Error"), 
						"The credit card cannot be void text.");
				return;
			}
			
			view.getUser().changeCreditCard(creditCard);
			view.getParent().remove(view);
			view.getParent().validate();
			view.getParent().repaint();
			
			break;
		
		}
	}

}
