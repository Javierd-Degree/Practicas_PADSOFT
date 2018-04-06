package Demos;


import java.time.LocalDate;
import java.util.*;

import Application.Administrator;
import Application.Application;
import Date.ModificableDate;
import Exceptions.NotAvailableOfferException;
import Offer.Offer;
import User.RegisteredUser;
import User.UserType;
import es.uam.eps.padsof.telecard.OrderRejectedException;

public class ApplicationDemo {
	public static void main(String args[]) {
		System.out.println("This demo checks the functionality of the whole application.\n");
		System.out.println("\nFirst we get an instance of the application instead of creating one so that there is only 1 application running at the same time.");
		System.out.println("\nIn the get instance method we also load the data from the file.\n");
		Application app = Application.getInstance();
		
		System.out.println("\nWe try to login with an incorrect id.\n");
		int result = app.login("abcd", "abcde");
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful, error.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found.");
		}
		Object o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in.");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("There is a user logged in, error.");
		}
		
		System.out.println("\nWe now try to login with an existing id, but a password that does not match the id.\n");
		
		List<RegisteredUser> users = app.getUsers();
		
		result = app.login(users.get(6).getId(), users.get(4).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful, error.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("There is a user logged in, error.");
		}
		
		System.out.println("\nNow we log in correctly with a host user with an incorrect credit card.\n");
		
		result = app.login(users.get(5).getId(), users.get(5).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		System.out.println("\nWe try to login with another user at the same time.\n");
		
		result = app.login(users.get(4).getId(), users.get(4).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful, error.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}else if(result == Application.SOMEONE_LOGGED) {
			System.out.println("There is someon already logged in.");
		}
		
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		System.out.println("\nWe add a new house.\n");
		
		boolean res = app.addHouse("ABCDE");
		if(res == true) {
			System.out.println("The house was sucessfully added.");
		}else {
			System.out.println("The house could not be added, error.");
		}
		
		System.out.println("\nWe try to add the same houose again.\n");
		
		res = app.addHouse("ABCDE");
		if(res == true) {
			System.out.println("The house was sucessfully added, error.");
		}else {
			System.out.println("The house could not be added as expected.");
		}
		
		System.out.println("\nWe try to add a house with id null.\n");
		
		res = app.addHouse(null);
		if(res == true) {
			System.out.println("The house was sucessfully added, error.");
		}else {
			System.out.println("The house could not be added as expected.");
		}
		
		System.out.println("\nWe add a different house.\n");
		
		res = app.addHouse("ABFGE");
		if(res == true) {
			System.out.println("The house was sucessfully added.");
		}else {
			System.out.println("The house could not be added, error.");
		}
		
		
		RegisteredUser user = ((RegisteredUser)o);
		if(user == null) {
			System.out.println("El usuario es null, error");
			return;
		}
		
		System.out.println("\nWe add a characteristic ZIP code to both houses, the first one will have the zip code E322, and the second one E333.\n");
		
		user.getCreatedHouses().get(0).addCharacteristic("ZIP_CODE", "E322");
		user.getCreatedHouses().get(1).addCharacteristic("ZIP_CODE", "E333");
		
		System.out.println("\nWe try to create a new living offer with a host null.\n");
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), app.getHouses().get(0), null);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe create a new living offer with house null.\n");
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), null, user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe create a new living offer with start date null.\n");
		
		res = app.addOffer(100, 400, null, user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe create a living offer for the first house.\n");
		
		res = app.addOffer(100, 200, LocalDate.of(2018, 7, 13), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		System.out.println("\nWe try to create another living offer for the same house.\n");
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 9, 10), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe create a living offer for the second house.\n");
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), user.getCreatedHouses().get(1), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		
		System.out.println("\nWe create a holiday offer with an ending date prior to the start date.\n");
		
		res = app.addOffer(90, 700, LocalDate.of(2019, 9, 1), LocalDate.of(2019, 7, 5), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe create a holiday offer for the first house.\n");
		
		res = app.addOffer(90, 700, LocalDate.of(2019, 9, 1), LocalDate.of(2019, 11, 5), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		System.out.println("\nWe try to create another holiday offer for the same house.\n");
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe try to create a holiday offer with hotst null.\n");
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), user.getCreatedHouses().get(1), null);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
			
		System.out.println("\nWe try to create a holiday offer with house null.\n");
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), null, user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}	
		
		System.out.println("\nWe try to create two holiday offers one with null start date and one with null end date.\n");
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), null, user.getCreatedHouses().get(1), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}	
		
		res = app.addOffer(90, 450, null, LocalDate.of(2019, 12, 5), user.getCreatedHouses().get(1), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe create a holiday offer for second house.\n");
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2018, 12, 5), user.getCreatedHouses().get(1), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		System.out.println("\nWe logout and get a new instance of the application as the logout method exits the application putting instance to null.");
		
		app.logout();
		
		
		
		app = Application.getInstance();
		
		System.out.println("\nWe check that noone is logged in.\n");
		
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("There is a user logged in, error.");
		}
		
		users = app.getUsers();
		
		System.out.println("\nWe log in as a user that is both host and guest and has an incorrect credit card number.\n");
		
		result = app.login(users.get(3).getId(), users.get(3).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		RegisteredUser user2 = ((RegisteredUser)o);
		if(user2 == null) {
			System.out.println("El usuario es null, error");
			return;
		}
		
		System.out.println("\nWe try to create a house with the same id as one previously created by the host ligged in before.\n");
		
		res = app.addHouse("ABCDE");
		if(res == true) {
			System.out.println("The house was sucessfully added, error.");
		}else {
			System.out.println("The house could not be added.");
		}
		
		System.out.println("\nWe try to create a holiday offer with the house added by the other host.\n");
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), users.get(5).getCreatedHouses().get(1), user2);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe try to create a living offer with the house added by the other host.\n");
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), users.get(5).getCreatedHouses().get(1), user2);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		System.out.println("\nWe search offers by zip code E322.\n");
		
		List<Offer> zips = app.searchByZIP("E322");
		if(zips == null) {
			return;
		}
		System.out.println("The offers with this ZIP code are: " + zips + ".");
		
		System.out.println("\nWe search offers by date between 1/1/2018 and 31/12/2018.\n");
		
		List<Offer> dates = app.searchByDate(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
		if(dates == null) {
			return;
		}
		System.out.println("The offers in this dates are: " + dates + ".");
		
		System.out.println("\nWe search for holiday offers.\n");
		
		List<Offer> types = app.searchByType(Application.HOLIDAY_OFFER);
		if(types == null) {
			return;
		}
		System.out.println("The holiday offers are: " + types + ".");
		
		System.out.println("\nWe search for living offers.\n");
		
		List<Offer> types2 = app.searchByType(Application.LIVING_OFFER);
		if(types2 == null) {
			return;
		}
		
		System.out.println("The living offers are: " + types2 + ".");
		
		System.out.println("\nWe add a rating of 5 to the first offer found when searching by zip code and we search by ratings over 2.\n");
		
		zips.get(0).postComment(user2, 5);
		
		List<Offer> ratings = app.searchByRating(2);
		if(ratings == null) {
			return;
		}
		System.out.println("The offers with a rating higher than 2 are: " + ratings + ".");
		
		System.out.println("\nWe try to reserve an offer (they have not been approved yet).\n");
		
		try {
			dates.get(1).reserveOffer(user2);
			System.out.println("The offer was sucessfully reserved, error");
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not available.");
		}
		
		System.out.println("\nWe try to buy an offer (they have not been approved yet).\n");
		
		try {
			dates.get(1).buyOffer(user2, "BUY");
			System.out.println("The offer was sucessfully bought, error");
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("The offer is not available.");
		}
		
		System.out.println("\nWe logout and get a new instance of the application.\n");
				
		app.logout();
		

		app = Application.getInstance();
		
		users = app.getUsers();
		
		System.out.println("\nWe create a new administrator, and add it to the application.\n");
		
		Administrator admin = new Administrator("001", "Pedro", "Fernandez", "pepe123");
		
		res = app.addAdmin(admin);
		if(res == true){
			System.out.println("The administrator was sucessfully added.");	
		}else {
			System.out.println("The administrator could not be added, error");
		}
		
		System.out.println("\nWe login as the administrator.\n");
		
		app.login(admin.getId(), admin.getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There administrator: " + ((Administrator)o) + " is logged in.");
		}else {
			System.out.println("There is a user logged in, error.");
		}
		
		admin = ((Administrator)o);
		if(admin == null) {
			System.out.println("El administrador es null, error");
			return;
		}
		
		System.out.println("\nWe search for the not approved offers.\n");
		
		List<Offer> notApproved = app.seeNonApprovedOffer();
		System.out.println("The not approved offers are: " + notApproved + ".");
		
		System.out.println("\nWe ask for changes in all the not approved offers and logout.\n");
		
		try {
			for(Offer off: notApproved) {
				off.askForChanges("Do changes");
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("Some offers were not WAITING, error");
		}
		
		app.logout();
		
		System.out.println("\nWe get a new instance of the application and we log in as the first host.\n");
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		result = app.login(users.get(5).getId(), users.get(5).getPassword());
		if(result == 0) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error.");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		user = ((RegisteredUser)o);
		if(user == null) {
			System.out.println("El usuario es null, error");
			return;
		}
		
		System.out.println("\nWe make changes to all the offers that require them.\n");
		
		try {
			for(Offer off : user.seeOffers()) {
					off.setDeposit(150);
				if(off.getStatus() == Offer.WAITING) {
					System.out.println("The offer: " + off + " was sucessfully changed and is waiting for approval.");
				}
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("Some offers were not in the status TO_CHANGE");
		}
		
		System.out.println("\nWe try to search for reserved offers.\n");
		
		List<Offer> reservedOffers = app.searchReservedOffers();
		if(reservedOffers == null) {
			System.out.println("The current user is not a guest.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe try to search for bought offers.\n");
		
		List<Offer> boughtOffers = app.searchBoughtOffers();
		if(boughtOffers == null) {
			System.out.println("The current user is not a guest.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe try to search for offers with rating higher than 2.\n");
		
		ratings = app.searchByRating(2);
		if(ratings == null) {
			System.out.println("The current user is not a guest.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe logout and get a new instance of the application.\n");
		
		app.logout();
		
		
		app = Application.getInstance();
		
		System.out.println("\nWe log in as the administrator.\n");
		
		result = app.login(app.getAdmins().get(0).getId(), app.getAdmins().get(0).getPassword());
		
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There administrator: " + ((Administrator)o) + " is logged in.");
		}else {
			System.out.println("There is a user logged in, error.");
		}
		
		admin = ((Administrator)o);
		if(admin == null) {
			System.out.println("El administrador es null, error");
			return;
		}
		
		System.out.println("\nWe try to search for reserved offers.\n");
		
		reservedOffers = app.searchReservedOffers();
		if(reservedOffers == null) {
			System.out.println("The current user is not a guest.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe try to search for bought offers.\n");
		
		boughtOffers = app.searchBoughtOffers();
		if(boughtOffers == null) {
			System.out.println("The current user is not a guest.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe try to search for offers with a rating higher tha 2.\n");
		
		ratings = app.searchByRating(2);
		if(ratings == null) {
			System.out.println("The current user is not a guest.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe search for not approved offers and approve all of them.\n");
		
		notApproved = app.seeNonApprovedOffer();
		if(notApproved == null) {
			return;
		}
		
		try {
			for(Offer off: notApproved) {
				off.approveOffer();
				if(off.getStatus() == Offer.AVAILABLE) {
					System.out.println("The offer is now available.");
				}
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("Some offers were not WAITING, error");
		}
		
		System.out.println("\nWe logout and get a new instance of the application.\n");
		
		app.logout();
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		System.out.println("\nWe log in as a user that is both host and guest with a correct credit card.\n");
		
		result = app.login(users.get(2).getId(), users.get(2).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		user2 = ((RegisteredUser)o);
		if(user2 == null) {
			System.out.println("El usuario es null, error");
			return;
		}
		
		System.out.println("\nWe search for offers by dates between 1/1/2018 and 31/12/2018.\n");
		
		dates = app.searchByDate(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
		System.out.println("The offers in this dates are: " + dates + ".");
		
		System.out.println("\nWe reserve the first of the searched offers.\n");
		
		try {
			dates.get(0).reserveOffer(user2);
			System.out.println("The offer was sucessfully reserved.");
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not available, error.");
		}
		
		System.out.println("\nWe reserve the second of the searched offers.\n");
		
		try {
			dates.get(1).reserveOffer(user2);
			System.out.println("The offer was sucessfully reserved.");
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not available, error.");
		}
		
		System.out.println("\nWe buy the first of the searched offers.\n");
		
		try {
			dates.get(1).buyOffer(user2, "BUY");
			if(user2.getStatus() == RegisteredUser.BANNED) {
				System.out.println("The guest's credit card is incorrect, the offer was not bought, the guest was sucessfully banned, error.");	
			}else if(dates.get(1).getHost().getStatus() == RegisteredUser.BANNED) {
				System.out.println("The host's credit card is incorrect, the offer was bought, the debt to the host was added, the host was sucessfully banned.");
			}else {
				System.out.println("The offer was sucessfully bought, error.");
			}
		}catch(OrderRejectedException | NotAvailableOfferException e) {
				System.out.println("The offer was not available, error.");
		}
		
		System.out.println("The bought offer is: " + user2.seeHistory() + ".");
		
		System.out.println("\nWe logout and get a new instance of the application.\n");
		
		app.logout();
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		System.out.println("\nWe log in as a guest with an incorrect credit card.\n");
		
		result = app.login(users.get(3).getId(), users.get(3).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error.");
		}else {
			System.out.println("There user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		RegisteredUser user3 = ((RegisteredUser)o);
		if(user3 == null) {
			return;
		}
		
		System.out.println("\nWe try to buy an offer.\n");
		try {
			dates.get(2).buyOffer(user3, "BUY");
			if(user3.getStatus() == RegisteredUser.BANNED) {
				System.out.println("The guest's credit card is incorrect, the offer was not bought, the guest was sucessfully banned.");	
			}else if(dates.get(1).getHost().getStatus() == RegisteredUser.BANNED) {
				System.out.println("The host's credit card is incorrect, the offer was bought, the debt to the host was added, the host was sucessfully banned, error.");
			}else {
				System.out.println("The offer was sucessfully bought, error.");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("The offer was not available, error.");
		}
		
		System.out.println("\nWe get a new instance of the application as when you ban the logged in user, the user logs out.\n");
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		System.out.println("\nWe check that noone is logged in.\n");
		
		o = app.searchLoggedIn();
		if(o != null) {
			System.out.println("There is someone logged in, error");
		}else {
			System.out.println("There is noone logged in.");
		}
		
		System.out.println("\nWe login as the administrator.\n");
		
		result = app.login(app.getAdmins().get(0).getId(), app.getAdmins().get(0).getPassword());
		
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There administrator: " + ((Administrator)o) + " is logged in.");
		}else {
			System.out.println("There is a user logged in, error.");
		}
		
		admin = ((Administrator)o);
		
		System.out.println("\nWe search for banned users and we unban them by changing their credit card and then we logout.\n");
		
		List<RegisteredUser> bannedUsers = app.seeBannedUsers();
		System.out.println("The banned users are: " + bannedUsers + ".");
		for(RegisteredUser u : bannedUsers) {
			u.changeCreditCard("3333222211110000");
			if(u.getStatus() == RegisteredUser.UNLOGGED) {
				System.out.println("The user: " + u + " was successfully unbanned.");
				if(u.getType() == UserType.HOST) {
					System.out.println("The host was successfully paid.");
				}
			}
		}
		
		app.logout();
		
		System.out.println("\nWe get a new instance of the application.\n");
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		System.out.println("\nWe login as the first host.\n");
		
		result = app.login(users.get(5).getId(), users.get(5).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		user = ((RegisteredUser)o);
		if(user == null) {
			return;
		}
		
		System.out.println("\nWe add a new house and create a holiday and a living offer for this one.\n");
		
		res = app.addHouse("XEDR");
		
		if(res == true) {
			System.out.println("The house was successfully added");
		}else {
			System.out.println("Error");
		}
		
		res = app.addOffer(90, 100, LocalDate.of(2018, 6, 20), user.getCreatedHouses().get(2), user);
		if(res == true) {
			System.out.println("The offer was successfully added");
		}else {
			System.out.println("Error");
		}
		
		res = app.addOffer(80, 400, LocalDate.of(2018, 5, 18), LocalDate.of(2018, 6, 17), user.getCreatedHouses().get(2), user);
		if(res == true) {
			System.out.println("The offer was successfully added");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe cancel the not approved offers.\n");
		
		try {
			for(Offer of : user.seeOffers()) {
				if(of.getStatus()==Offer.WAITING || of.getStatus() == Offer.TO_CHANGE) {
					app.cancelOffer(of);
					System.out.println("The offer was successfully cancelled.");
					break;
				}
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer could not be cancelled as it is already approved, error.");
		}
		
		System.out.println("\nWe try to cancel the already approved offers.\n");
		
		try {
			for(Offer of : user.seeOffers()) {
				if(of.getStatus() != Offer.WAITING && of.getStatus() != Offer.TO_CHANGE) {
					app.cancelOffer(of);
					System.out.println("The offer was successfully cancelled, error.");
					break;
				}
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer could not be cancelled as it is already approved.");
		}
		
		System.out.println("\nWe logout and get a new instance of the application.\n");
		
		app.logout();
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		System.out.println("\nWe login as a user that is both host and guest.\n");
		
		result = app.login(users.get(3).getId(), users.get(3).getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		user = ((RegisteredUser)o);
		if(user == null) {
			return;
		}
		
		System.out.println("\nWe try to cancel an offer from another host.\n");
		
		try {
			for(Offer of : users.get(5).seeOffers()) {
				if(of.getStatus()==Offer.WAITING || of.getStatus() == Offer.TO_CHANGE) {
					app.cancelOffer(of);
					System.out.println("The offer was successfully cancelled, error.");
					break;
				}
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer could not be cancelled the logged user is not its host.");
		}
		
		System.out.println("\nWe logout and get a new instance of the application.\n");
		
		app.logout();	
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		System.out.println("\nWe login as a guest.\n");
		
		result = app.login(users.get(4).getId(), users.get(4).getPassword());
		
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		System.out.println("\nWe try to add a house.\n")
		;
		res = app.addHouse("SDFG");
		if(res == true){
			System.out.println("The house was sucessfully added, error.");
		}else {
			System.out.println("The house could not be added as the logged user is not a host.");
		}
		
		System.out.println("\nWe try to add an offer.\n");
		
		res = app.addOffer(90, 100, LocalDate.of(2018, 2, 27), app.getHouses().get(1), ((RegisteredUser)app.searchLoggedIn()));
		if(res == true){
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added as the logged user is not a host.");
		}
		
		
		System.out.println("\nWe try to search for not approved offers.\n");
		
		notApproved = app.seeNonApprovedOffer();
		if(notApproved == null) {
			System.out.println("There is a user logged in, not an admin.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe search for reserved offers.\n");
		System.out.println("The already reserved offers are: " + app.searchReservedOffers() + ".");
		
		System.out.println("\nWe search for bought offers.\n");
		System.out.println("The already bought offers are: " + app.searchBoughtOffers() + ".");	
		
		System.out.println("\nWe logout and let 6 days pass and get a new instance of the application.\n");
		
		
		/*Close the application. Wait six days so that now the first offer 
		 * is not valid and it is denied and deleted (because it is a 
		 * holiday offer, and the user did not made the changes).
		 * The third offer should now be available, as the user
		 * who reserved it has not paid.*/
		app.logout();
		
		ModificableDate.setToday();
		ModificableDate.plusDays(6);
		
		app = Application.getInstance();
		
		System.out.println("\nWe login as a user that is both host and guest.\n");
		
		user = app.getUsers().get(3);
		
		result = app.login(user.getId(), user.getPassword());
		if(result == Application.SUCCESS) {
			System.out.println("Login sucessful.");
		}else if(result == Application.NO_MATCHED) {
			System.out.println("The password does not match the id, error.");
		}else if(result == Application.NOT_FOUND_ID) {
			System.out.println("The id could not be found, error.");
		}
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in, error");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("The user: " + ((RegisteredUser)o) + " is logged in.");
		}
		
		System.out.println("\nWe check that the offers that were reserved are now available as more then 5 days have passed.\n");
		
		System.out.println("The reserved offers are: " + app.searchReservedOffers() + ".");
		
		System.out.println("\nWe check that the bought offers remain the same.\n");
		
		System.out.println("The bought offers are: " + app.searchBoughtOffers() + ".");
		
		System.out.println("\nWe logout and get a new instance of the application.\n");
		
		app.logout();
		
		app = Application.getInstance();
		
		System.out.println("\nWe do not login.\n");
		
		if(app.searchLoggedIn() == null) {
			System.out.println("There is noone logged in");
		}else {
			System.out.println("Error.");
		}
		
		System.out.println("\nWe try to search for reserved offers.\n");
		
		reservedOffers = app.searchReservedOffers();
		if(reservedOffers == null) {
			System.out.println("The current user is not a RegisteredUser.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe try to search for bought offers.\n");
		
		boughtOffers = app.searchBoughtOffers();
		if(boughtOffers == null) {
			System.out.println("The current user is not a RegisteredUser.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe try to search for offers with a rating that is higher than 2.\n");

		ratings = app.searchByRating(2);
		if(ratings == null) {
			System.out.println("The current user is not a registeredUser.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe try to search for not approved offers.\n");
		
		notApproved = app.seeNonApprovedOffer();
		if(ratings == null) {
			System.out.println("The current user is not an administrator.");
		}else {
			System.out.println("Error");
		}
		
		System.out.println("\nWe search for holiday offers.\n");
		
		types = app.searchByType(Application.HOLIDAY_OFFER);
		System.out.println("The holiday offers are: " + types +".");
		
		System.out.println("\nWe search for offers by date between 1/1/2018 and 31/12/2018.\n");
		
		dates = app.searchByDate(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
		System.out.println("The offers  between these dates are: " + dates +".");
		
		System.out.println("\nWe search for offers with zip code E333.\n");
		
		zips = app.searchByZIP("E333");
		if(zips == null) {
			return;
		}
		System.out.println("The offers with this ZIP code are: " + zips +".");
		
		System.out.println("\nWe try to add a house.\n");
		
		res = app.addHouse("ADHG");
		if(res == true) {
			System.out.println("Error");
		}else {
			System.out.println("There is no RegisteredUser logged in");
		}
		
		System.out.println("\nWe try to add an offer.\n");
		
		res = app.addOffer(90, 100, LocalDate.of(2018, 5, 5), app.getHouses().get(0), app.getUsers().get(5));
		if(res == true) {
			System.out.println("Error");
		}else {
			System.out.println("There is no RegisteredUser logged in");
		}
		
		System.out.println("\nWe do not try to buy or reserve an offer as it is already proved in the OfferDemo.\n");
		System.out.println("\nEnd of the demo.\n");
	}
}
