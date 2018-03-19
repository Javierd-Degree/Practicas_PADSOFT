import java.util.List;

import House.Characteristic;
import House.House;

import java.util.ArrayList;
import Offer.*;
import User.RegisteredUser;

public class System {
	private List<Administrator> admins;
	private List<RegisteredUser> guests;
	private List<RegisteredUser> hosts;
	private List<RegisteredUser> both;
	private List<Offer> offers;
	private List<House> houses;
	
	private System() {
		admins = new ArrayList<>();
		guests = new ArrayList<>();
		hosts = new ArrayList<>();
		both = new ArrayList<>();
		offers = new ArrayList<>();
		houses = new ArrayList<>();
	}
	
	/*TODO Hacer funciones para cargar los datos de fichero*/
	
	public int login(int id, String password) {
		/*Change 1, -1, -2 to MACROS*/
		for(RegisteredUser user: guests) {
			if(user.getId() == id) {
				if(user.getPassword().equals(password)) {
					/*Successfully logged in*/
					user.changeStatus(RegisteredUser.LOGGED);
					return 1;
				}else {
					return -1;
				}
			}
		}
		
		for(RegisteredUser user: hosts) {
			if(user.getId() == id) {
				if(user.getPassword().equals(password)) {
					/*Successfully logged in*/
					user.changeStatus(RegisteredUser.LOGGED);
					return 1;
				}else {
					return -1;
				}
			}
		}
		
		for(RegisteredUser user: both) {
			if(user.getId() == id) {
				if(user.getPassword().equals(password)) {
					/*Successfully logged in*/
					user.changeStatus(RegisteredUser.LOGGED);
					return 1;
				}else {
					return -1;
				}
			}
		}
		return -2;
	}
	
	public void logout(RegisteredUser user) {
		user.changeStatus(RegisteredUser.UNLOGGED);
	}
	
	public void addHouse(String id, List<Characteristic> chars) {
		House h = new House(id, chars);
		houses.add(h);
	}
	
	/*TODO Ver como llamar a esta funcion cuando pasan los cinco dias.*/
	public void cancelOffer(Offer o) {
		/*remove it from the system array*/
		for(int i = 0; i < offers.size(); i++) {
			/*TODO Cambiar por un equals¿?*/
			if(offers.get(i) == o) {
				offers.remove(i);
			}
		}
		
		/*TODO Eliminar de los arrays*/
		RegisteredUser host = o.getHost();
		RegisteredUser guest = o.getGuest();
		
	}
}
