package Demos;

import java.time.LocalDate;
import java.util.*;

import Application.Administrator;
import Application.Application;
import Exceptions.NotAvailableOfferException;
import Offer.Offer;
import User.RegisteredUser;
import User.UserType;
import es.uam.eps.padsof.telecard.OrderRejectedException;

public class ApplicationDemo {
	public static void main(String args[]) {
		Application app = Application.getInstance();
		
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
		
		
		boolean res = app.addHouse("ABCDE");
		if(res == true) {
			System.out.println("The house was sucessfully added.");
		}else {
			System.out.println("The house could not be added, error.");
		}
		
		res = app.addHouse("ABCDE");
		if(res == true) {
			System.out.println("The house was sucessfully added, error.");
		}else {
			System.out.println("The house could not be added as expected.");
		}
		
		res = app.addHouse(null);
		if(res == true) {
			System.out.println("The house was sucessfully added, error.");
		}else {
			System.out.println("The house could not be added as expected.");
		}
		
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
		
		user.getCreatedHouses().get(0).addCharacteristic("ZIP_CODE", "E322");
		user.getCreatedHouses().get(1).addCharacteristic("ZIP_CODE", "E333");
		
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), app.getHouses().get(0), null);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), null, user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		res = app.addOffer(100, 400, null, user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		res = app.addOffer(100, 4070, LocalDate.of(2018, 9, 10), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), user.getCreatedHouses().get(1), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		
		
		
		res = app.addOffer(90, 700, LocalDate.of(2019, 9, 1), LocalDate.of(2019, 7, 5), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		res = app.addOffer(90, 700, LocalDate.of(2019, 9, 1), LocalDate.of(2019, 11, 5), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), user.getCreatedHouses().get(0), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), user.getCreatedHouses().get(1), null);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
			
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), null, user);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}	
		
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
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2018, 12, 5), user.getCreatedHouses().get(1), user);
		if(res == true) {
			System.out.println("The offer was sucessfully added.");
		}else {
			System.out.println("The offer could not be added, error.");
		}
		
		app.logout();
		
		
		
		app = Application.getInstance();
		
		o = app.searchLoggedIn();
		if(o == null) {
			System.out.println("There is noone logged in");
		}else if(o instanceof Administrator) {
			System.out.println("There is an administrator logged in, error");
		}else {
			System.out.println("There is a user logged in, error.");
		}
		
		users = app.getUsers();
		
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
		
		res = app.addHouse("ABCDE");
		if(res == true) {
			System.out.println("The house was sucessfully added, error.");
		}else {
			System.out.println("The house could not be added.");
		}
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), user.getCreatedHouses().get(1), user2);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		res = app.addOffer(100, 400, LocalDate.of(2018, 7, 13), user.getCreatedHouses().get(1), user2);
		if(res == true) {
			System.out.println("The offer was sucessfully added, error.");
		}else {
			System.out.println("The offer could not be added.");
		}
		
		
		List<Offer> zips = app.searchByZIP("E322");
		System.out.println("The offers with this ZIP code are: " + zips + ".");
		
		List<Offer> dates = app.searchByDate(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
		System.out.println("The offers in this dates are: " + dates + ".");
		
		List<Offer> types = app.searchByType(0);
		System.out.println("The holiday offers are: " + types + ".");
		
		List<Offer> types2 = app.searchByType(1);
		System.out.println("The living offers are: " + types2 + ".");
		
		try {
			dates.get(1).reserveOffer(user2);
			System.out.println("The offer was sucessfully reserved, error");
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not available.");
		}
		
		try {
			dates.get(1).buyOffer(user2, "BUY");
			System.out.println("The offer was sucessfully bought, error");
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("The offer is not available.");
		}
		
		app.logout();
		

		app = Application.getInstance();
		
		users = app.getUsers();
		
		Administrator admin = new Administrator("001", "Pedro", "Fernandez", "pepe123");
		
		app.addAdmin(admin);
		
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
		
		List<Offer> notApproved = app.seeNonApprovedOffer();
		
		try {
			for(Offer off: notApproved) {
				off.askForChanges("Do changes");
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("Some offers were not WAITING, error");
		}
		
		app.logout();
		
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
		app.logout();
		
		
		app = Application.getInstance();
		
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
		

		notApproved = app.seeNonApprovedOffer();
		
		try {
			for(Offer off: notApproved) {
				off.approveOffer();
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("Some offers were not WAITING, error");
		}
		
		app.logout();
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
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
		
		dates = app.searchByDate(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
		System.out.println("The offers in this dates are: " + dates + ".");
		
		
		try {
			dates.get(0).reserveOffer(user2);
			System.out.println("The offer was sucessfully reserved.");
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not available, error.");
		}
		
		try {
			dates.get(1).reserveOffer(user2);
			System.out.println("The offer was sucessfully reserved.");
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not available, error.");
		}
		
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
		
		app.logout();
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
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
		
		
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		o = app.searchLoggedIn();
		if(o != null) {
			System.out.println("There is someone logged in, error");
		}else {
			System.out.println("There is noone logged in.");
		}
		
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
		
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
		result = app.login(users.get(0).getId(), users.get(0).getPassword());
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
		
		System.out.println("The already reserved offers are: " + app.searchReservedOffers() + ".");
		System.out.println("The already bought offers are: " + app.searchBoughtOffers() + ".");	
		
		app.logout();	
		
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
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
		
		app.logout();
		
		app = Application.getInstance();
		
		users = app.getUsers();
		
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
		
		app.logout();	
	}
}
