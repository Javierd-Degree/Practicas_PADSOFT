package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Application;
import Offer.Offer;
import views.SearchResultsView;
import views.SearchView;

public class SearchController implements ActionListener{

	SearchView view;
	
	public SearchController(SearchView view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "SEARCH":
			String text = view.getSearchText();
			search(view.getSelected(), text);
			
			break;
			
		case "ZIP_SEARCH":	
			view.setTextHint("ZIP code");
			view.enableTextField(true);
			break;
		case "OFFER_SEARCH":
			view.setTextHint("Insert Holiday or Living.");
			view.enableTextField(true);
			break;
		case "DATE_SEARCH":
			view.setTextHint("dd/mm/yyyy - dd/mm/yyyy");
			view.enableTextField(true);
			break;
		case "RATING_SEARCH":
			view.setTextHint("Minimun rating");
			view.enableTextField(true);
			break;
		case "RESERVED_SEARCH":
			view.setTextHint("Search reserved offers.");
			view.enableTextField(false);
			break;
		case "BOOKED_SEARCH":
			view.setTextHint("Search booked offers.");
			view.enableTextField(false);
			break;
			
		}
		
	}
	
	public void search(String type, String text) {
		Application app = Application.getInstance();
		List<Offer> result = null;
		switch(type) {
		case "ZIP_SEARCH":
			if(text == null || text.equals("")) {
				return;
			}
			
			result = app.searchByZIP(text);
			break;
			
		case "OFFER_SEARCH":
			if(text == null || text.equals("")) {
				return;
			}
			
			if(!text.equals("Holiday") && !text.equals("Living")) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"You can just enter Holiday or Living.");
				return;
			}
			
			if(text.equals("Holiday")) {
				result = app.searchByType(Application.HOLIDAY_OFFER);
			}else {
				result = app.searchByType(Application.LIVING_OFFER);
			}
			break;
			
		case "DATE_SEARCH":
			if(text == null || text.equals("")) {
				return;
			}
			
			text = text.replaceAll(" ", "");
			String[] dates = text.split("-");
			if(dates.length != 2) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"You need to enter two dates separated by a -. For example:\n"
						+ "21/04/2018 - 04/05/2018");
				return;
			}
			
			LocalDate start = stringToDate(dates[0]);
			LocalDate end = stringToDate(dates[1]);
			
			if(start == null || end == null) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered dates are not valid.\n"
						+"You need to enter two dates separated by a -. For example:\n"
						+ "21/04/2018 - 04/05/2018");
				return;
			}
			
			result = app.searchByDate(start, end);
			
			break;
			
		case "RATING_SEARCH":
			if(text == null || text.equals("")) {
				return;
			}
			
			Double rating = stringToDouble(text);
			if(rating == null) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The entered text is not a valid rating.");
				return;
			}
			if(rating < 0 || rating > 5) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"The rating must be a number between 0 and 5.");
				return;
			}
			result = app.searchByRating(rating);
			break;
			
		case "RESERVED_SEARCH":
			result = app.searchReservedOffers();
			break;
			
		case "BOOKED_SEARCH":
			result = app.searchBoughtOffers();
			break;
		}
		
		if(result == null) {
			JOptionPane.showMessageDialog(new JFrame("Error"),
					"Upps, something bad happened, but anyway, nobody is "
					+ "perfect. Try again later.");
			return;
		}
		
		if(result.size() == 0) {
			JOptionPane.showMessageDialog(new JFrame("Error"),
					"Sorry, currently there are no available offers for your search.");
			return;
		}
	
		/*Once we have the results, we can change the view.*/
		SearchResultsView v = new SearchResultsView(result, view.getLogged());
		SearchResultsController c = new SearchResultsController(v);
		v.setController(c);
		Application.getWindow().setSecondaryView(v);
	}
	
	public Double stringToDouble(String rating) {
		try {
			return Double.parseDouble(rating);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	public LocalDate stringToDate(String date) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
			return LocalDate.parse(date, formatter);
		}catch(DateTimeParseException e) {
			return null;
		}
	}
}
