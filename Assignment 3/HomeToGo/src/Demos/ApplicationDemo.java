package Demos;

import Application.Application;

public class ApplicationDemo {
	public static void main(String args[]) {
		Application app = Application.getInstance();
		
		System.out.println("Los usuarios del sistema son: " + app.getUsers() + ".");
		
	}
}
