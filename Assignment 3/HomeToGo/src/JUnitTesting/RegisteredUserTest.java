package JUnitTesting;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import Comment.ChangeComment;
import House.House;
import Offer.LivingOffer;
import Offer.Offer;
import User.RegisteredUser;

/*We do not test the getters and setters as they are
 * extremely simple and they do not return anything.*/

public class RegisteredUserTest {
	private static RegisteredUser user;
	
	@Before
	public void before() {
		user = new RegisteredUser(198, "Juan", "Ramirez", "9876543219876543", "Hello world");
	}
	
	@Test
	public void testEquals() {
		RegisteredUser user2 = new RegisteredUser(197, "Juan", "Ramirez", "9876543219876543", "Hello world");
		assertEquals(user.equals(user), true);
		assertEquals(user.equals(null), false);
		assertEquals(user.equals(user2), false);
		assertEquals(user.equals(new RegisteredUser(198, "Juan", "Ramirez", "9876543219876543", "Hello world")), true);
	}
	
	@Test
	public void testAddOffer() {
		boolean result;
		Offer offer = new LivingOffer(100, LocalDate.of(2018, 7, 11), user, new House("H9FMHJ7"), 442.7);
		Offer offer2 = new LivingOffer(100, LocalDate.of(2018, 11, 1), user, new House("ABCDE87"), 612);
		
		/*Add a created offer that is not added*/
		result = user.addOffer(offer, RegisteredUser.CREATED_OFFER);
		assertEquals(result, true);
		
		/*Add a created offer that is already added*/
		result = user.addOffer(offer, RegisteredUser.CREATED_OFFER);
		assertEquals(result, false);
		
		/*Add a bought offer that is not added*/
		result = user.addOffer(offer, RegisteredUser.HIST_OFFER);
		assertEquals(result, true);
		
		/*Add a bought offer that is already added*/
		result = user.addOffer(offer, RegisteredUser.HIST_OFFER);
		assertEquals(result, false);
		
		/*Remove a created offer that is already added*/
		result = user.removeOffer(offer, RegisteredUser.CREATED_OFFER);
		assertEquals(result, true);
		
		/*Remove a created offer that is not added*/
		result = user.removeOffer(offer2, RegisteredUser.CREATED_OFFER);
		assertEquals(result, false);
		
		/*Remove a bought offer that is already added*/
		result = user.removeOffer(offer, RegisteredUser.HIST_OFFER);
		assertEquals(result, true);
		
		/*Remove a bought offer that is not added*/
		result = user.removeOffer(offer2, RegisteredUser.HIST_OFFER);
		assertEquals(result, false);
		
	}
	
	@Test
	public void testAddHouse() {
		/*Add a house that has not been added previously*/
		assertEquals(user.addHouse(new House("QWERTY")), true);
		
		/*Add a house that has been added previously*/
		assertEquals(user.addHouse(new House("QWERTY")), false);
	}

}
