package Demos;

import java.time.LocalDate;

import Comment.TextComment;
import Date.ModificableDate;
import Exceptions.DateRangeException;
import Exceptions.NotAvailableOfferException;
import House.House;
import Offer.*;
import User.RegisteredUser;
import User.UserType;
import es.uam.eps.padsof.telecard.*;

/*As the unique difference between HolidayOffer and LivingOffer
 * are the constructor and the equals method, we decided to test
 * both on the the same JUnit test.*/

public class OfferDemo {
	private static LivingOffer lOffer;
	private static HolidayOffer offer;
	private static House house;
	/**The differences between host, guest or both
	 * is managed on System, so we do not have to worry
	 * about it here. 
	 */
	private static RegisteredUser host;
	private static RegisteredUser guest;
	
	public static void main(String args[]) {
		System.out.println("This demo checks the functionality of the class Offer\n");
		System.out.println("\nFirst we create a house for the offer\n");
		/*Create the house*/
		house = new House("H9FMHJ7");
		house.addCharacteristic("Street", "Little avenue");
		System.out.println("The house created is: " + house + ".");
		System.out.println("\nWe create a host and a guest for the offers\n");
		/*Create the host*/
		host = new RegisteredUser("112", "Pedro", "Lopez", "1234567890123456", "Hello world", UserType.HOST);
		guest = new RegisteredUser("198", "Juan", "Ramirez", "9876543219876543", "Hello world", UserType.BOTH);
		System.out.println("The host is: " + host + ".");
		System.out.println("The guest is: " + guest + ".");
		System.out.println("\nWe try to create a HolidayOffer with data:  100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  5,  12), 799.12.\n");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  5,  12), 799.12);
			System.out.println("The offer was created. Error in constructor.");
		}catch(DateRangeException e) {
			System.out.println("The end date is before the start date.");
		}
		System.out.println("\nWe create a HolidayOffer with data:  100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12.\n");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			System.out.println("Unwanted exception. Error in the constructor.");
		}
		System.out.println("The created HolidayOffer is: " + offer + ".");
		System.out.println("\nWe create a LivingOffer with data:  100, LocalDate.of(2018, 7, 11), host, house, 442.7.\n");
		lOffer = new LivingOffer(100, LocalDate.of(2018, 7, 11), host, house, 442.7);
		System.out.println("The created LivingOffer is: " + lOffer + ".");
		
		/*Test if the equals works correctly on HolidayOffer*/
		System.out.println("\nNow we check the functionality of equals for HolidayOffer.\n");
		if(offer.equals(offer)) {
			System.out.println("The HolidayOffer is equal to itself.");
		}else {
			System.out.println("The HolidayOffer is not equal to itself, error in equals() of HolidayOffer.");
		}
		
		if(offer.equals(null) == false) {
			System.out.println("The HolidayOffer is not equal to null.");
		}else {
			System.out.println("The HolidayOffer is equal to null, error in equals() of HolidayOffer.");
		}
		try {
			HolidayOffer offer2 =  new HolidayOffer(10, LocalDate.of(2018, 7, 18), host, house, LocalDate.of(2018,  9,  12), 799.12);
			if(offer.equals(offer2) == false) {
				System.out.println("The HolidayOffer is not equal to another HolidayOffer with different data.");
			}else {
				System.out.println("The HolidayOffer is equal to another HolidayOffer with different data, error in equals() of HolidayOffer.");
			}
		}catch(DateRangeException e) {
			/*We ignore the exception as we have already proved it works and we know this offer is correct*/
		}
		
		
		
		/*Test if the equals works correctly on LivingOffer*/
		System.out.println("\nWe do the same for the equals() of LivingOffer.\n");
		if(lOffer.equals(lOffer)) {
			System.out.println("The LivingOffer is equal to itself.");
		}else {
			System.out.println("The LivingOffer is not equal to itself, error in equals() of HolidayOffer.");
		}
		if(lOffer.equals(null) == false) {
			System.out.println("The LivingOffer is not equal to null.");
		}else {
			System.out.println("The LivingOffer is equal to null, error in equals() of HolidayOffer.");
		}
		
		LivingOffer lOffer2 = new LivingOffer(90, LocalDate.of(2018, 9, 11), host, house, 442.7);
		if(lOffer.equals(lOffer2) == false) {
			System.out.println("The LivingOffer is not equal to another LivingOffer with different data.");
		}else {
			System.out.println("The LivingOffer is equal to another LivingOffer with different data, error in equals() of HolidayOffer.");
		}
	
		/*Check if the get price function works on HolidayOffer*/
		System.out.println("\nWe check the functionality of getPrice() of HolidayOffer.\n");
		if(offer.getPrice() == 100+799.12) {
			System.out.println("The price was correctly calculated and is: " + offer.getPrice() +".");
		}else {
			System.out.println("Error in getPrice() of HolidayOffer.");
		}
		
		/*Check if the get price function works on LivingOffer*/
		System.out.println("\nWe do the same with LivingOffer.\n");
		if(lOffer.getPrice() == 100+442.7) {
			System.out.println("The price was correctly calculated and is: " + lOffer.getPrice() +".");
		}else {
			System.out.println("Error in getPrice() of LivingOffer.");
		}
		
		System.out.println("\n We check the functionality of the DateRangeException.\n");
		System.out.println("We try to create a holiday offer with an end date before the start date.\n");
		/*The start date cannot be after the end date*/
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018, 5, 17), 799.12);
			System.out.println("The offer was sucessfully created, error.");
		} catch (DateRangeException e) {
			System.out.println("The offer could not be created as the end date is before the start date.");
		}
		
		System.out.println("\nWe create a holiday offer with the same end date and start date (is allowed).\n");
		/*The offer can end and start the same day*/
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018, 5, 17), 799.12);
			System.out.println("The offer was sucessfully created.");
		} catch (DateRangeException e) {
			System.out.println("The offer was not created, error.");
		}
		
		System.out.println("\nWe try to create a living offer with a negative number of months.\n");
		/*A living offer cannot have a negative or zero number of months*/
		try {
			new LivingOffer(90, LocalDate.of(2018, 9, 11), host, house, 442.7, -1);
			System.out.println("The offer was sucessfully created, error.");
		} catch (DateRangeException e) {
			System.out.println("The offer could not be created as it cannot have a negative number of months.");
		}
		System.out.println("\nWe try to create a living offer with 0 months.\n");
		try {
			new LivingOffer(90, LocalDate.of(2018, 9, 11), host, house, 442.7, 0);
			System.out.println("The offer was sucessfully created, error.");
		} catch (DateRangeException e) {
			System.out.println("The offer could not be created as it cannot have 0 months.");
		}

		/*From here, the methods are inherited from Offer, so they 
		 * are the same from Holiday and Living Offers*/
		System.out.println("\n\nFrom this point the methods are the same for living and holiday offers so we just check them once.\n");

		/*Change the status of the offer to make sure it is working as it should*/
		System.out.println("We check the functionality of the methods that chenge the offer's status.\n");
		System.out.println("We check approveOffer().\n");
		try {
			offer.approveOffer();
			if(offer.getStatus() == Offer.AVAILABLE) {
				System.out.println("The offer was successfully approved.");
			}else {
				System.out.println("Error in approveOffer().");
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("Unwanted exception in approveOffer.");
		}
		System.out.println("\nWe check denyOffer().\n");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			/*We ignore the exception as we have already proved it works and we know this offer is correct*/
		}
		try {
			offer.denyOffer();
			if(offer.getStatus() == Offer.DENIED) {
				System.out.println("The offer was successfully denied.");
			}else {
				System.out.println("Error in denyOffer().");
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not in a status that can be denied.");
		}
		System.out.println("\nWe check askForChanges() with data: Anade mas caracteristicas.\n");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			/*We ignore the exception as we have already proved it works and we know this offer is correct*/
		}
		try {
			offer.askForChanges("Anade mas caracteristicas");
			if(offer.getStatus() ==  Offer.TO_CHANGE){
				System.out.println("The offer's status was successfully changed to TO_CHANGE and the ChangeComment written is: .");
			}else {
				System.out.println("Error in askForChanges().");
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("The offer is not in a status that can be asked for changes.");
		}
		
		/*The offer needs to be approved in order to reserve it.*/
		System.out.println("\nWe check reserveOffer() havong in mind that the offer must be available in order to reserve it.\n");
		try {
			offer.reserveOffer(guest);
			if(offer.getStatus()== Offer.RESERVED) {
				System.out.println("The offer was successfully reserved but was not available, error in reserveOffer()");
			}else {
				System.out.println("The offer was not reserved.");
			}
		} catch (NotAvailableOfferException e1) {
			System.out.println("The reservation could not be done because the offer is not available.");
		}
		System.out.println("\nWe approve the offer and try again.\n");
		try {
			offer.approveOffer();
		}catch(NotAvailableOfferException e) {
			System.out.println("Unwanted exception in approveOffer");
		}
		try {
			offer.reserveOffer(guest);
			if(offer.getStatus() == Offer.RESERVED) {
				System.out.println("The offer was successfully booked by: " + offer.getGuest() + ".");
			}else {
				System.out.println("The offer could not be reserved, error in reserveOffer().");
			}
		} catch (NotAvailableOfferException e1) {
			System.out.println("Error while trying to reserve the offer.");
			e1.printStackTrace();
		}
		System.out.println("\nWe create a new guest that tries to reserve the already reerved offer.\n");
		RegisteredUser guest2 = new RegisteredUser("226", "Manuel", "Perez", "579", "Hello world", UserType.BOTH);
		try {
			offer.reserveOffer(guest2);
			if(offer.getStatus() == Offer.RESERVED && offer.getGuest() == guest2) {
				System.out.println("The offer was successfully booked by the new guest, error in reserveOffer().");
			}else if(offer.getStatus() == Offer.RESERVED && offer.getGuest() == guest) {
				System.out.println("The offer is still reserved by the same user as before.");
			}else {
				System.out.println("Error in reserveOffer().");
			}
		} catch (NotAvailableOfferException e1) {
			System.out.println("An exception was catched because a user tried to reserve an already reserved offer.");
		}
		
		System.out.println("\nWe create a new offer and try to reserve it as a host.\n");
		
		Offer offer2 = new LivingOffer(90, LocalDate.of(2018, 5, 1), host, house, 400);
		
		try {
			offer2.reserveOffer(host);
			if(offer2.getStatus()== Offer.RESERVED) {
				System.out.println("The offer was successfully reserved but the user is a host, error in reserveOffer().");
			}else {
				System.out.println("The offer was not reserved.");
			}
		} catch (NotAvailableOfferException e1) {
			System.out.println("The reservation could not be done because the user is a host.");
		}
		/*Test the different combinations of buy offer.
		 * In this case, we simply ignore the exceptions and 
		 * check the status of the offer, as we do not 
		 * need to manage the exceptions here, but on 
		 * the graphical user interface.*/
		
		System.out.println("\n\nNow we check the buyOffer method with all the possibilities.\n");
		System.out.println("First we try to buy the offer by a user that is not the one who reserved it previously.\n");
		try {
			offer.buyOffer(guest2, "Buy");
			if(offer.getStatus() == Offer.BOUGHT) {
				System.out.println("The offer was successfully bought but it was already reserved by another user, error in buyOffer.");
			}else if(offer.getStatus() == Offer.RESERVED && offer.getGuest() == guest) {
				System.out.println("The offer could not be bought because it was already reserved by another user, and it remains booked by this one.");
			}else {
				System.out.println("Error in buyOffer().");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Caught exception in buyOffer as someone tried to buy an already reserved offer.");
		}
		
		System.out.println("\nWe try to buy the offer by the same user that previously reserved it.\n");
		try {
			offer.buyOffer(guest, "Buy");
			if(offer.getStatus() == Offer.BOUGHT) {
				System.out.println("The offer was successfully bought by the guest: " + offer.getGuest() + ".");
			}else {
				System.out.println("The offer could not be bought, error in buyOffer().");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Unwanted exception in buyOffer.");
			e.printStackTrace();
		}
		
		System.out.println("\nWe try to, as another user, buy the offer which is already bought.\n");
		try {
			
			offer.buyOffer(guest2, "Buy");
			if(offer.getStatus() == Offer.BOUGHT && offer.getGuest() == guest2) {
				System.out.println("The offer was successfully bought but it was already bought, error in buyOffer().");
			}else if(offer.getStatus() == Offer.BOUGHT && offer.getGuest() == guest){
				System.out.println("The offer could not be bought by the new user but is still bought by the first user");
			}else {
				System.out.println("Error in buyOffer.");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Exception caught as someone tried to buy an alread bought offer.");
		}
		
		System.out.println("\n\nWe go back to reserveOffer() to try to reserve an already bought offer.\n");
		try {
			offer.reserveOffer(guest2);
			if(offer.getStatus() == Offer.BOUGHT && offer.getGuest() == guest) {
				System.out.println("The offer could not be reserved as it was already bought.");
			}else if(offer.getStatus() == Offer.RESERVED && offer.getGuest() == guest2) {
				System.out.println("The offer was reserved by the new user, error in reserveOffer().");
			}else {
				System.out.println("Error in reserveOffer().");
			}
		}catch(NotAvailableOfferException e) {
			System.out.println("Caught exception in reserveOffer as someone tried to reserve an already bought offer.");
		}	
		
		System.out.println("\n\nWe reset the offer and continue with the remaining possibilities of buyOffer.\n");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			/*We ignore the exception as we have already proved it works and we know this offer is correct*/
		}
		System.out.println("We try to directly buy an offer that is available and it has not been reserved.\n");
		try {
			offer.approveOffer();
			offer.buyOffer(guest, "Buy");
			if(offer.getStatus() == Offer.BOUGHT) {
				System.out.println("The offer was successfully bought by the guest: " + offer.getGuest() + ".");
			}else {
				System.out.println("The offer could not be bought, error in buyOffer().");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Unwanted exception in buyOffer.");
			e.printStackTrace();
		}
		
		/* Try to buy the offer with an invalid credit card
		* The offer should remain available, and the user should be banned.*/
		System.out.println("\nWe reset again the offer and try to buy it with an invalid credit card.\n");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			/*We ignore the exception as we have already proved it works and we know this offer is correct*/
		}try {
			offer.approveOffer();
			offer.buyOffer(guest2, "Buy offer");
			if(offer.getStatus() == Offer.AVAILABLE && guest2.getStatus() == RegisteredUser.BANNED) {
				System.out.println("The offer remains available and the user was sucessfully banned.");
			}else if(offer.getStatus() == Offer.AVAILABLE && guest2.getStatus() != RegisteredUser.BANNED) {
				System.out.println("The offer remains available but the user was not banned, error in buyOffer.");
			}else if(offer.getStatus() == Offer.BOUGHT) {
				System.out.println("The offer was bought, error in buyOffer.");
			}else {
				System.out.println("Error in buyOffer.");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Invalid credit card exception caught.");
		}	
			
		/* Reset the offer and try to buy it with subjects which starts with W or R
		 * The offer should remain available.*/
		System.out.println("\nWe reset the offer again and try to buy it with subjects that start with W or R.\n");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			/*We ignore the exception as we have already proved it works and we know this offer is correct*/
		}try {	
			offer.approveOffer();
			offer.buyOffer(guest, "RRRR");
			if(offer.getStatus() == Offer.AVAILABLE) {
				System.out.println("With R the offer remains available.");
			}else {
				System.out.println("Error in buyOffer with subject R.");
			}
			offer.buyOffer(guest, "WWWW");
			if(offer.getStatus() == Offer.AVAILABLE) {
				System.out.println("With W the offer remains available.");
			}else {
				System.out.println("Error in buyOffer with subject W.");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Order rejected exception caught as wanted.");
		}

		
		System.out.println("\nWe try to buy the offer as a host.\n");
		
		try {	
			offer.approveOffer();
			offer.buyOffer(host, "BUY");
			if(offer.getStatus() == Offer.AVAILABLE) {
				System.out.println("The offer remains available as the buyer was a host.");
			}else {
				System.out.println("Error in buyOffer.");
			}
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Not available offer exception caught as wanted.");
		}
		
		guest2 = new RegisteredUser("226", "Manuel", "Perez", "579", "Hello world", UserType.BOTH);
		
		
		/*Test the text comments and that the application can distinguish between text and rating comments.
		 * We create a new comment as in this case, we do not have access to the user interface in order
		 * to select the same comment*/
		
		System.out.println("\nFinally we check the functionality of the postComment and calculateRating methods.\n");
		System.out.println("We create a comment with the text: Texto. We post it in the offer and another user posts the answer: Respuesta.\n");
		TextComment comment = new TextComment("Texto", guest);
		if(offer.postComment(guest, "Texto") == true) {
			System.out.println("The comment was posted correctly.");
		}else {
			System.out.println("Error in postComment.");
		}
		if(offer.postComment(guest2, "Respuesta", comment) == true) {
			System.out.println("The answer was posted correctly.");
		}else {
			System.out.println("Error in postComment.");
		}
		
		
		/*Test the rating comments*/
		System.out.println("\nWe post several ratings to the offer: 4, 17, 2, -1.\n");
		if(offer.postComment(guest, 4)== true) {
			System.out.println("The rating was posted correctly.");
		}else {
			System.out.println("Error in postComment.");
		}
		if(offer.postComment(guest, 17) == false) {
			System.out.println("The rating was not posted as it must be between 1 and 10.");
		}else {
			System.out.println("Error in postComment.");
		}
		if(offer.postComment(guest2, 2) == true) {
			System.out.println("The rating was posted correctly.");
		}else {
			System.out.println("Error in postComment.");
		}
		if(offer.postComment(guest2, -1) == false) {
			System.out.println("The rating was not posted as it must be between 1 and 10.");
		}else {
			System.out.println("Error in postComment.");
		}
		System.out.println("\nNow we calculate the rating of the offer, wich sould be 3.\n");
		System.out.println("The rating of the offer is: " + offer.calculateRating());
		
		System.out.println("\nLastly we check the functionality of the isValid method.\n");
		
		System.out.println("We create a new offer and we ask for changes so that it is in to change status.\n");
		Offer offer = new LivingOffer(100, LocalDate.now().plusDays(50), host, house, 442.7);
		try {
			offer.askForChanges("Change something");
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		
		/*The host have not made changes but he has time.*/
		ModificableDate.setToday();
		ModificableDate.plusDays(4);
		System.out.println("\nAfter 4 days the offer should still be To change.\n");
		if(offer.isValid(ModificableDate.getModifiableDate()) == true){
			System.out.println("The offer is still valid.");
		}else {
			System.out.println("Error.");
		}
		if(offer.getStatus() == Offer.TO_CHANGE) {
			System.out.println("The offer is still To Change.");
		}else {
			System.out.println("The offer is not To Change, error.");
		}

		/*The host have not made changes and he has not more time.*/
		System.out.println("\nAfter 6 days the offer should not be valid.\n");
		ModificableDate.plusDays(2);
		if(offer.isValid(ModificableDate.getModifiableDate()) == false) {
			System.out.println("The offer is not valid.");
		}else {
			System.out.println("The offer is valid, error.");
		}
			
		/*The user has reserved the offer and he has not paid, but he has time.*/
		System.out.println("\nNow we approve and make a user reserve the offer so that its status is reserved.\n");
		try {
			offer.approveOffer();
			offer.reserveOffer(guest);
		} catch (NotAvailableOfferException e4) {
			System.out.println("Error while trying to reserve the offer");
		}
		System.out.println("\nAfter 4 days the offer should still be reserved and valid\n.");
		ModificableDate.setToday();
		ModificableDate.plusDays(4);
		if(offer.getStatus() == Offer.RESERVED) {
			System.out.println("The offer is still reserved.");
		}else {
			System.out.println("The offer is not reserved, error.");
		}
		if(offer.isValid(ModificableDate.getModifiableDate()) == true) {
			System.out.println("The offer is valid.");
		}else {
			System.out.println("The offer is not valid, error.");
		}
			
		/*More than five days have passes and the user has not paid, so
		 * the offer should be marked as available.*/
		System.out.println("\nAfter 6 days the offer should be available and valid\n.");
		ModificableDate.plusDays(2);
		if(offer.isValid(ModificableDate.getModifiableDate()) == true) {
			System.out.println("The offer is valid.");
		}else {
			System.out.println("The offer is not valid, error.");
		}
		if(offer.getStatus() == Offer.AVAILABLE && offer.getGuest() == null) {
			System.out.println("The offer is available.");
		}else {
			System.out.println("The offer is not available, error.");
		}

		
		System.out.println("\n\nEnd of the demo.\n");
	}
}
