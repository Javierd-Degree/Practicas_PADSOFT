package Demos;

import java.time.LocalDate;
import java.util.*;

import Application.Administrator;
import Application.Application;
import Offer.Offer;
import User.RegisteredUser;

public class ApplicationDemo {
	public static void main(String args[]) {
		Application app = Application.getInstance();
		
		int result = app.login("abcd", "abcde");
		if(result == 0) {
			System.out.println("Login sucessful, error.");
		}else if(result == -1) {
			System.out.println("The password does not match the id, error.");
		}else if(result == -2) {
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
		
		app.login(users.get(2).getId(), users.get(3).getPassword());
		if(result == 0) {
			System.out.println("Login sucessful, error.");
		}else if(result == -1) {
			System.out.println("The password does not match the id.");
		}else if(result == -2) {
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
		
		app.login(users.get(2).getId(), users.get(2).getPassword());
		if(result == 0) {
			System.out.println("Login sucessful.");
		}else if(result == -1) {
			System.out.println("The password does not match the id, error.");
		}else if(result == -2) {
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
		
		user.getCreatedHouses().get(0).addCharacteristic("ZIP_CODE", "E322");
		user.getCreatedHouses().get(0).addCharacteristic("ZIP_CODE", "E333");
		
		
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
		
		res = app.addOffer(90, 450, LocalDate.of(2018, 9, 1), LocalDate.of(2019, 12, 5), user.getCreatedHouses().get(1), user);
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
		
		app.login(users.get(3).getId(), users.get(3).getPassword());
		if(result == 0) {
			System.out.println("Login sucessful.");
		}else if(result == -1) {
			System.out.println("The password does not match the id, error.");
		}else if(result == -2) {
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
		
		
		
		
		
		
	}
}
