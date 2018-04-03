package Demos;

import java.time.LocalDate;

import House.House;
import Offer.LivingOffer;
import Offer.Offer;
import User.RegisteredUser;
import User.UserType;

/*We do not test the getters and setters as they are
 * extremely simple and they do not return anything.*/

public class RegisteredUserDemo {
	private static RegisteredUser user;
	
	public void main() {
		System.out.println("We initialise a RegisteredUser with data: 198, Juan, Ramirez, 9876543219876543, Hello world, UserType.BOTH.");
		user = new RegisteredUser("198", "Juan", "Ramirez", "9876543219876543", "Hello world", UserType.BOTH);
		System.out.println("The user created is: " + user + ".");
		System.out.println("We initialise another RegisteredUser with data: 197, Juan, Ramirez, 9876543219876543, Hello world, UserType.BOTH.");
		RegisteredUser user2 = new RegisteredUser("197", "Juan", "Ramirez", "9876543219876543", "Hello world", UserType.BOTH);
		System.out.println("The user created is: " + user2 + ".");
		System.out.println("We check the functionality of the equals method.");
		
		if(user.equals(user) == true) {
			System.out.println("A user is equal to itself.");
		}else {
			System.out.println("Error in equals.");
		}
		
		if(user.equals(null) == false) {
			System.out.println("A user is not equal to null.");
		}else {
			System.out.println("A user is equal to null, error in equals.");
		}
		if(user.equals(user2) == false) {
			System.out.println("A user is not equal to another user with different id.");
		}else {
			System.out.println("A user is equal to another user with different id, error in equals.");
		}
		if(user.equals(new RegisteredUser("198", "Juan", "Ramirez", "9876543219876543", "Hello world", UserType.BOTH)) == true) {
			System.out.println("A user is equal to another user with the same id.");
		}else {
			System.out.println("A user is not equal to another user with the same id, error in equals.");
		}
		
		System.out.println("We check the functionality of addOffer");
		System.out.println("We create an offer with data: 100, LocalDate.of(2018, 7, 11), user, new House(\"H9FMHJ7\"), 442.7");
		System.out.println("We create another offer with data: 100, LocalDate.of(2018, 11, 1), user, new House(\"ABCDE87\"), 6127");
		boolean result;
		Offer offer = new LivingOffer(100, LocalDate.of(2018, 7, 11), user, new House("H9FMHJ7"), 442.7);
		Offer offer2 = new LivingOffer(100, LocalDate.of(2018, 11, 1), user, new House("ABCDE87"), 612);
		
		/*Add a created offer that is not added*/
		System.out.println("We add an offer to the user created offer's list.");
		result = user.addOffer(offer, RegisteredUser.CREATED_OFFER);
		if(result == true) {
			System.out.println("The offer was added successfully.");
		}else {
			System.out.println("Error in addOffer.");
		}
		
		/*Add a created offer that is already added*/
		System.out.println("We add an offer that is already added to the list to the user's created offer's list.");
		result = user.addOffer(offer, RegisteredUser.CREATED_OFFER);
		if(result == false) {
			System.out.println("The offer was not added as it is already in the array.");
		}else {
			System.out.println("The offer was added to the array, error in addOffer.");
		}
		
		/*Add a bought offer that is not added*/
		System.out.println("We add an offer to the user's bought offer's list.");
		result = user.addOffer(offer, RegisteredUser.HIST_OFFER);
		if(result == true) {
			System.out.println("The offer was added successfully.");
		}else {
			System.out.println("Error in addOffer.");
		}
		
		/*Add a bought offer that is already added*/
		System.out.println("We add an offer that is already added to the list to the user's bought offer's list.");
		result = user.addOffer(offer, RegisteredUser.HIST_OFFER);
		if(result == false) {
			System.out.println("The offer was not added as it is already in the array.");
		}else {
			System.out.println("The offer was added to the array, error in addOffer.");
		}
		
		/*Remove a created offer that is already added*/
		System.out.println("We check the functionality of removeOffer.");
		System.out.println("We remove an offer that is in the user's created offer's list.");
		result = user.removeOffer(offer, RegisteredUser.CREATED_OFFER);
		result = user.addOffer(offer, RegisteredUser.HIST_OFFER);
		if(result == true) {
			System.out.println("The offer was removed successfully.");
		}else {
			System.out.println("Error in removedOffer.");
		}
		
		/*Remove a created offer that is not added*/
		System.out.println("We remove an offer that is not in the user's created offer's list.");
		result = user.removeOffer(offer2, RegisteredUser.CREATED_OFFER);
		if(result == false) {
			System.out.println("The offer was not removed as it is not in the array.");
		}else {
			System.out.println("The offer was removed, error in addOffer.");
		}
		
		/*Remove a bought offer that is already added*/
		System.out.println("We remove an offer that is in the user's bought offer's list.");
		result = user.removeOffer(offer, RegisteredUser.HIST_OFFER);
		if(result == true) {
			System.out.println("The offer was removed successfully.");
		}else {
			System.out.println("Error in removedOffer.");
		}
		
		/*Remove a bought offer that is not added*/
		System.out.println("We remove an offer that is not in the user's created offer's list.");
		result = user.removeOffer(offer2, RegisteredUser.HIST_OFFER);
		if(result == false) {
			System.out.println("The offer was not removed as it is not in the array.");
		}else {
			System.out.println("The offer was removed, error in addOffer.");
		}

		System.out.println("We check the functionality of addHouse.");
		System.out.println("We add a house that was not previously added to the user's house list.");
		/*Add a house that has not been added previously*/
		if(user.addHouse(new House("QWERTY")) == true) {
			System.out.println("The house was added correctly.");
		}else {
			System.out.println("The house could not be added, error in addHouse.");
		}
		
		/*Add a house that has been added previously*/
		System.out.println("We add a house that was previously added to the user's house list.");
		if(user.addHouse(new House("QWERTY")) == false) {
			System.out.println("The house was not added as it was already in the list.");
		}else {
			System.out.println("The house was added, error in addHouse.");
		}
	}

}
