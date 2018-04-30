package Application;
import java.util.List;

import Date.ModificableDate;
import Exceptions.DateRangeException;
import Exceptions.NotAvailableOfferException;

import java.io.Serializable;

import House.House;

import java.util.ArrayList;

import Offer.*;

import User.RegisteredUser;
import User.UserType;
import views.WindowInterface;

import java.time.LocalDate;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class which implements the whole application, it has a list of administrators,
 * a list of users, a list of offers and a list of houses.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static WindowInterface window;
	
	private List<Administrator> admins;
	private List<RegisteredUser> users;
	private List<Offer> offers;
	private List<House> houses;
	
	private static Application INSTANCE;
	private static final String BACKUP_FILE = "data.obj";

	public static final int HOLIDAY_OFFER = 0;
	public static final int LIVING_OFFER = 1;
	
	public static final int BANNED_USER = -4;
	public static final int SOMEONE_LOGGED = -3;
	public static final int NOT_FOUND_ID = -2;
	public static final int NO_MATCHED = -1;
	public static final int SUCCESS = 1;
		
	
	/**
	 * Constructor of the Application class.
	 */
	private Application() {
		admins = new ArrayList<>();
		users = new ArrayList<>();
		offers = new ArrayList<>();
		houses = new ArrayList<>();	
	}
	
	/**
	 * Method that returns an instance of Application so that there
	 * cannot be 2 instances of application at the same time.
	 * 
	 * @return Instance of Application.
	 */
	public static Application getInstance(){
		if(INSTANCE == null){
			INSTANCE = loadFromFile(BACKUP_FILE);
		}
		return INSTANCE;
	}
	
	/**
	 * Return the list of registered users on the application.
	 * 
	 * @return List of application's RegisteredUsers.
	 */
	public List<RegisteredUser> getUsers(){
		return this.users;
	}
	
	/**
	 * Method that adds an administrator to the array of the system.
	 * @param admin Administrator to be added.
	 * @return boolean true if it was added successfully, false otherwise.
	 */
	public boolean addAdmin(Administrator admin) {
		if(this.admins.contains(admin)) {
			return false;
		}
		this.admins.add(admin);
		return true;
	}
	
	/**
	 * Return the list of administrators on the application.
	 * 
	 * @return List of application's Administrators.
	 */
	public List<Administrator> getAdmins(){
		return this.admins;
	}
	
	/**
	 * Return the list of House on the application.
	 * 
	 * @return List of application's Houses.
	 */
	public List<House> getHouses(){
		return this.houses;
	}
	
	/**
	 * Return the list of Offer on the application.
	 * 
	 * @return List of application's Offers.
	 */
	public List<Offer> getOffers(){
		return this.offers;
	}
	
	/**
	 * Method that logs in a user or an administrator so that they can use the application. 
	 * 
	 * @param id ID of the user or administrator that is going to log in.
	 * @param password password of the user or administrator that is going to log in.
	 * @return int which is 1 if everything is correct, -1 if the password does not match the id, or -2 if the id cannot be found.
	 */
	public int login(String id, String password) {
		/*Once the user or administrator is logged in we load the screen 
		 * for the administrator or the different types of user.*/
		/*Change 1, -1, -2 to MACROS*/
		Object o = this.searchLoggedIn();
		if(o != null) {
			return SOMEONE_LOGGED;
		}
		
		for(Administrator admin : admins) {
			if(admin.getId() == id) {
				if(admin.getPassword().equals(password)) {
					/*Successfully logged in*/
					admin.changeLogged(true);
					return SUCCESS;
				}else {
					return NO_MATCHED;
				}
			}
		}
		for(RegisteredUser user : users) {
			if(user.getId() == id) {
				if(user.getPassword().equals(password)) {
					if(user.getStatus() == RegisteredUser.BANNED) {
						return BANNED_USER;
					}
					
					/*Successfully logged in*/
					user.changeStatus(RegisteredUser.LOGGED);
					return SUCCESS;
				}else {
					return NO_MATCHED;
				}
			}
		}
		return NOT_FOUND_ID;
	}
	
	
	/**
	 * Method that searches for the user or administrator who is logged in.
	 * 
	 * @return Administrator or RegisteredUser logged in casted as Object. 
	 */
	public Object searchLoggedIn() {
		//There is only one administrator or user logged in each time, so we search for him in the arrays.
		for(Administrator admin : admins) {
			if(admin.getLogged()) {
				return admin;
			}
		}
		for(RegisteredUser user : users) {
			if(user.getStatus() == RegisteredUser.LOGGED) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Method that logs out the user or administrator that is currently logged in.
	 */
	public void logout() {
		Object logged = this.searchLoggedIn();
		if(logged == null) {
			/*On some tests we need to ban the user, but we do not have initialized the application,
			 * so we need to check this.*/
			return;
		}
		if(logged instanceof Administrator) {
			((Administrator) logged).changeLogged(false);
		}else {
			((RegisteredUser) logged).changeStatus(RegisteredUser.UNLOGGED);
		}
		if(INSTANCE != null) {
			INSTANCE.saveToFile(BACKUP_FILE);
			INSTANCE = null;
		}
	}
	
	/**
	 * Method that creates a new house and adds it to the application's list, and
	 * into the host's houses list, making sure that the user is not a guest.
	 * 
	 * @param id of the new house to add.
	 * 
	 * @return boolean to check if the house was correctly added or not.
	 */
	public boolean addHouse(String id) {
		Object logged = this.searchLoggedIn();
		if(id != null && logged instanceof RegisteredUser && (
				((RegisteredUser) logged).getType() != UserType.GUEST)) {
			
			House h = new House(id);
			if(houses.contains(h)) {
				return false;
			}
			
			houses.add(h);
			return((RegisteredUser) logged).addHouse(h);
		}
		return false;	
	}
	
	/**
	 * Method that is used by the host to cancel his own offer if it is not reserved or bought yet.
	 *
	 * @param o Offer to cancel.
	 * 
	 * @throws NotAvailableOfferException Exception that indicates that the offer is already reserved or bought, or that the user who tries to cancel it is not the host of the offer.
	 */
	public void cancelOffer(Offer o) throws NotAvailableOfferException {
		Object logged = this.searchLoggedIn();
		if(logged instanceof RegisteredUser &&
				o.getHost().equals(logged)) {
			if(o.getStatus() != Offer.WAITING && o.getStatus() != Offer.TO_CHANGE) {
				throw new NotAvailableOfferException();
			}
			if(offers.contains(o)==true) {
				offers.remove(o);
			}
			RegisteredUser host = o.getHost();
			host.removeOffer(o, RegisteredUser.CREATED_OFFER);
			o.denyOffer();
		}else {
			throw new NotAvailableOfferException();
		}
	}
	
	/**
	 * Method that selects from the offers array the ones that are not approved yet.
	 * 
	 * @return List with the offer that are waiting to be approved.
	 */
	public List<Offer> seeNonApprovedOffer(){
		Object ob = searchLoggedIn();
		if(ob instanceof Administrator) {
			List<Offer> offs = new ArrayList<>();
			for(Offer o : offers) {
				if(o.getStatus() == Offer.WAITING || o.getStatus() == Offer.TO_CHANGE) {
					offs.add(o);
				}
			}
			return offs;
		}else {
			return null;
		}
	}
	
	
	/**
	 * Method that selects from all the users the ones who are banned.
	 * 
	 * @return List of all the banned users.
	 */
	public List<RegisteredUser> seeBannedUsers(){
		Object ob = searchLoggedIn();
		if(ob instanceof Administrator) {
			List<RegisteredUser> banned = new ArrayList<>();
			for(RegisteredUser u : users) {
				if(u.getStatus() == RegisteredUser.BANNED) {
					banned.add(u);
				}
			}
			return banned;
		}else {
			return null;
		}
	}
	
	/**
	 * Method that creates a new holiday offer, and adds it to the offers array,
	 * and to the host created offers list, making sure that the user is not a guest.
	 * 
	 * @param deposit Deposit of the offer.
	 * @param totalPrice Price of the offer (without the deposit).
	 * @param startDate Starting date of the offer.
	 * @param endDate Ending date of the offer.
	 * @param house House in which the guest will stay if he buys the offer.
	 * @param host User that created the offer
	 * 
	 * @return boolean indicating if the offer was successfully added or not.
	 */
	public boolean addOffer(double deposit, double totalPrice, LocalDate startDate, LocalDate endDate, House house, RegisteredUser host) {
		if(house == null || host == null || startDate == null || endDate == null) {
			return false;
		}
		
		if(!host.getCreatedHouses().contains(house)) {
			return false;
		}
		
		if(host.getType() == UserType.GUEST || !host.equals(searchLoggedIn())) {
			return false;
		}
		
		for(Offer o : offers) {
			if(o instanceof HolidayOffer && o.getHouse().equals(house)) {
				return false;
			}
		}
		
		HolidayOffer o;
		try {
			o = new HolidayOffer(deposit, startDate, host, house, endDate, totalPrice);
		} catch (DateRangeException e) {
			System.out.println(e);
			return false;
		}
		
		if(offers.contains(o)) {
			return false;
		}
		
		offers.add(o);
		return host.addOffer(o, RegisteredUser.CREATED_OFFER);
	}
	
	/**
	 * Method that creates a new living offer with the default number of months,
	 * and adds it to the offers array and to the host created offers list,
	 * making sure that the user is not a guest.
	 * 
	 * @param deposit Deposit of the offer.
	 * @param pricePerMonth Price to be paid per month to the host in order to buy the offer.
	 * @param startDate Starting day of the offer.
	 * @param house House in which the guest will stay if he buys the offer.
	 * @param host User that created the offer.
	 * 
	 * @return boolean indicating if the offer was successfully added or not.
	 */
	public boolean addOffer(double deposit, double pricePerMonth, LocalDate startDate, House house, RegisteredUser host) {
		if(house == null || host == null || startDate == null) {
			return false;
		}
		
		if(!host.getCreatedHouses().contains(house)) {
			return false;
		}
		
		if(host.getType() == UserType.GUEST || !host.equals(searchLoggedIn())) {
			return false;
		}
		
		for(Offer o : offers) {
			if(o instanceof LivingOffer && o.getHouse().equals(house)) {
				return false;
			}
		}
		LivingOffer o = new LivingOffer(deposit, startDate, host, house, pricePerMonth);
		
		if(offers.contains(o)) {
			return false;
		}
		
		offers.add(o);
		return host.addOffer(o, RegisteredUser.CREATED_OFFER);
	}
	
	/**
	 * Method that creates a new living offer with a number of months, and adds it to the offers 
	 * array and to the host created offers list, making sure that the user is not a guest.
	 * 
	 * @param deposit Deposit of the offer.
	 * @param pricePerMonth Price to be paid per month to the host in order to buy the offer.
	 * @param startDate Starting day of the offer.
	 * @param house House in which the guest will stay if he buys the offer.
	 * @param host User that created the offer.
	 * @param nMonths number of months the offer has. 
	 * 
	 * @return boolean indicating if the offer was successfully added or not.
	 */
	public boolean addOffer(double deposit, double pricePerMonth, LocalDate startDate, House house, RegisteredUser host, int nMonths) {
		if(house == null || host == null || startDate == null || nMonths <= 0) {
			return false;
		}
		
		if(host.getType() == UserType.GUEST || !host.equals(searchLoggedIn())) {
			return false;
		}
		
		for(Offer o : offers) {
			if(o instanceof LivingOffer && o.getHouse().equals(house)) {
				return false;
			}
		}
		
		LivingOffer o;
		/*As we have checked that nMonths > 0 before, it is not going to happen.*/
		try {
			o = new LivingOffer(deposit, startDate, host, house, pricePerMonth, nMonths);
		} catch (DateRangeException e) {
			return false;
		}
		
		if(offers.contains(o)) {
			return false;
		}
		offers.add(o);
		
		return host.addOffer(o, RegisteredUser.CREATED_OFFER);
	}
	
	/**
	 * Method that selects from all the offers the one with the given ZIP code.
	 * 
	 * @param zip ZIP code of the requested offers.
	 * 
	 * @return List of the offers with the given ZIP code.
	 */
	public List<Offer> searchByZIP(String zip){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			House h = o.getHouse();
			String s = h.getCharacteristics().get("ZIP_CODE");
			if(s != null && s.equals(zip)) {
				offs.add(o);
			}				
		}
		return offs;
	}
	
	/**
	 * Method that selects all the offers that start between 2 given dates.
	 * 
	 * @param startDate Date in which the given date range starts. 
	 * @param endDate Date in which the given date range ends.
	 * 
	 * @return List of the offers between the 2 given dates.
	 */
	public List<Offer> searchByDate(LocalDate startDate, LocalDate endDate){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o instanceof LivingOffer) {
				if(o.getStartDate().isAfter(startDate) && o.getStartDate().isBefore(endDate)) {
					offs.add(o);
				}
			}else if(o instanceof HolidayOffer) {
				if(o.getStartDate().isAfter(startDate) && ((HolidayOffer)o).getEndDate().isBefore(endDate)) {
					offs.add(o);
				}
			}	
		}
		return offs;
	}
	
	/**
	 * Method that selects all the offers with the given type (living or holiday).
	 * 
	 * @param offerType int that indicates the type of offer.
	 * 
	 * @return List of the offers of the given type.
	 */
	public List<Offer> searchByType(int offerType){
		List<Offer> offs = new ArrayList<>();
		if(offerType == HOLIDAY_OFFER) {
			for(Offer o : offers) {
				if(o instanceof HolidayOffer) {
					offs.add(o);
				}
			}
		}
		else {
			for(Offer o : offers) {
				if(o instanceof LivingOffer) {
					offs.add(o);
				}
			}
		}	
		return offs;
	}
	
	/**
	 * Method that selects the offers which have a rating equal or superior to the one given.
	 * 
	 * @param rating Minimum rating the offer has to have in order to be selected.
	 * 
	 * @return List of offers with an equal or superior rating than the one provided.
	 */
	public List<Offer> searchByRating(double rating){
		Object ob = searchLoggedIn();
		if(ob instanceof RegisteredUser && ((RegisteredUser)ob).getType() != UserType.HOST) {
			List<Offer> offs = new ArrayList<>();
			for(Offer o : offers) {
				if(o.calculateRating() >= rating) {
					offs.add(o);
				}
			}
			return offs;
		}else {
			return null;
		}
	}
	
	/**
	 * Method that selects all the offers that are reserved.
	 * 
	 * @return List of the offers that are reserved.
	 */
	public List<Offer> searchReservedOffers(){
		Object ob = searchLoggedIn();
		if(ob instanceof RegisteredUser && ((RegisteredUser)ob).getType() != UserType.HOST) {
			List<Offer> offs = new ArrayList<>();
			for(Offer o : offers) {
				if(o.getStatus() == Offer.RESERVED) {
					offs.add(o);
				}
			}
			return offs;
		}else {
			return null;
		}
	}
	
	/**
	 * Method that selects all the offers that are bought.
	 * 
	 * @return List of the offers that are bought.
	 */
	public List<Offer> searchBoughtOffers(){
		Object ob = searchLoggedIn();
		if(ob instanceof RegisteredUser && ((RegisteredUser)ob).getType() != UserType.HOST) {
			List<Offer> offs = new ArrayList<>();
			for(Offer o : offers) {
				if(o.getStatus() == Offer.BOUGHT) {
					offs.add(o);
				}
			}
			return offs;
		}else {
			return null;
		}
	}
	
	/**
	 * Method that loads all the system from a backup file, if the file does not exist yet 
	 * it loads the first users from the file initial_data.txt.
	 * Once the file is restored, we remove the not valid offers or update them.
	 * For example if the guest reserved the offer but after five days he has not paid it,
	 * the offer is marked as available and removed from the user's reserved/bought offers.
	 * If the offer is not valid and it is a holiday offer, it is removed from the system, 
	 * otherwise, it is marked as denied and stored. 
	 * 
	 * @param file String with the name of the backup file.
	 * 
	 * @return The loaded application
	 */
	private static Application loadFromFile(String file) {
		Application sys = null;
		try { 
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			sys = (Application) ois.readObject();
			ois.close();
			
			List<Offer> toRemoveOffers = new ArrayList<>();
			for(Offer o: sys.offers) {
				if(!o.isValid(ModificableDate.getModifiableDate())) {
					try {
						o.denyOffer();
					} catch (NotAvailableOfferException e) {
						/* As the offer is not valid, this will never
						 * be reached*/
						e.printStackTrace();
					}
					if(o instanceof HolidayOffer) {
						toRemoveOffers.add(o);
						o.getHost().removeOffer(o, RegisteredUser.CREATED_OFFER);
					}
				}
			}
			
			sys.offers.removeAll(toRemoveOffers);
			return sys;

		} catch (FileNotFoundException e) {
			String data, file2 = "initial_data.txt";
			try {
				FileReader f = new FileReader(file2);
				BufferedReader b = new BufferedReader(f);
				sys = new Application();
				b.readLine();
				while((data = b.readLine())!=null) {
					String[] tokens = data.split(";");
					String[] name = tokens[2].split(",");
					UserType type;
					if(tokens[0].equals("O")){
						type = UserType.HOST;
					}else if(tokens[0].equals("D")){
						type = UserType.GUEST;
					}else{
						type = UserType.BOTH;
					}
					sys.users.add(new RegisteredUser(tokens[1], name[1], name[0], tokens[4], tokens[3], type));
				}
				b.close();
				return sys;
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return sys;
	}
		
	/**
	 * Method that saves all the application's data into a backup file.
	 * @param file String with the name of the backup file.
	 */
	private void saveToFile(String file) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( new FileOutputStream(file));
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method that allows us to change the Application
	 * window reference.
	 * 
	 * @return the current Application's WindowInterface.
	 */
	public static WindowInterface getWindow() {
		return Application.window;
	}
	
	/**
	 * Method that allows us to change the Application
	 * window reference.
	 * @param window New WindowInterface.
	 */
	public static void setWindow(WindowInterface window) {
		Application.window = window;
	}
}


