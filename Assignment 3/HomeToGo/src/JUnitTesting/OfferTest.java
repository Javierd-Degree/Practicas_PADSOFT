package JUnitTesting;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import Comment.TextComment;
import Exceptions.DateRangeException;
import Exceptions.NotAvailableOfferException;
import House.House;
import Offer.*;
import User.RegisteredUser;
import User.UserType;
import es.uam.eps.padsof.telecard.OrderRejectedException;

/*As the unique difference between HolidayOffer and LivingOffer
 * are the constructor and the equals method, we decided to test
 * both on the the same JUnit test.*/

public class OfferTest {
	private static LivingOffer lOffer;
	private static HolidayOffer offer;
	private static House house;
	/**The differences between host, guest or both
	 * is managed on System, so we do not have to worry
	 * about it here. 
	 */
	private static RegisteredUser host;
	private static RegisteredUser guest;
	
	@Before
	public void before() {
		/*Create the house*/
		house = new House("H9FMHJ7");
		house.addCharacteristic("Street", "Little avenue");
		
		/*Create the host*/
		host = new RegisteredUser("112", "Pedro", "Lopez", "1234567890123456", "Hello world", UserType.HOST);
		guest = new RegisteredUser("198", "Juan", "Ramirez", "9876543219876543", "Hello world", UserType.HOST);
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018,  6,  12), 799.12);
		} catch (DateRangeException e) {
			System.out.println("Error triying to create Holiday Offer");
			e.printStackTrace();
		}
		lOffer = new LivingOffer(100, LocalDate.of(2018, 7, 11), host, house, 442.7);
	}
	
	@Test
	public void testEquals(){
		/*Test if the equals works correctly on HolidayOffer*/
		assertEquals(offer.equals(offer), true);
		assertEquals(offer.equals(null), false);
		
		HolidayOffer offer2;
		try {
			offer2 = new HolidayOffer(10, LocalDate.of(2018, 7, 18), host, house, LocalDate.of(2018,  9,  12), 799.12);
			assertEquals(offer.equals(offer2), false);
		} catch (DateRangeException e) {
			System.out.println("Error triying to create Holiday Offer");
			e.printStackTrace();
		}
		
		/*Test if the equals works correctly on LivingOffer*/
		assertEquals(lOffer.equals(lOffer), true);
		assertEquals(lOffer.equals(null), false);
		
		LivingOffer lOffer2 = new LivingOffer(90, LocalDate.of(2018, 9, 11), host, house, 442.7);
		assertEquals(lOffer.equals(lOffer2), false);
	}
	
	@Test
	public void testPrice() {
		/*Check if the get price function works on HolidayOffer*/
		assertEquals(offer.getPrice(), 100+799.12, 0.1);
		
		/*Check if the get price function works on LivingOffer*/
		assertEquals(lOffer.getPrice(), 100+442.7, 0.1);
	}
	
	
	/*From here, the methods are inherited from Offer, so they 
	 * are the same from Holiday and Living Offers.
	 * We avoid the exceptions here because JUnit marks them as
	 * failures, so we test them on the Java tests.*/
	@Test
	public void testChangeStatus() {
		/*Deny the offer. Once it is denied, it can't be changed, so
		 * we need to create a new one.*/
		try {
			offer.denyOffer();
		} catch (NotAvailableOfferException e2) {
			System.out.println("Error while trying to deny the offer");
			e2.printStackTrace();
		}
		assertEquals(offer.getStatus(), Offer.DENIED);
		
		/*Create a new offer, as the previous one was denied.
		 * Ask for changes on the offer.*/
		before();
		try {
			offer.askForChanges("Anade mas caracteristicas");
		} catch (NotAvailableOfferException e2) {
			System.out.println("Error while trying to ask for changes on an offer");
			e2.printStackTrace();
		}
		assertEquals(offer.getStatus(), Offer.TO_CHANGE);
		
		/*Approve the offer.*/
		try {
			offer.approveOffer();
		} catch (NotAvailableOfferException e3) {
			System.out.println("Error while trying to approve the offer");
			e3.printStackTrace();
		}
		assertEquals(offer.getStatus(), Offer.AVAILABLE);
		
		/*Once the offer is approved, we can reserve it.*/
		try {
			offer.reserveOffer(guest);
			assertEquals(offer.getStatus(), Offer.RESERVED);
		} catch (NotAvailableOfferException e1) {
			System.out.println("Error while trying to reserve the offer on testChangeStatus");
			e1.printStackTrace();
		}
		
		/*Buy the user. In this case, we use a guest that won't cause troubles
		 *as we test the buy offer method in a different function*/
		try {
			offer.buyOffer(guest, "Buy offer");
			assertEquals(offer.getStatus(), Offer.BOUGHT);
			
		} catch (NotAvailableOfferException | OrderRejectedException e) {
			System.out.println("Error while trying to buy the offer on testChangeStatus");
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testReserveOffer() {
		/*In this case, as the offer is no AVAILABLE, it will
		 * throw an exception and it wont be reserved*/
		try {
			offer.reserveOffer(guest);
			assertEquals(offer.getStatus(), Offer.WAITING);
			
			offer.approveOffer();
			offer.reserveOffer(guest);
			assertEquals(offer.getStatus(), Offer.RESERVED);
			assertEquals(offer.getGuest(), guest);
			
		}catch(NotAvailableOfferException e) {
			/*Ignore the exceptions as they will be used on system.*/
		}
	}
	
	@Test
	public void testBuyOffer(){
		/*Test the different combinations of buy offer.
		 * In this case, we simply ignore the exceptions and 
		 * check the status of the offer, as we do not 
		 * need to manage the exceptions here, but on 
		 * the graphical user interface.*/
		
		try {
			RegisteredUser guest2 = new RegisteredUser("226", "Manuel", "Perez", "579", "Hello world", UserType.GUEST);
			/*Reserve the offer and try to buy it with a different user
			 * In this case, the user won't be bought and the guest
			 * won't change*/
			offer.reserveOffer(guest);
			offer.buyOffer(guest2, "Buy");
			assertEquals(offer.getStatus(), Offer.RESERVED);
			assertEquals(offer.getGuest(), guest);
			
			/*Buy it with a valid user and subject*/
			offer.buyOffer(guest, "Buy");
			assertEquals(offer.getStatus(), Offer.BOUGHT);
			assertEquals(offer.getGuest(), guest);
			
			/* Reset the offer and try to buy it with an invalid credit card
			 * The offer should remain available, and the user should be banned.*/
			/*TODO, deberiamos llamar a system.unlog, asi que a lo mejor no hay que banearlo aqui.*/
			before();
			offer.approveOffer();
			offer.buyOffer(guest2, "Buy offer");
			assertEquals(offer.getStatus(), Offer.AVAILABLE);
			assertEquals(guest2.getStatus(), RegisteredUser.BANNED);
			
			
			/* Reset the offer and try to buy it with subjects which starts with W or R
			 * The offer should remain available.*/
			before();
			offer.approveOffer();
			offer.buyOffer(guest, "RRRR");
			assertEquals(offer.getStatus(), Offer.AVAILABLE);
			offer.buyOffer(guest, "WWWW");
			assertEquals(offer.getStatus(), Offer.AVAILABLE);
		}catch(NotAvailableOfferException | OrderRejectedException e) {
			/*Ignore the exceptions as they will be used on system.*/
		}
	}
	
	@Test
	public void testCommentAndRating() {
		RegisteredUser guest2 = new RegisteredUser("226", "Manuel", "Perez", "579", "Hello world", UserType.GUEST);
		/*Test the text comments and that the application can distinguish between text and rating comments.
		 * We create a new comment as in this case, we do not have access to the user interface in order
		 * to select the same comment*/
		TextComment comment = new TextComment("Texto", guest);
		assertEquals(offer.postComment(guest, "Texto"), true);
		assertEquals(offer.postComment(guest2, "Respuesta", comment), true);
		assertEquals(offer.postComment(guest2, "Respuesta", null), false);
		
		
		/*Test the rating comments*/
		assertEquals(offer.postComment(guest, 4), true);
		assertEquals(offer.postComment(guest, 17), false);
		assertEquals(offer.postComment(guest2, 2), true);
		assertEquals(offer.postComment(guest2, -1), false);
		assertEquals(offer.calculateRating(), 3, 0.001);
	}
}
