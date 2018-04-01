import java.util.List;

import Exceptions.NotAvailableOfferException;

import java.io.Serializable;

import House.House;

import java.util.ArrayList;

import Offer.*;

import User.RegisteredUser;

import java.time.LocalDate;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class System implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Administrator> admins;
	private List<RegisteredUser> guests;
	private List<RegisteredUser> hosts;
	private List<RegisteredUser> both;
	private List<Offer> offers;
	private List<House> houses;
	
	public static final int HOLIDAY_OFFER = 0;
	public static final int LIVING_OFFER = 1;
	
	
	private System() {
		admins = new ArrayList<>();
		guests = new ArrayList<>();
		hosts = new ArrayList<>();
		both = new ArrayList<>();
		offers = new ArrayList<>();
		houses = new ArrayList<>();
		/* Lo ha puesto el profesor
		ArrayList<Offer> toRemove = new ArrayList<Offer>();
		for (Offer of : offers) {
			if (of.hasExpired()) {
				toRemove.add(of);
				offers.remove
			}
		}
		offers.removeAll(toRemove);*/
		
		
	}
	
	/*TODO Hacer funciones para cargar los datos de fichero*/
	
	public int login(String id, String password) {
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
	
	public void addHouse(String id) {
		House h = new House(id);
		houses.add(h);
	}
	
	public void cancelOffer(Offer o) throws NotAvailableOfferException {
		/*remove it from the system array*/
		if(o.getStatus() == Offer.RESERVED || o.getStatus() == Offer.BOUGHT) {
			throw new NotAvailableOfferException();
		}
		if(offers.contains(o)==true) {
			offers.remove(o);
		}
		RegisteredUser host = o.getHost();
		RegisteredUser guest = o.getGuest();
		host.removeOffer(o, RegisteredUser.CREATED_OFFER);
		guest.removeOffer(o, RegisteredUser.HIST_OFFER);
		
	}
	
	public List<Offer> seeNonApprovedOffer(){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.getStatus() == Offer.WAITING) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	public boolean addOffer(double deposit, double totalPrice, LocalDate startDate, LocalDate endDate, House house, RegisteredUser host) {
		for(Offer o : offers) {
			if(o instanceof HolidayOffer && o.getHouse() == house) {
				return false;
			}
		}
		HolidayOffer o = new HolidayOffer(deposit, startDate, host, house, endDate, totalPrice);
		offers.add(o);
		return true;
	}
	
	public boolean addOffer(double deposit, double pricePerMonth, LocalDate startDate, House house, RegisteredUser host) {
		for(Offer o : offers) {
			if(o instanceof LivingOffer && o.getHouse() == house) {
				return false;
			}
		}
		LivingOffer o = new LivingOffer(deposit, startDate, host, house, pricePerMonth);
		offers.add(o);
		return true;
	}
	
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
	
	
	public List<Offer> searchByDate(LocalDate startDate, LocalDate endDate){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.getStartDate().isAfter(startDate) && o.getStartDate().isBefore(endDate)) {
				offs.add(o);
			}
		}
		return offs;
	}
	
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
	
	public List<Offer> searchByRating(double rating){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.calculateRating() >= rating) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	public List<Offer> searchReservedOffers(){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.getStatus() == Offer.RESERVED) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	public List<Offer> searchBoughtOffers(){
		List<Offer> offs = new ArrayList<>();
		for(Offer o : offers) {
			if(o.getStatus() == Offer.BOUGHT) {
				offs.add(o);
			}
		}
		return offs;
	}
	
	public static System loadFromFile(String file) {
		System sys = null;
		try { 
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			sys = (System) ois.readObject();
			ois.close();
			return sys;

		} catch (FileNotFoundException e) {
			String data, file2 = "fichero.txt";
			try {
				FileReader f = new FileReader(file2);
				BufferedReader b = new BufferedReader(f);
				sys = new System();
				while((data = b.readLine())!=null) {
					String[] tokens = data.split(";");
					String[] name = tokens[2].split(",");
					if(tokens[0].equals("O")){
						sys.hosts.add(new RegisteredUser(tokens[1], name[0], name[1], tokens[4], tokens[3]));
					}else if(tokens[0].equals("D")){
						sys.guests.add(new RegisteredUser(tokens[1], name[0], name[1], tokens[4], tokens[3]));
					}else{
						sys.both.add(new RegisteredUser(tokens[1], name[0], name[1], tokens[4], tokens[3]));
					}
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


