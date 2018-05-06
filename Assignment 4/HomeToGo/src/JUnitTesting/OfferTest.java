package JUnitTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import Comment.TextComment;
import Date.ModificableDate;
import Exceptions.DateRangeException;
import Exceptions.NotAvailableOfferException;
import House.House;
import Offer.*;
import User.RegisteredUser;
import User.UserType;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.OrderRejectedException;

/*As the unique difference between HolidayOffer and LivingOffer
 * are the constructor and the equals method, we decided to test
 * both on the the same JUnit test.
 * 
 * We decided not to check the setters because they are just usual setters
 * with exceptions when the status does not equals TO_CHANGE.*/

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
		guest = new RegisteredUser("198", "Juan", "Ramirez", "9876543219876543", "Hello world", UserType.GUEST);
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
	public void testValidDateRange() {
		/*The start date cannot be after the end date*/
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018, 5, 17), 799.12);
			fail("Expected failure as the dates are not right");
		} catch (DateRangeException e) {
		}
		
		/*The offer can end and start the same day*/
		try {
			offer = new HolidayOffer(100, LocalDate.of(2018, 5, 18), host, house, LocalDate.of(2018, 5, 17), 799.12);
		} catch (DateRangeException e) {
		}
		
		/*A living offer cannot have a negative or zero number of months*/
		try {
			new LivingOffer(90, LocalDate.of(2018, 9, 11), host, house, 442.7, -1);
			fail("Expected failure as the number of months is not right");
		} catch (DateRangeException e) {
		}
		try {
			new LivingOffer(90, LocalDate.of(2018, 9, 11), host, house, 442.7, 0);
			fail("Expected failure as the number of months is not right");
		} catch (DateRangeException e) {
		}
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
	public void testValidOffer() {
		Offer offer = new LivingOffer(100, LocalDate.now().plusDays(50), host, house, 442.7);
		try {
			offer.askForChanges("Change something");
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		
		/*The host have not made changes but he has time.*/
		ModificableDate.setToday();
		ModificableDate.plusDays(4);
		assertEquals(offer.isValid(ModificableDate.getModifiableDate()), true);
		assertEquals(offer.getStatus(), Offer.TO_CHANGE);

		/*The host have not made changes and he has not more time.*/
		ModificableDate.plusDays(2);
		assertEquals(offer.isValid(ModificableDate.getModifiableDate()), false);
		
		/*The user has reserved the offer and he has not paid, but he has time.*/
		try {
			offer.approveOffer();
			offer.reserveOffer(guest);
		} catch (NotAvailableOfferException e4) {
			System.out.println("Error while trying to reserve the offer");
		}
		ModificableDate.setToday();
		ModificableDate.plusDays(4);
		assertEquals(offer.getStatus(), Offer.RESERVED);
		assertEquals(offer.getGuest(), guest);
		assertEquals(offer.isValid(ModificableDate.getModifiableDate()), true);
		assertEquals(offer.getStatus(), Offer.RESERVED);
		assertEquals(offer.getGuest(), guest);
		
		/*More than five days have passes and the user has not paid, so
		 * the offer should be marked as available.*/
		ModificableDate.plusDays(2);
		assertEquals(offer.isValid(ModificableDate.getModifiableDate()), true);
		assertEquals(offer.getStatus(), Offer.AVAILABLE);
		assertEquals(offer.getGuest(), null);
		
		/*The offer start date has passes and it is not bought.*/
			/*The offer is approved*/
		offer = new LivingOffer(100, LocalDate.now(), host, house, 442.7);
		ModificableDate.setToday();
		ModificableDate.plusDays(1);
		try {
			offer.approveOffer();
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		assertEquals(offer.isValid(ModificableDate.getModifiableDate()), true);
		assertEquals(offer.getStatus(), Offer.NOT_AVAILABLE);
		
			/*The offer is reserved*/
		offer = new LivingOffer(100, LocalDate.now(), host, house, 442.7);
		ModificableDate.setToday();
		ModificableDate.plusDays(1);
		try {
			offer.approveOffer();
			offer.reserveOffer(guest);
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		assertEquals(offer.isValid(ModificableDate.getModifiableDate()), true);
		assertEquals(offer.getStatus(), Offer.NOT_AVAILABLE);
		assertEquals(offer.getGuest(), null);
		
		/*If the offer is bought, nothing should change*/
		offer = new LivingOffer(100, LocalDate.now(), host, house, 442.7);
		ModificableDate.setToday();
		ModificableDate.plusDays(1);
		try {
			offer.approveOffer();
			offer.reserveOffer(guest);
			offer.buyOffer(guest, "Buy");
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		assertEquals(offer.isValid(ModificableDate.getModifiableDate()), true);
		assertEquals(offer.getStatus(), Offer.BOUGHT);
		assertEquals(offer.getGuest(), guest);
	}
	
	@Test
	public void testChangeStatus() {
		/*Let's check that a waiting offer cannot be reserved or bought*/
		try {
			offer.reserveOffer(guest);
			fail("A waiting offer cannot be reserved");
		} catch (NotAvailableOfferException e4) {
		}
		try {
			offer.buyOffer(guest, "Buy offer");
			fail("A waiting offer cannot be bought");
		} catch (FailedInternetConnectionException e4) {
		} catch (NotAvailableOfferException e4) {
		} catch (OrderRejectedException e4) {
		}
		
		/*Deny the offer. Once it is denied, it can't be changed, so
		 * we need to create a new one.*/
		try {
			offer.denyOffer();
		} catch (NotAvailableOfferException e2) {
			System.out.println("Error while trying to deny the offer");
			e2.printStackTrace();
		}
		assertEquals(offer.getStatus(), Offer.DENIED);
		/*Check that as it is denied, we cannot reserve it, buy it, ask
		 * for changes or approve it.*/
			try {
				offer.reserveOffer(guest);
				fail("A denied offer cannot be reserved");
			} catch (NotAvailableOfferException e4) {
			}
			try {
				offer.buyOffer(guest, "Buy offer");
				fail("A denied offer cannot be bought");
			} catch (FailedInternetConnectionException e4) {
			} catch (NotAvailableOfferException e4) {
			} catch (OrderRejectedException e4) {
			}
			try {
				offer.askForChanges("Sample text");
				fail("A denied offer cannot be asked for changes");
			} catch (NotAvailableOfferException e4) {
			}
			try {
				offer.approveOffer();
				fail("A denied offer cannot be asked for changes");
			} catch (NotAvailableOfferException e4) {
			}
		
		
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
		
		/*Let's check that an offer waiting for changes cannot be reserved or bought*/
			try {
				offer.reserveOffer(guest);
				fail("An an offer waiting for changes cannot be reserved");
			} catch (NotAvailableOfferException e4) {
			}
			try {
				offer.buyOffer(guest, "Buy offer");
				fail("An offer waiting for changes cannot be bought");
			} catch (FailedInternetConnectionException e4) {
			} catch (NotAvailableOfferException e4) {
			} catch (OrderRejectedException e4) {
			}
		
		/*Approve the offer.*/
		try {
			offer.approveOffer();
		} catch (NotAvailableOfferException e3) {
			System.out.println("Error while trying to approve the offer");
			e3.printStackTrace();
		}
		assertEquals(offer.getStatus(), Offer.AVAILABLE);
		/*Test that an approved offer cannot be asked for changes, or denied*/
			try {
				offer.askForChanges("Sample text");
				fail("An approved offer cannot be asked for changes");
			} catch (NotAvailableOfferException e4) {
			}
			try {
				offer.denyOffer();
				fail("An approved offer cannot be denied");
			} catch (NotAvailableOfferException e4) {
			}
		
		/*Once the offer is approved, we can reserve and it, just if we are not a host.*/
		try {
			offer.reserveOffer(host);
			fail("A host acnnot reserve an offer.");
		} catch (NotAvailableOfferException e1) {}
			
		try {
			offer.reserveOffer(guest);
			assertEquals(offer.getStatus(), Offer.RESERVED);
		} catch (NotAvailableOfferException e1) {
			System.out.println("Error while trying to reserve the offer on testChangeStatus");
			e1.printStackTrace();
		}
		/*Test that a reserved offer cannot be denied, asked for changes, or
		 * bought by a different user.*/
			try {
				offer.askForChanges("Sample text");
				fail("A reserved offer cannot be asked for changes");
			} catch (NotAvailableOfferException e4) {}
			try {
				offer.denyOffer();
				fail("A reserved offer cannot be denied");
			} catch (NotAvailableOfferException e4) {}
			try {
				offer.buyOffer(host, "Buy offer");
				fail("A reserved offer cannot be bought");
			} catch (FailedInternetConnectionException e4) {
			} catch (NotAvailableOfferException e4) {
			} catch (OrderRejectedException e4) {
			}
			try {
				offer.buyOffer(host, "Buy offer");
				fail("A reserved offer cannot be bought");
			} catch (FailedInternetConnectionException e4) {
			} catch (NotAvailableOfferException e4) {
			} catch (OrderRejectedException e4) {
			}
			RegisteredUser guest2 = new RegisteredUser("226", "Manuel", "Perez", "579", "Hello world", UserType.GUEST);
			try {
				offer.buyOffer(guest2, "Buy offer");
				fail("A reserved offer cannot be bought");
			} catch (FailedInternetConnectionException e4) {
			} catch (NotAvailableOfferException e4) {
			} catch (OrderRejectedException e4) {
			}
			
		
		/*Buy the offer. In this case, we use a guest that won't cause troubles
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
		/*Test the different combinations of buy offer.*/
		
		/*Approve the offer.*/
		try {
			offer.approveOffer();
		} catch (NotAvailableOfferException e3) {
			System.out.println("Error while trying to approve the offer");
			e3.printStackTrace();
		}
		
		RegisteredUser guest2 = new RegisteredUser("226", "Manuel", "Perez", "579", "Hello world", UserType.BOTH);
		/* Check that a host cannot reserve/buy an offer.*/
		try {
			offer.reserveOffer(host);
			fail("A host cannot reserve an offer");
		} catch (NotAvailableOfferException e) {}
		try {
			offer.buyOffer(host, "Buy");
			fail("A host cannot reserve an offer");
		} catch (FailedInternetConnectionException e) {
		} catch (NotAvailableOfferException e) {
		} catch (OrderRejectedException e) {
		}
		/*Reserve the offer and try to buy it with a different user
		 * In this case, the offer won't be bought and the guest
		 * won't change*/
		try {
			offer.reserveOffer(guest);
		} catch (NotAvailableOfferException e1) {
			System.out.println("Error while reserving the offer.");
			e1.printStackTrace();
		}
		try {
			offer.buyOffer(guest2, "Buy");
			fail("The offer is reserved by a different user");
		} catch (FailedInternetConnectionException e) {
		} catch (NotAvailableOfferException e) {
		} catch (OrderRejectedException e) {
		}
		assertEquals(offer.getStatus(), Offer.RESERVED);
		assertEquals(offer.getGuest(), guest);
		
		/*Buy it with a valid user and subject*/
		try {
			offer.buyOffer(guest, "Buy");
		} catch (FailedInternetConnectionException e) {
			System.out.println("Internet error while buying the offer.");
			e.printStackTrace();
		} catch (NotAvailableOfferException e) {
			System.out.println("Error while buying the offer.");
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			System.out.println("Order rejected while buying the offer.");
			e.printStackTrace();
		}
		assertEquals(offer.getStatus(), Offer.BOUGHT);
		assertEquals(offer.getGuest(), guest);
		
		/* Reset the offer and try to buy it with an invalid credit card
		 * The offer should remain available, and the user should be banned,
		 * and no exception should be thrown as it is managed on offer.*/
		before();
		/*Approve the offer.*/
		try {
			offer.approveOffer();
		} catch (NotAvailableOfferException e3) {
			System.out.println("Error while trying to approve the offer");
			e3.printStackTrace();
		}
		
		try {
			offer.buyOffer(guest2, "Buy offer");
		} catch (FailedInternetConnectionException e) {
		} catch (NotAvailableOfferException e) {
		} catch (OrderRejectedException e) {
		}
		assertEquals(offer.getStatus(), Offer.AVAILABLE);
		assertEquals(guest2.getStatus(), RegisteredUser.BANNED);
		
		
		/* Reset the offer and try to buy it with subjects which starts with W or R
		 * The offer should remain available.*/
		before();
		try {
			offer.approveOffer();
		} catch (NotAvailableOfferException e3) {
			System.out.println("Error while trying to approve the offer");
			e3.printStackTrace();
		}
		try {
			offer.buyOffer(guest, "RRRR");
			fail("The subject name starts with R.");
		} catch (FailedInternetConnectionException e) {
		} catch (NotAvailableOfferException e) {
		} catch (OrderRejectedException e) {
		}
		assertEquals(offer.getStatus(), Offer.AVAILABLE);
		
		try {
			offer.buyOffer(guest, "WWWW");
			fail("The subject name starts with W.");
		} catch (FailedInternetConnectionException e) {
		} catch (NotAvailableOfferException e) {
		} catch (OrderRejectedException e) {
		}
		assertEquals(offer.getStatus(), Offer.AVAILABLE);
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
