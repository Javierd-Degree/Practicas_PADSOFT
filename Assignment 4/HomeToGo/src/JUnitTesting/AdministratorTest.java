package JUnitTesting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Application.Administrator;

public class AdministratorTest {
	Administrator admin;
	
	@Before
	public void before() {
		admin = new Administrator("0000", "Pedro", "J. Ramirez", "Hello world 1234");
	}
	
	@Test
	public void testLogin() {
		/*Test the functions that change the administrator status.*/
		assertEquals(admin.getLogged(), false);
		admin.changeLogged(true);
		assertEquals(admin.getLogged(), true);
	}
	
	@Test
	public void testEquals() {
		/*Test the Administrator equals method*/
		assertEquals(admin.equals(admin), true);
		/*Check that two admins are the same if they have the same id.*/
		assertEquals(admin.equals(new Administrator("0000", "Juan", "Mismo id", "Hello world 1234")), true);
		assertEquals(admin.equals(new Administrator("0001", "Pedro", "J. Ramirez", "Hello world 1234")), false);
	}

}
