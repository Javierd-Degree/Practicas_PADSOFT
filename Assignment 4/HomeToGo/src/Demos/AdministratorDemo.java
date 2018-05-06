package Demos;

import Application.Administrator;

public class AdministratorDemo {
	private static Administrator admin;
	
	public static void main(String[] args) {
		System.out.println("This demo checks the functionality of the Administrator class.\n");
		System.out.println("To start with, we initialise a new administrator with data: 0000, Pedro, J. Ramirez, Hello world 1234.\n");
		admin = new Administrator("0000", "Pedro", "J. Ramirez", "Hello world 1234");
		System.out.println("The new administrator is: " + admin + ".");
	
		/*Test the functions that change the administrator status.*/
		System.out.println("\nIn this case we only have to test the method that changes the admin's logged field in order to see if it can change from unlogged to logged correctly and the equals method.\n");
		if(admin.getLogged() == false) {
			System.out.println("The administrator " + admin + " is currently unlogged.");
		}else {
			System.out.println("The administrator " + admin + " was initialised as logged, error.");
		}
		System.out.println("\nWe change the logged field of the administrator so that it is logged in.\n");	
		admin.changeLogged(true);
		if(admin.getLogged() == true) {
			System.out.println("The admin " + admin + " is now logged in.");
		}else {
			System.out.println("The logged field did not change, error in changeLogged.");			
		}
		
		System.out.println("\nNow we check the functionality of the equals method.\n");
		System.out.println("We create another administrator with data: 0001, Maria, Martinez Perez, Godbye world 1234\n");
		Administrator admin2 = new Administrator("0001", "Maria", "Martinez Perez", "Godbye world 1234");
		System.out.println("The new administrator is: " + admin2 + ".\n");
		
		if(admin.equals(admin2)) {
			System.out.println("The 2 administrators are equal, error in equals.");
		}else {
			System.out.println("The 2 administrators are different as expected as they have different id.");
		}
		
		if(admin.equals(admin)) {
			System.out.println("An administrator is equal to itself.");
		}else {
			System.out.println("An administrator is not equal to itself, error in equals.");
		}
		
		if(admin.equals(null)) {
			System.out.println("An administrator equals null, error in equals.");
		}else {
			System.out.println("An administrator is not equal to null.");
			
		}if(admin.equals(new Administrator("0000", "Pedro", "J. Ramirez", "Hello world 1234"))) {
			System.out.println("An administrator equals another administrator with the same id.");
		}else {
			System.out.println("An administrator is not equal to another administrator with the same id, error in equals.");
		}
		System.out.println("\n\nEnd of the demo.\n");
	}	
}

