package JUnitTesting;

import House.House;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class HouseTest{
	private static House h;

	@Before
	public void before() {
		h = new House("H9FMHJ7");
	}
	
	@Test
	public void testAddRemoveCharacteristic(){
		boolean result;
		/*Add a new characteristic*/
		result = h.addCharacteristic("Street", "Little avenue");
		assertEquals(result, true);
		
		/*Add a new value for a characteristic it already has*/
		result = h.addCharacteristic("Street", "5th avenue");
		assertEquals(result, false);
		
		/*Remove a characteristic it has*/
		result = h.removeCharacteristic("Street");
		assertEquals(result, true);
		
		/*Remove a characteristic it does not have*/
		result = h.removeCharacteristic("ZIP CODE");
		assertEquals(result, false);
	}
	
	@Test
	public void testEquals(){
		/*Test if the equals works*/
		assertEquals(h.equals(h), true);
		assertEquals(h.equals(new House("123")), false);
		assertEquals(h.equals(new House("H9FMHJ7")), true);
	}
}
