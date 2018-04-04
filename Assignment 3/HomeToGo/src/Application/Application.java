package Application;
import java.util.List;

import Exceptions.DateRangeException;
import Exceptions.NotAvailableOfferException;

import java.io.Serializable;

import House.House;

import java.util.ArrayList;

import Offer.*;

import User.RegisteredUser;
import User.UserType;

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
	private List<Administrator> admins;
	private List<RegisteredUser> users;
	private List<Offer> offers;
	private List<House> houses;
	
	private static Application INSTANCE;
	private static final String BACKUP_FILE = "data.obj";

	public static final int HOLIDAY_OFFER = 0;
	public static final int LIVING_OFFER = 1;
	
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
	 * @return Instance of Application.
	 */
	public static Application getInstance(){
		if(INSTANCE == null){
			INSTANCE = loadFromFile(BACKUP_FILE);
		}
		return INSTANCE;
	}
	
	/**
	 * Method that adds a user to the array of the system.
	 */
	public void addUser(RegisteredUser user) {
		this.users.add(user);
	}
	
	public List<RegisteredUser> getUsers(){
		return this.users;
	}
	
	/**
	 * Method that adds an administrator to the array of the system.
	 */
	public void addAdmin(Administrator admin) {
		this.admins.add(admin);
	}
	
	public List<Administrator> getAdmins(){
		return this.admins;
	}
	
	public List<House> getHouses(){
		return this.houses;
	}
	
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
		for(Administrator admin : admins) {
			if(admin.getId() == id) {
				if(admin.getPassword().equals(password)) {
					/*Successfully logged in*/
					admin.changeLogged(true);
					return 1;
				}else {
					return -1;
				}
			}
		}
		for(RegisteredUser user : users) {
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
	
	
	/**
	 * Method that searches for the user or administrator who is logged in.
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
		if(logged instanceof Administrator) {
			((Administrator) logged).changeLogged(false);
		}else {
			((RegisteredUser) logged).changeStatus(RegisteredUser.UNLOGGED);
		}
		INSTANCE.saveToFile(BACKUP_FILE);
		INSTANCE = null;
	}
	
	/**
	 * Method that creates a new house and adds it to the application's list.
	 * @param id of the new house to add.
	 * @return boolean to check if the house was correctly added or not.
	 */
	public boolean addHouse(String id) {
		Object logged = this.searchLoggedIn();
		if(logged instanceof RegisteredUser && (
				((RegisteredUser) logged).getType() != UserType.GUEST)) {
			House h = new House(id);
			houses.add(h);
			return((RegisteredUser) logged).addHouse(h);
		}
		return false;	
	}
	
	/**
	 * Method that is used by the host to cancel his own offer if it is not reserved or bought yet.
	 * @param o Offer to cancel.
	 * @throws NotAvailableOfferException Exception that indicates that the offer is already reserved or bought, or that the user who tries to cancel it is not the host of the offer.
	 */
	public void cancelOffer(Offer o) throws NotAvailableOfferException {
		Object logged = this.searchLoggedIn();
		if(logged instanceof RegisteredUser &&
				((RegisteredUser) logged) == o.getHost()) {
			if(o.getStatus() == Offer.RESERVED || o.getStatus() == Offer.BOUGHT) {
				throw new NotAvailableOfferException();
			}
			if(offers.contains(o)==true) {
				offers.remove(o);
			}
			RegisteredUser host = o.getHost();
			host.removeOffer(o, RegisteredUser.CREATED_OFFER);
		}else {
			throw new NotAvailableOfferException();
		}
	}
	
	/**
	 * Method that selects from the offers array the ones that are not approved yet.
	 * @return List with the offer that are waiting to be approved.
	 */
	public List<Offer> seeNonApprovedOffer(){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.getStatus() == Offer.WAITING) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	/**
	 * Method that adds a new holiday offer to the offers array.
	 * @param deposit Deposit of the offer.
	 * @param totalPrice Price of the offer (without the deposit).
	 * @param startDate Starting date of the offer.
	 * @param endDate Ending date of the offer.
	 * @param house House in which the guest will stay if he buys the offer.
	 * @param host User that created the offer
	 * @return boolean indicating if the offer was successfully added or not.
	 */
	public boolean addOffer(double deposit, double totalPrice, LocalDate startDate, LocalDate endDate, House house, RegisteredUser host) {
		if(house == null || host == null || startDate == null || endDate == null) {
			return false;
		}
		for(Offer o : offers) {
			if(o instanceof HolidayOffer && o.getHouse() == house) {
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
		offers.add(o);
		return true;
	}
	
	/**
	 * Method that adds a new living offer to the offers array.
	 * @param deposit Deposit of the offer.
	 * @param pricePerMonth Price to be paid per month to the host in order to buy the offer.
	 * @param startDate Starting day of the offer.
	 * @param house House in which the guest will stay if he buys the offer.
	 * @param host User that created the offer.
	 * @return boolean indicating if the offer was successfully added or not.
	 */
	public boolean addOffer(double deposit, double pricePerMonth, LocalDate startDate, House house, RegisteredUser host) {
		if(house == null || host == null || startDate == null) {
			return false;
		}
		for(Offer o : offers) {
			if(o instanceof LivingOffer && o.getHouse() == house) {
				return false;
			}
		}
		LivingOffer o = new LivingOffer(deposit, startDate, host, house, pricePerMonth);
		offers.add(o);
		return true;
	}
	
	/**
	 * Method that selects from all the offers the one with the given ZIP code.
	 * @param zip ZIP code of the requested offers.
	 * @return List of the offers with the given ZIP code.
	 */
	public List<Offer> searchByZIP(String zip){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			House h = o.getHouse();
			String s = h.getCharacteristics().get("ZIP_CODE");
			if(s.equals(zip)) {
				offs.add(o);
			}				
		}
		return offs;
	}
	
	/**
	 * Method that selects all the offers that start between 2 given dates.
	 * @param startDate Date in which the given date range starts. 
	 * @param endDate Date in which the given date range ends.
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
	 * @param offerType Int that indicates the type of offer.
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
	 * @param rating Minimum rating the offer has to have in order to be selected.
	 * @return List of offers with an equal or superior rating than the one provided.
	 */
	public List<Offer> searchByRating(double rating){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.calculateRating() >= rating) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	/**
	 * Method that selects all the offers that are reserved.
	 * @return List of the offers that are reserved.
	 */
	public List<Offer> searchReservedOffers(){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.getStatus() == Offer.RESERVED) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	/**
	 * Method that selects all the offers that are bought.
	 * @return List of the offers that are bought.
	 */
	public List<Offer> searchBoughtOffers(){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.getStatus() == Offer.BOUGHT) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	/**
	 * Method that loads all the system from a backup file, if the file does not exist yet 
	 * it loads the first users from the file initial_data.txt.
	 * @param file String with the name of the backup file.
	 * @return The loaded application
	 */
	public static Application loadFromFile(String file) {
		Application sys = null;
		try { 
			//TODO Quitar ofertas ya eliminadas, que no han pagado, etc.
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			sys = (Application) ois.readObject();
			ois.close();
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
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sys;
	}
		
	/**
	 * Method that saves all the application's data into a backup file.
	 * @param file String with the name of the backup file.
	 */
	public void saveToFile(String file) {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream( new FileOutputStream(file));
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


