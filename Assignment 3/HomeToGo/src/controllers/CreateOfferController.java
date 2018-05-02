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
import Exceptions.NotAvailableOfferException;
import House.House;
import Offer.HolidayOffer;
import Offer.LivingOffer;
import Offer.Offer;
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
			
			if(view.getHouseId().equals(CreateOfferView.newHouseString)) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered house id is not valid.\n"
						+"Please, choose a new one.");
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
			
			if(view.getHousePanelMode()) {
				houseCharacteristics.put(view.getHouseChar(), view.getHouseCharVaue());
			}else {
				view.getHouse().addCharacteristic(view.getHouseChar(), view.getHouseCharVaue());
			}
			
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
			
			if(house2 == null || house2.getId().equals(CreateOfferView.newHouseString)) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"You need to select a house.");
				
				return;
			}
			
			/*Check is everything is completed.*/
			if(start == null || start.replaceAll(" ", "").equals("") || deposit == null || deposit.replaceAll(" ", "").equals("")
					|| end == null  || end.replaceAll(" ", "").equals("") || extraPrice == null || extraPrice.replaceAll(" ", "").equals("")) {
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
							"Sorry, there was an error creating your offer, try again later.\n"
							+ "Maybe you already have one offer of this type on the selected house, at the same time range.");
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
							"Sorry, there was an error creating your offer, try again later.\n"
							+ "Maybe you already have one offer of this type on the selected house, at the same time range.");
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
			
		case "UPDATE_OFFER":
			Object loggedUser = Application.getInstance().searchLoggedIn();
			if(loggedUser instanceof Administrator || ((RegisteredUser)loggedUser).getType() == UserType.GUEST) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			RegisteredUser hostUser = (RegisteredUser) loggedUser;
			
			if(updateOffer(view.getOffer()) == false) {
				return;
			}
			
			if((view.getOffer() instanceof LivingOffer) && !Application.getInstance().validOffer((LivingOffer)view.getOffer()) ||
					(view.getOffer() instanceof HolidayOffer) && !Application.getInstance().validOffer((HolidayOffer)view.getOffer())) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The house you selected has another offer of the same type in the same time range.");
				return;
			}
			
			JOptionPane.showMessageDialog(new JFrame("Success"),
					"Your offer was successfully updated!.");
			
			SearchResultsView resultsView = new SearchResultsView(hostUser.seeOffers(), SearchResultsView.HOST_CREATED);
			SearchResultsController controller = new SearchResultsController(resultsView);
			resultsView.setController(controller);
			Application.getWindow().setSecondaryView(resultsView);
			
			break;
			
		case "HOUSE_SELECTED":
			if(!view.getHouse().getId().equals(CreateOfferView.newHouseString)) {
				view.housePanelMode(false);
			}else {
				view.housePanelMode(true);
			}

			break;
			
		case "CANCEL_OFFER":
			Object log = Application.getInstance().searchLoggedIn();
			if(log instanceof Administrator || ((RegisteredUser)log).getType() == UserType.GUEST) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			RegisteredUser hostLogged = (RegisteredUser) log;
			
			try {
				view.getOffer().denyOffer();
			} catch (NotAvailableOfferException e) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"You cannot cancel this offer.");
				return;
			}
			
			JOptionPane.showMessageDialog(new JFrame("Success"),
					"Your offer was successfully canceled!.");
			
			SearchResultsView resultsView2 = new SearchResultsView(hostLogged.seeOffers(), SearchResultsView.HOST_CREATED);
			SearchResultsController controller2 = new SearchResultsController(resultsView2);
			resultsView2.setController(controller2);
			Application.getWindow().setSecondaryView(resultsView2);
			
			break;
		}
	}
	
	private boolean updateOffer(Offer offer) {		
		String start = view.getStartDate();
		String deposit = view.getDeposit();
		String end = view.getEnd();
		String extraPrice = view.getExtraPrice();
		House house2 = view.getHouse();
		
		/*Check is everything is completed.*/
		if(house2 == null || house2.getId().equals(CreateOfferView.newHouseString)) {
			JOptionPane.showMessageDialog(new JFrame("Error"),
					"You need to select a house.");
			
			return false;
		}
		if(start == null || start.replaceAll(" ", "").equals("") || deposit == null || deposit.replaceAll(" ", "").equals("")
				|| end == null  || end.replaceAll(" ", "").equals("") || extraPrice == null || extraPrice.replaceAll(" ", "").equals("")) {
			JOptionPane.showMessageDialog(new JFrame("Error"),
					"All the fields must be filled.");
			return false;
		}
		
		LocalDate startDate = SearchController.stringToDate(start);
		if(startDate == null) {
			JOptionPane.showMessageDialog(new JFrame("Error"),
					"The entered date is not valid.\n"
					+"You need to enter a date with format dd/mm/yyy:\n");
			return false;
		}
		
		Double dDeposit = SearchController.stringToDouble(deposit);
		if(dDeposit == null || dDeposit < 0) {
			JOptionPane.showMessageDialog(new JFrame("Error"),
					"The entered deposit is not valid.\n"
					+"It must be a non negative number.\n");
			return false;
		}
		
		Double dExtraPrice = SearchController.stringToDouble(extraPrice);
		
		/*A holiday offer*/
		if(view.getSelected().equals("HOLIDAY")) {
			LocalDate endDate = SearchController.stringToDate(end);
			if(endDate == null) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered end date is not valid.\n"
						+"You need to enter a date with format dd/mm/yyy:\n");
				return false;
			}
			
			if(dExtraPrice == null || dExtraPrice < 0) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered total price is not valid.\n"
						+"It must be a non negative number.\n");
				return false;
			}
			
			try {
				offer.setDeposit(dDeposit);
				offer.setHouse(house2);
				offer.setStartDate(startDate);
				((HolidayOffer)offer).setEndDate(endDate);
				((HolidayOffer)offer).setTotalPrice(dExtraPrice);
			} catch (NotAvailableOfferException e) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			
		/*A living offer*/
		}else {
			Integer nMonths = SearchController.stringToInteger(end.replaceAll(" ", ""));
			if(nMonths == null || nMonths <= 0) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered number of months is not valid.\n"
						+"It must be a positive integer.\n");
				
				System.out.println(end.replaceAll(" ", ""));
				return false;
			}
			
			if(dExtraPrice == null || dExtraPrice < 0) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered price per month is not valid.\n"
						+"It must be a non negative number.\n");
				return false;
			}
			
			try {
				offer.setDeposit(dDeposit);
				offer.setHouse(house2);
				offer.setStartDate(startDate);
				((LivingOffer)offer).setNumberMonths(nMonths);
				((LivingOffer)offer).setPricePerMonth(dExtraPrice);
			} catch (NotAvailableOfferException e) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}		
		return true;
	}
	
}
