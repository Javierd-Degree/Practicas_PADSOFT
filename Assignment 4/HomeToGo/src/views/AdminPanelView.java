package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Application.Application;
import Offer.Offer;
import User.RegisteredUser;
import controllers.AdminOfferCellController;
import controllers.BannedUserCellController;

public class AdminPanelView extends JPanel{

	private static final long serialVersionUID = 4693506462550476617L;
	
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
		
		JPanel offers2 = createOffersView();
		add(offers2, c);
		
		c.gridy = 1;
		c.weighty = 0.4;
		c.fill = GridBagConstraints.BOTH;
		JPanel bannedUsers = createBannedUsersView();
		add(bannedUsers, c);
	}
	
	public JPanel createBannedUsersView() {
		JPanel mainView = new JPanel(new BorderLayout());
		mainView.setBorder(BorderFactory.createTitledBorder("Banned users"));
		mainView.setPreferredSize(new Dimension(1280, 280));
		JPanel view = new JPanel(new FlowLayout());
		view.setBorder(BorderFactory.createEmptyBorder());
		
		for(RegisteredUser user: Application.getInstance().getUsers()) {
			if(user.getStatus() == RegisteredUser.BANNED) {
				BannedUserCellView v = new BannedUserCellView(user, view);
				BannedUserCellController c = new BannedUserCellController(v);
				v.setController(c);
				view.add(v);
			}
		}
		
		mainView.add(new JScrollPane(view), BorderLayout.CENTER);
		return mainView;
	}
	
	public JPanel createOffersView() {
		JPanel mainView = new JPanel(new BorderLayout());
		JPanel view = new JPanel(new GridLayout(0, 1));
		mainView.setBorder(BorderFactory.createTitledBorder("To be approved"));
		view.setBorder(BorderFactory.createEmptyBorder());
		
		for(Offer o: Application.getInstance().getOffers()) {
			if(o.getStatus() == Offer.WAITING) {
				AdminOfferCellView v = new AdminOfferCellView(o, view);
				AdminOfferCellController c = new AdminOfferCellController(v);
				v.setController(c);
				view.add(v);
			}
		}
		
		mainView.add(new JScrollPane(view), BorderLayout.CENTER);
		return mainView;
	}
}
