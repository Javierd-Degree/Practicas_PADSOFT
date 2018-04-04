package Demos;

import java.time.LocalDate;

import Comment.TextComment;
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
		System.out.println("This demo checks the functionality of the class Offer");
		System.out.println("First we create a house for the offer");
		/*Create the house*/
		house = new House("H9FMHJ7");
		house.addCharacteristic("Street", "Little avenue");
		System.out.println("The house created is: " + house + ".");
		System.out.println("We create a host and a guest for the offers");
		/*Create the host*/
		host = new RegisteredUser("112", "Pedro", "Lopez", "1234567890123456", "Hello world", UserType.BOTH);
		guest = new RegisteredUser("198", "Juan", "Ramirez", "9876543219876543", "Hello world", UserType.BOTH);
		System.out.println("The host is: " + host + ".");
		System.out.println("The guest is: " + guest + ".");
		System.out.println("We try to create a HolidayOffer with data:  100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  5,  12), 799.12.");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  5,  12), 799.12);
			System.out.println("The offer was created. Error in constructor.");
		}catch(DateRangeException e) {
			System.out.println("The end date is before the start date..");
		}
		System.out.println("We create a HolidayOffer with data:  100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12.");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			System.out.println("Unwanted exception. Error in the constructor.");
		}
		System.out.println("The created HolidayOffer is: " + offer + ".");
		System.out.println("We create a LivingOffer with data:  100, LocalDate.of(2018, 7, 11), host, house, 442.7.");
		lOffer = new LivingOffer(100, LocalDate.of(2018, 7, 11), host, house, 442.7);
		System.out.println("The created LivingOffer is: " + lOffer + ".");
		
		/*Test if the equals works correctly on HolidayOffer*/
		System.out.println("Now we check the functionality of equals for HolidayOffer.");
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
		System.out.println("We do the same for the equals() of LivingOffer");
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
		System.out.println("We check the functionality of getPrice() of HolidayOffer.");
		if(offer.getPrice() == 100+799.12) {
			System.out.println("The price was correctly calculated and is: " + offer.getPrice() +".");
		}else {
			System.out.println("Error in getPrice() of HolidayOffer.");
		}
		
		/*Check if the get price function works on LivingOffer*/
		System.out.println("We do the same with LivingOffer.");
		if(lOffer.getPrice() == 100+442.7) {
			System.out.println("The price was correctly calculated and is: " + lOffer.getPrice() +".");
		}else {
			System.out.println("Error in getPrice() of LivingOffer.");
		}

		/*From here, the methods are inherited from Offer, so they 
		 * are the same from Holiday and Living Offers*/
		System.out.println("From this poit the methods are the same for living and holiday offers so we just check them onec.");

		/*Change the status of the offer to make sure it is working as it should*/
		System.out.println("We check the functionality of the methods that chenge the offer's status.");
		System.out.println("We check approveOffer().");
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
		System.out.println("We check denyOffer().");
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
		System.out.println("We check askForChanges() with data: Anade mmas caracteristicas.");
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
		System.out.println("We check reserveOffer() havong in mind that the offer must be available in order to reserve it.");
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
		System.out.println("We approve the offer and try again.");
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
		System.out.println("We create a new guest that tries to reserve the already reerved offer");
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
		/*Test the different combinations of buy offer.
		 * In this case, we simply ignore the exceptions and 
		 * check the status of the offer, as we do not 
		 * need to manage the exceptions here, but on 
		 * the graphical user interface.*/
		
		System.out.println("Now we check the buyOffer method with all the possibilities");
		System.out.println("First we try to buy the offer by a user that is not the one who reserved it previously.");
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
		
		System.out.println("We try to buy the offer by the same user that previously reserved it.");
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
		
		System.out.println("We try to, as another user, buy the offer which is already bought.");
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
		
		System.out.println("We go back to reserveOffer() to try to reserve an already bought offer.");
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
		
		System.out.println("We reset the offer and continue with the remaining possibilities of buyOffer.");
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		}catch(DateRangeException e) {
			/*We ignore the exception as we have already proved it works and we know this offer is correct*/
		}
		System.out.println("We try to directly buy an offer that is available and it has not been reserved.");
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
		/*TODO, deberiamos llamar a system.unlog, asi que a lo mejor no hay que banearlo aqui.*/
		System.out.println("We reset again the offer and try to buy it with an invalid credit card.");
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
		System.out.println("We reset the offer again and try to buy it with subjects that start with W or R.");
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

		
		guest2 = new RegisteredUser("226", "Manuel", "Perez", "579", "Hello world", UserType.BOTH);
		/*Test the text comments and that the application can distinguish between text and rating comments.
		 * We create a new comment as in this case, we do not have access to the user interface in order
		 * to select the same comment*/
		
		System.out.println("Finally we check the functionality of the postComment and calculateRating methods.");
		System.out.println("We create a comment with the text: Texto. We post it in the offer and another user posts the answer: Respuesta.");
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
		System.out.println("We post several ratings to the offer: 4, 17, 2, -1.");
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
		System.out.println("Lastly we calculate the rating of the offer, wich sould be 3.");
		System.out.println("Thhe rating of the offer is: " + offer.calculateRating());
	}
}
