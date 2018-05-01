package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Administrator;
import Application.Application;
import House.House;
import User.RegisteredUser;
import User.UserType;
import views.CreateOfferView;
import views.SearchResultsView;


public class CreateOfferController implements ActionListener{
	private CreateOfferView view;
	private Map<String, String> houseCharacteristics;
	
	public CreateOfferController(CreateOfferView view) {
		houseCharacteristics = new HashMap<>();
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "HOLIDAY":
			view.setOfferType("HOLIDAY");
			break;
		case "LIVING":
			view.setOfferType("LIVING");
			
			break;
		case "CREATE_HOUSE":
			Object logged = Application.getInstance().searchLoggedIn();
			if(logged instanceof Administrator || ((RegisteredUser)logged).getType() == UserType.GUEST) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			RegisteredUser user = (RegisteredUser) logged;
			
			Boolean result = Application.getInstance().addHouse(view.getHouseId(), this.houseCharacteristics);
			if(result == false) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered house id already exists in our database."
						+" Please, choose a new one.");
				return;
			}
			
			House house = user.getCreatedHouses().get(user.getCreatedHouses().size()-1);
			view.addHouseToList(house);
			view.clearHouse();
			houseCharacteristics.clear();
			break;
		case "ADD_CHARACTERISTIC":
			if(view.getHouseChar() == null || view.getHouseChar().replaceAll(" ", "").equals("")
					|| view.getHouseCharVaue() == null || view.getHouseCharVaue().replaceAll(" ", "").equals("")) {
				return;
			}
			houseCharacteristics.put(view.getHouseChar(), view.getHouseCharVaue());
			view.clearCharacteristic();
			break;
			
		case "CREATE_OFFER":
			Object o = Application.getInstance().searchLoggedIn();
			if(o instanceof Administrator || ((RegisteredUser)o).getType() == UserType.GUEST) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			RegisteredUser host = (RegisteredUser) o;
			
			String start = view.getStartDate();
			String deposit = view.getDeposit();
			String end = view.getEnd();
			String extraPrice = view.getExtraPrice();
			House house2 = view.getHouse();
			/*Check is everything is completed.*/
			if(start == null || start.replaceAll(" ", "").equals("") || deposit == null || deposit.replaceAll(" ", "").equals("")
					|| end == null  || end.replaceAll(" ", "").equals("") || extraPrice == null || extraPrice.replaceAll(" ", "").equals("")
					|| house2 == null) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"All the fields must be filled.");
				return;
			}
			
			LocalDate startDate = SearchController.stringToDate(start);
			if(startDate == null) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered date is not valid.\n"
						+"You need to enter a date with format dd/mm/yyy:\n");
				return;
			}
			
			Double dDeposit = SearchController.stringToDouble(deposit);
			if(dDeposit == null || dDeposit < 0) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered deposit is not valid.\n"
						+"It must be a non negative number.\n");
				return;
			}
			
			Double dExtraPrice = SearchController.stringToDouble(extraPrice);
			
			/*A holiday offer*/
			if(view.getSelected().equals("HOLIDAY")) {
				LocalDate endDate = SearchController.stringToDate(end);
				if(endDate == null) {
					JOptionPane.showMessageDialog(new JFrame("Error"),
							"The entered end date is not valid.\n"
							+"You need to enter a date with format dd/mm/yyy:\n");
					return;
				}
				
				if(dExtraPrice == null || dExtraPrice < 0) {
					JOptionPane.showMessageDialog(new JFrame("Error"),
							"The entered total price is not valid.\n"
							+"It must be a non negative number.\n");
					return;
				}
				
				Boolean result2 = Application.getInstance().addOffer(dDeposit, dExtraPrice, startDate, endDate, house2, host);
				if(result2 == false) {
					JOptionPane.showMessageDialog(new JFrame("Error"),
							"Sorry, there was an error creating your offer, try again later.");
					return;
				}
			/*A living offer*/
			}else {
				Integer nMonths = SearchController.stringToInteger(end.replaceAll(" ", ""));
				if(nMonths == null || nMonths <= 0) {
					JOptionPane.showMessageDialog(new JFrame("Error"),
							"The entered number of months is not valid.\n"
							+"It must be a positive integer.\n");
					
					System.out.println(end.replaceAll(" ", ""));
					return;
				}
				
				if(dExtraPrice == null || dExtraPrice < 0) {
					JOptionPane.showMessageDialog(new JFrame("Error"),
							"The entered price per month is not valid.\n"
							+"It must be a non negative number.\n");
					return;
				}
				
				Boolean result3 = Application.getInstance().addOffer(dDeposit, dExtraPrice, startDate, house2, host, nMonths);
				if(result3 == false) {
					JOptionPane.showMessageDialog(new JFrame("Error"),
							"Sorry, there was an error creating your offer, try again later.");
					return;
				}
				
			}
			
			JOptionPane.showMessageDialog(new JFrame("Success"),
					"Your offer was successfully created!.");
			
			SearchResultsView v = new SearchResultsView(host.seeOffers(), SearchResultsView.HOST_CREATED);
			SearchResultsController c = new SearchResultsController(v);
			v.setController(c);
			Application.getWindow().setSecondaryView(v);
			
			break;
		
		}
	}
}
