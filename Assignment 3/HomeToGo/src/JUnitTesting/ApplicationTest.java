package JUnitTesting;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import Application.Administrator;
import Application.Application;
import User.RegisteredUser;
import User.UserType;

public class ApplicationTest {
	Application app;
	
	@Before
	public void before() {
		app = Application.getInstance();
	}
	
	@Test
	public void testLogin() {
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
	public void testLoginAdmin() {
		/*Add a new administrator that is not included.*/
		Administrator admin = new Administrator("001", "Pedro", "Fernandez", "pepe123");
		assertEquals(app.addAdmin(admin), true);

		/*Try to add an administrator that is already added.*/
		assertEquals(app.addAdmin(admin), false);
		
		/*Log with the administrator.*/
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
	public void testAddHouse() {
		/*Check that there are no houses (This will
		 * just work the first time we run the test).*/
		assertEquals(app.getHouses().size(), 0);
		
		RegisteredUser user = app.getUsers().get(0);
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
		 
		
		/*Logout and open the app again to check that 
		 * the houses have been saved*/
		app.logout();
		before();
		assertEquals(app.getHouses().size(), 2);
		/*Check that those offers have just one characteristic*/
		assertEquals(app.getHouses().get(0).getCharacteristics().size(), 1);
		assertEquals(app.getHouses().get(1).getCharacteristics().size(), 1);
		assertEquals(user.getCreatedHouses().size(), 2);
		app.logout();
	}
	
	@Test 
	public void testAddOffer() {
		/*Try to add a house with some parameter null*/
		assertEquals(app.addOffer(100, 400, LocalDate.of(2018, 7, 13), app.getHouses().get(0), null), false);
	}
}
