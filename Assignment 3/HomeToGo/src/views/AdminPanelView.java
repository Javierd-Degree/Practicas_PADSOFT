package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Application.Application;
import User.RegisteredUser;
import controllers.BannedUserCellController;

public class AdminPanelView extends JPanel{

	private static final long serialVersionUID = 4693506462550476617L;
	
	SearchResultsView offers;
	
	public AdminPanelView() {
		super();
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		
		offers = new SearchResultsView(Application.getInstance().getOffers(), SearchResultsView.ADMINISTRATOR);
		offers.setBorder(BorderFactory.createTitledBorder("To be approved"));
		add(offers, c);
		
		c.gridy = 1;
		c.weighty = 0.4;
		c.fill = GridBagConstraints.BOTH;
		JPanel bannedUsers = createBannedUsersView();
		add(bannedUsers, c);
	}
	
	public JPanel createBannedUsersView() {
		JPanel view = new JPanel(new FlowLayout());
		view.setBorder(BorderFactory.createTitledBorder("Banned users"));
		view.setPreferredSize(new Dimension(1280, 280));
		
		for(RegisteredUser user: Application.getInstance().getUsers()) {
			if(user.getStatus() == RegisteredUser.BANNED) {
				BannedUserCellView v = new BannedUserCellView(user, view);
				BannedUserCellController c = new BannedUserCellController(v);
				v.setController(c);
				view.add(v);
			}
		}
		
		return view;
	}
	
	public void setControler(ActionListener c) {
		offers.setAdminButtonsController(c);
	}

}
