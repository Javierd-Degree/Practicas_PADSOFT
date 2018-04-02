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
		assertEquals(admin.getLog(), false);
		admin.changeLog(true);
		assertEquals(admin.getLog(), true);
	}

}
