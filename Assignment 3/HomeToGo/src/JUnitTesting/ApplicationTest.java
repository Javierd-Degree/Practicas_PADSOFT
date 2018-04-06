package JUnitTesting;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import Application.Administrator;
import Application.Application;
import Date.ModificableDate;
import Exceptions.NotAvailableOfferException;
import Offer.Offer;
import User.RegisteredUser;
import User.UserType;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.OrderRejectedException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {
	Application app;
	
	@Before
	public void before() {
		if(app == null) {
			app = Application.getInstance();
			ModificableDate.setToday();
		}else {
			app.logout();
			app = Application.getInstance();
		}
	}
	
	@Test
	public void testA_Login() {
		/*Try to login with a user that does not exist*/
		assertEquals(app.login("abcd", "abcde"), Application.NOT_FOUND_ID);
		/*Try to get the logged user (none)*/
		assertEquals(app.searchLoggedIn(), null);
		
		/*Login with an existing user and incorrect password*/
		RegisteredUser user = app.getUsers().get(1);
		assertEquals(app.login(user.getId(), user.getPassword()+"H"), Application.NO_MATCHED);
		/*Try to get the logged user (none)*/
		assertEquals(app.searchLoggedIn(), null);
		
		/*Login with an existing user and correct password*/
		assertEquals(app.login(user.getId(), user.getPassword()), Application.SUCCESS);
		/*Try to get the logged user (user)*/
		assertEquals(app.searchLoggedIn(), user);
		
		/*Try to log with another user*/
		RegisteredUser user2 = app.getUsers().get(0);
		assertEquals(app.login(user2.getId(), user2.getPassword()), Application.SOMEONE_LOGGED);
		/*Try to get the logged user (user)*/
		assertEquals(app.searchLoggedIn(), user);
		
		app.logout();
	}
	
	@Test
	public void testB_LoginAdmin() {
		/*Add a new administrator that is not included.*/
		Administrator admin = new Administrator("001", "Pedro", "Fernandez", "pepe123");
		assertEquals(app.addAdmin(admin), true);

		/*Try to add an administrator that is already added.*/
		assertEquals(app.addAdmin(admin), false);
		
		/*Log with the administrator.*/
		app.logout();
		before();
		assertEquals(app.login(admin.getId(), admin.getPassword()), Application.SUCCESS);
		assertEquals(app.searchLoggedIn(), admin);
		
		/*Try to login with a different administrator/user*/
		RegisteredUser user2 = app.getUsers().get(0);
		assertEquals(app.login(user2.getId(), user2.getPassword()), Application.SOMEONE_LOGGED);
		/*Try to get the logged user (user)*/
		assertEquals(app.searchLoggedIn(), admin);
		app.logout();
	}
	
	@Test 
	public void testC_AddHouse() {
		/*Check that there are no houses (This will
		 * just work the first time we run the test).*/
		assertEquals(app.getHouses().size(), 0);
		
		/*Try to add a house with a registered user that is a guest.*/
		RegisteredUser user = app.getUsers().get(1);
		assertEquals(user.getType(), UserType.GUEST);
		app.login(user.getId(), user.getPassword());
		assertEquals(app.addHouse("ABCD"), false);
		app.logout();
		
		/*Use a registered user which is a host*/
		before();
		user = app.getUsers().get(0);
		assertEquals(user.getType(), UserType.HOST);
		app.login(user.getId(), user.getPassword());
		/*Add a new house to the system*/
		assertEquals(app.addHouse("ABCD"), true);
		/*Try to add the same house again (the same id)*/
		assertEquals(app.addHouse("ABCD"), false);
		/*Try to add a null house*/
		assertEquals(app.addHouse(null), false);
		/*Add a new house to the system*/
		assertEquals(app.addHouse("EFGH"), true);
		
		/*Modify the offers (This would be done easily 
		 * on the graphic user interface*/
		user.getCreatedHouses().get(0).addCharacteristic("ZIP_CODE", "E322");
		user.getCreatedHouses().get(1).addCharacteristic("ZIP_CODE", "E333");
		 
		/*Use a registered user which is a host and a guest*/
		app.logout();
		before();
		assertEquals(app.getHouses().size(), 2);
		user = app.getUsers().get(3);
		assertEquals(user.getType(), UserType.BOTH);
		app.login(user.getId(), user.getPassword());
		
		/*Add a new house to the system*/
		assertEquals(app.addHouse("QWERTY"), true);
		user.getCreatedHouses().get(0).addCharacteristic("ZIP_CODE", "3AM");
		
		/*Logout and open the app again to check that 
		 * the houses have been saved*/
		app.logout();
		before();
		assertEquals(app.getHouses().size(), 3);
		/*Check that those offers have just one characteristic*/
		assertEquals(app.getHouses().get(0).getCharacteristics().size(), 1);
		assertEquals(app.getHouses().get(1).getCharacteristics().size(), 1);
		/*The second third user has just created one house*/
		assertEquals(user.getCreatedHouses().size(), 1);
		app.logout();
	}
	
	@Test 
	public void testD_AddOffer() {
		/*Try to add an offer with no user logged in (the first user of the list is a host).*/
		assertEquals(app.addOffer(100, 400, LocalDate.of(2018, 7, 13), app.getHouses().get(0), app.getUsers().get(0)), false);
		
		/*Try to add an offer with a registered user that is a guest.*/
		RegisteredUser user = app.getUsers().get(1);
		assertEquals(user.getType(), UserType.GUEST);
		app.login(user.getId(), user.getPassword());
		assertEquals(app.addOffer(100, 400, LocalDate.of(2018, 7, 13), app.getHouses().get(0), app.getUsers().get(0)), false);
		app.logout();
		
		/*Try to add a house with some parameter null*/
		before();
		user = app.getUsers().get(0);
		assertEquals(user.getType(), UserType.HOST);
		app.login(user.getId(), user.getPassword());
		assertEquals(app.getHouses().get(0), user.getCreatedHouses().get(0));
		assertEquals(app.addOffer(100, 400, LocalDate.of(2018, 7, 13), app.getHouses().get(0), null), false);
		
		/*Try to add an offer which is not from the logged user (The third user is host and guest).*/
		assertEquals(app.addOffer(100, 400, LocalDate.of(2018, 7, 13), app.getHouses().get(0), app.getUsers().get(2)), false);
	
		/*Check that for now the system has not offers*/
		assertEquals(app.getOffers().size(), 0);
		
		/*Add and ask for changes on an offer*/
		assertEquals(app.addOffer(100, 400, LocalDate.of(2018, 7, 13), LocalDate.of(2018, 10, 21), app.getHouses().get(0), user), true);
		try {
			app.getOffers().get(0).askForChanges("Changes");
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		
		assertEquals(app.getOffers().get(0).getStatus(), Offer.TO_CHANGE);
		
		/*Add an offer and buy it*/
		assertEquals(app.addOffer(100, 300, LocalDate.of(2018, 10, 23), LocalDate.of(2018, 10, 28), app.getHouses().get(1), user), true);
		try {
			app.getOffers().get(1).approveOffer();
			app.getOffers().get(1).buyOffer(app.getUsers().get(2), "Buy offer");
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			e.printStackTrace();
		}
		
		assertEquals(app.getOffers().get(1).getStatus(), Offer.BOUGHT);
		assertEquals(app.getOffers().get(1).getGuest(), app.getUsers().get(2));
		app.logout();
		
		/*Check that now we have two offers*/
		before();
		user = app.getUsers().get(3);
		assertEquals(user.getType(), UserType.BOTH);
		app.login(user.getId(), user.getPassword());
		assertEquals(app.getOffers().size(), 2);
		
		/*Create a new offer and let a guest user reserve it.*/
		assertEquals(app.addOffer(150, 300, LocalDate.of(2018, 7, 15), app.getHouses().get(2), user), true);
		try {
			app.getOffers().get(2).approveOffer();
			app.getOffers().get(2).reserveOffer(app.getUsers().get(2));
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		
		assertEquals(app.getOffers().get(2).getStatus(), Offer.RESERVED);
		assertEquals(app.getOffers().get(2).getGuest(), app.getUsers().get(2));
		
		/*Close the application in order to save the data an check we now have three
		 * offers.*/
		app.logout();
		before();
		user = app.getUsers().get(3);
		assertEquals(user.getType(), UserType.BOTH);
		app.login(user.getId(), user.getPassword());
		assertEquals(app.getOffers().size(), 3);
		
		/*Close the application. Wait six days so that now the first offer 
		 * is not valid and it is denied and deleted (because it is a 
		 * holiday offer, and the user did not made the changes).
		 * The third offer should now be available, as the user
		 * who reserved it has not paid.*/
		app.logout();
		ModificableDate.setToday();
		ModificableDate.plusDays(6);
		before();
		user = app.getUsers().get(3);
		assertEquals(user.getType(), UserType.BOTH);
		app.login(user.getId(), user.getPassword());
		assertEquals(app.getOffers().size(), 2);
		
		/*Test that the offers are the same we created previously*/
		assertEquals(app.getOffers().get(0).getHouse(), app.getHouses().get(1));
		assertEquals(app.getOffers().get(0).getHost() , app.getUsers().get(0));
		assertEquals(app.getOffers().get(1).getHouse(), app.getHouses().get(2));
		assertEquals(app.getOffers().get(1).getHost() , user);
		assertEquals(app.getOffers().get(1).getStatus(), Offer.AVAILABLE);
		assertEquals(app.getOffers().get(1).getGuest() , null);
		
		/*Check that the second offer is still bought.*/
		assertEquals(app.getOffers().get(0).getHouse(), app.getHouses().get(1));
		assertEquals(app.getOffers().get(0).getHost() , app.getUsers().get(0));
		assertEquals(app.getOffers().get(0).getStatus(), Offer.BOUGHT);
		assertEquals(app.getOffers().get(0).getGuest(), app.getUsers().get(2));
		
		app.logout();
	}
	
	
	@Test
	public void testE_Search() {
		/*We need to log in as a guest*/
		RegisteredUser user = app.getUsers().get(1);
		assertEquals(user.getType(), UserType.GUEST);
		app.login(user.getId(), user.getPassword());
		
		/*Search offer by ZIP*/
		assertEquals(app.searchByZIP("NO HAY").size(), 0);
		/*We have created and added theses houses to offers previously.*/
		assertEquals(app.searchByZIP("E333").size(), 1);
		
		/*Search by type
		 * We have one HolidayOffer and one LivingOffer*/
		assertEquals(app.searchByType(Application.HOLIDAY_OFFER).size(), 1);
		assertEquals(app.searchByType(Application.LIVING_OFFER).size(), 1);
		
		/*Search by date*/
			/*Both offers fit*/
		assertEquals(app.searchByDate(LocalDate.now(), LocalDate.of(2019, 1, 30)).size(), 2);
			/*Just the LivingOffer fits*/
		assertEquals(app.searchByDate(LocalDate.now(), LocalDate.of(2018, 8, 30)).size(), 1);
			/*Just the HolidayOffer fits*/
		assertEquals(app.searchByDate(LocalDate.of(2018, 10, 20), LocalDate.of(2018, 11, 30)).size(), 1);
			/*No one fits*/
		assertEquals(app.searchByDate(LocalDate.now(), LocalDate.now().plusDays(2)).size(), 0);
		
		
		/*Search by rating*/
		assertEquals(app.searchByRating(0).size(), 2);
		assertEquals(app.searchByRating(2).size(), 0);
		/*Comment on an offer and search again*/
		app.getOffers().get(1).postComment(user, 4);
		assertEquals(app.searchByRating(2).size(), 1);
		
		app.logout();
	}
	
	@Test 
	public void testF_SeeBannedUser() {
		/*If we are not logged as an administrator*/
		assertEquals(app.seeBannedUsers(), null);
		
		/*Login as an administrator*/
		assertEquals(app.login(app.getAdmins().get(0).getId(), app.getAdmins().get(0).getPassword()), Application.SUCCESS);
		assertEquals(app.searchLoggedIn(), app.getAdmins().get(0));
		
		/*Now we do not have any banned user.*/
		assertEquals(app.seeBannedUsers().size(), 0);
		/*Let's buy an offer having an invalid credit card 
		 * (The last user has an invalid credit card)
		 * The host has also an invalid credit card, so both should be banned. */
		app.logout();
		before();
		RegisteredUser user = app.getUsers().get(app.getUsers().size() - 1);
		app.login(user.getId(), user.getPassword());
		try {
			app.getOffers().get(1).buyOffer(user, "Buy offer");
		} catch (NotAvailableOfferException | OrderRejectedException e) {
			e.printStackTrace();
		}
		
		assertEquals(user.getStatus(), RegisteredUser.BANNED);
		assertEquals(app.getOffers().get(1).getHost().getStatus(), RegisteredUser.BANNED);
		/*As the logged user is banned, he is logged out, and now we need to log in as an admin*/
		app.logout();
		before();
		assertEquals(app.login(app.getAdmins().get(0).getId(), app.getAdmins().get(0).getPassword()), Application.SUCCESS);
		assertEquals(app.searchLoggedIn(), app.getAdmins().get(0));
		System.out.println(app.seeBannedUsers());
		assertEquals(app.seeBannedUsers().size(), 2);
		app.logout();
	}
	
	@Test 
	public void testG_SeeNonApprovedOffers() {
		/*If we are not logged as an administrator*/
		app.logout();
		assertEquals(app.seeNonApprovedOffer(), null);
		
		/*Login as an administrator*/
		assertEquals(app.login(app.getAdmins().get(0).getId(), app.getAdmins().get(0).getPassword()), Application.SUCCESS);
		assertEquals(app.searchLoggedIn(), app.getAdmins().get(0));
		
		/*Now we do not have any non approved offer.*/
		assertEquals(app.seeNonApprovedOffer().size(), 0);
		/*Let's create a new offer*/
		app.logout();
		before();
		RegisteredUser user = app.getUsers().get(0);
		assertEquals(user.getType(), UserType.HOST);
		app.login(user.getId(), user.getPassword());
		
		assertEquals(app.addOffer(100, 400, LocalDate.of(2018, 7, 13), LocalDate.of(2018, 10, 21), app.getHouses().get(0), user), true);
		
		/*Now we need to log in as an admin in order to test the function*/
		app.logout();
		before();
		assertEquals(app.login(app.getAdmins().get(0).getId(), app.getAdmins().get(0).getPassword()), Application.SUCCESS);
		assertEquals(app.searchLoggedIn(), app.getAdmins().get(0));
		assertEquals(app.seeNonApprovedOffer().size(), 1);
		
		/*Let's approve the offer now*/
		try {
			app.getOffers().get(app.getOffers().size() - 1).approveOffer();
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		assertEquals(app.getOffers().get(app.getOffers().size() - 1).getStatus(), Offer.AVAILABLE);
		assertEquals(app.seeNonApprovedOffer().size(), 0);
		app.logout();
	}
	
	@Test
	public void testH_CancelOffer() {
		/*Create a new offer that can be canceled*/
		app.logout();
		RegisteredUser user = app.getUsers().get(5);
		assertEquals(user.getType(), UserType.HOST);
		assertEquals(app.login(user.getId(), user.getPassword()), Application.SUCCESS);
		
		app.addHouse("HJK");
		assertEquals(app.addOffer(110, 420, LocalDate.of(2018, 7, 13), LocalDate.of(2018, 10, 21), 
				app.getHouses().get(app.getHouses().size() - 1), user), true);
		Offer o = app.getOffers().get(app.getOffers().size() - 1);
		
		/*Try to cancel an offer without being logged in.*/
		app.logout();
		before();
		try {
			app.cancelOffer(o);
			fail("The user is not logged");
		} catch (NotAvailableOfferException e) {}
		
		/*Log with another user and try to cancel the offer*/
		assertEquals(app.login(app.getUsers().get(0).getId(), app.getUsers().get(0).getPassword()), Application.SUCCESS);
		try {
			app.cancelOffer(o);
			fail("The user is not the owner of the offer");
		} catch (NotAvailableOfferException e) {}
		
		app.logout();
		before();
		/*Log with the offer owner and see how he can cancel it*/
		user = app.getUsers().get(5);
		assertEquals(user.getType(), UserType.HOST);
		assertEquals(app.login(user.getId(), user.getPassword()), Application.SUCCESS);
		try {
			app.cancelOffer(o);
		} catch (NotAvailableOfferException e) {
			e.printStackTrace();
		}
		assertEquals(o.getStatus(), Offer.DENIED);
	}
}
