package Demos;

import House.House;

public class HouseDemo{
	private static House h;

	public static void main(String args[]) {
		System.out.println("This demo test the funcionality of the Comment class.\n");
		System.out.println("We create a House with ID: H9FMHJ7.\n");
		h = new House("H9FMHJ7");
		System.out.println("The created house is: " + h + ".");
		
	
		/*Add a new characteristic*/
		System.out.println("\nWe add the house a new characteristic: Street, Little avenue.\n");
		boolean result = h.addCharacteristic("Street", "Little avenue");
		if(result == true) {
			System.out.println("The characteristic was added successfully.");
			System.out.println("Now the house is: " + h + ".");
		}else {
			System.out.println("There was an error in adding the characteristic.");
		}
		
		/*Add a new value for a characteristic it already has*/
		System.out.println("\nWe modify an existing characteristic of the house: Street, 5th avenue.\n");
		result = h.addCharacteristic("Street", "5th avenue");
		if(result == false) {
			System.out.println("The characteristic was not added as it already exists.");
			System.out.println("The house has not changed: " + h + ".");
		}else {
			System.out.println("There was an error in the method that adds the characteristic.");
		}
		
		/*Remove a characteristic it has*/
		System.out.println("\nWe remove an existing characteristic of the house: Street.\n");
		result = h.removeCharacteristic("Street");
		if(result == true) {
			System.out.println("The characteristic was removed successfully.");
			System.out.println("Now the house is: " + h + ".");
		}else {
			System.out.println("There was an error in removing the characteristic.");
		}
		
		/*Remove a characteristic it does not have*/
		System.out.println("\nWe try to remove a characteristic that the house does not have: ZIP CODE.\n");
		result = h.removeCharacteristic("ZIP CODE");
		if(result == false) {
			System.out.println("The characteristic was not removed as it doesnt exist.");
			System.out.println("Now the house is: " + h + ".");
		}else {
			System.out.println("There was an error in the method that removes the characteristic.");
		}
	
		/*Test if the equals works*/
		System.out.println("\nWe check the functionality of equals() of House without any characteristics.\n");
		if(h.equals(h) == true) {
			System.out.println("A house is equal to itself.");
		}else {
			System.out.println("A house is not equal to itself, error in equals() of House.");
		}
		
		if(h.equals(new House("123")) == false) {
			System.out.println("A house is not equal to another house with a different ID.");
		}else {
			System.out.println("A house is equal to another house with a different ID, error in equals() of House.");
		}
		
		if(h.equals(new House("H9FMHJ7")) == true) {
			System.out.println("A house is equal to another house with the same ID.");
		}else {
			System.out.println("A house is not equal to another house with the same ID, error in equals() of House.");
		}
		
		System.out.println("\n\nEnd of the demo.\n");
	}
}