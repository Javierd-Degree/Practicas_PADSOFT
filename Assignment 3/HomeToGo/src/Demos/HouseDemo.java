package Demos;

import House.House;

public class HouseDemo{
	private static House h;

	public static void main(String args[]) {
		System.out.println("This demo test the funcionality of the Comment class.");
		System.out.println("We create a House with ID: H9FMHJ7.");
		h = new House("H9FMHJ7");
		System.out.println("The created house is: " + h + ".");
		
	
		/*Add a new characteristic*/
		System.out.println("We add the house a new characteristic: Street, Little avenue.");
		boolean result = h.addCharacteristic("Street", "Little avenue");
		if(result == true) {
			System.out.println("The characteristic was added successfully.");
			System.out.println("Now the house is: " + h + ".");
		}else {
			System.out.println("There was an error in adding the characteristic.");
		}
		
		/*Add a new value for a characteristic it already has*/
		System.out.println("We modify an existing characteristic of the house: Street, 5th avenue.");
		result = h.addCharacteristic("Street", "5th avenue");
		if(result == false) {
			System.out.println("The characteristic was not added as it already exists.");
			System.out.println("The house has not changed: " + h + ".");
		}else {
			System.out.println("There was an error in the method that adds the characteristic.");
		}
		
		/*Remove a characteristic it has*/
		System.out.println("We remove an existing characteristic of the house: Street.");
		result = h.removeCharacteristic("Street");
		if(result == true) {
			System.out.println("The characteristic was removed successfully.");
			System.out.println("Now the house is: " + h + ".");
		}else {
			System.out.println("There was an error in removing the characteristic.");
		}
		
		/*Remove a characteristic it does not have*/
		System.out.println("We try to remove a characteristic that the house does not have: ZIP CODE.");
		result = h.removeCharacteristic("ZIP CODE");
		if(result == false) {
			System.out.println("The characteristic was not removed as it doesnt exist.");
			System.out.println("Now the house is: " + h + ".");
		}else {
			System.out.println("There was an error in the method that removes the characteristic.");
		}
	
		/*Test if the equals works*/
		System.out.println("We check the functionalitu of equals() of House without any characteristics.");
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
		
		/*Test if the equals works with characteristics.*/
		System.out.println("We check the functionalitu of equals() of House with characteristics.");
		h.addCharacteristic("ZIP CODE", "28041");
		House h2 = new House("H9FMHJ7");
		if(h.equals(h2) == false) {
			System.out.println("A house is not equal to another house with the same ID but different characteristics.");
		}else {
			System.out.println("A house is equal to another house with the same ID but different characteristics, error in equals() of House.");
		}
		
		h2.addCharacteristic("ZIP CODE", "28041");
		if(h.equals(h2) == true) {
			System.out.println("A house is equal to another house with the same ID and characteristics.");
		}else {
			System.out.println("A house is not equal to another house with the same ID and characteristics, error in equals() of House.");
		}
		
		h.addCharacteristic("Street", "Little avenue");
		h2.addCharacteristic("Street", "Big avenue");
		if(h.equals(h2) == false) {
			System.out.println("A house is not equal to another house with the same ID and first characteristic, but different second characteristic.");
		}else {
			System.out.println("A house is not equal to another house with the same ID and first characteristic, but different second characteristic, error in equals() of House.");
		}
	}
}