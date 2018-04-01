package User;
import java.io.Serializable;
import java.util.*;
import House.House;
import Offer.*;


/**
 * This class implements a registered user who has a unique id,
 * name, surname, password and credit card.
 * He also has a list of bought and created offers, and a list
 * of created houses.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class RegisteredUser implements Serializable{
	private static final long serialVersionUID = 5875960192364410941L;
	private String id;
	private String name;
	private String surname;
	private String creditCard;
	private String password;
	private int status;
	private List<Offer> boughtOffers;
	private List<Offer> createdOffers;
	private List<House> createdHouses;
	
	public final static int UNLOGGED = 0;
	public final static int LOGGED = 1;
	public final static int BANNED = -1;
	
	public final static int HIST_OFFER = 0;
	public final static int CREATED_OFFER = 1;
	
	
	/**
	 * Constructor of the RegisteredUser class.
	 * 
	 * @param id int with the unique id of the user.
	 * @param name String with the user's name.
	 * @param surname String with the user's name.
	 * @param creditCard String with the user's credit card.
	 * It should have 16 digits to be well-formed and avoid exceptions.
	 * @param password String with the user's password.
	 */
	public RegisteredUser(String id, String name, String surname, String creditCard, String password) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.creditCard = creditCard;
		this.password = password;
		this.status = RegisteredUser.UNLOGGED;
		this.boughtOffers = new ArrayList<>();
		this.createdOffers = new ArrayList<>();
		this.createdHouses = new ArrayList<>();
	}
	
	/**
	 * Getter of the user's credit card used to pay the offers.
	 * @return String with user's credit card
	 */
	public String getCreditCard() {
		return creditCard;
	}
	
	/**
	 * Getter of the user's id used to log in.
	 * @return String with the user's id.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Getter of the user's name used to print his information
	 * on comments, for example.
	 * @return String with the user's name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter of the user's password used to log in.
	 * @return String with the user's password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Getter of the user's status used determine if he is logged in, banned, etc.
	 * @return int with the user's status.
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Getter of the list of houses the user has created,
	 * Used to represent those houses on the graphical user interface.
	 * @return List with the user's created houses.
	 */
	public List<House> getCreatedHouses() {
		return createdHouses;
	}
	
	/**
	 * Change the credit card of a user in order to un-ban it.
	 * @param creditCard
	 */
	public void changeCreditCard(String creditCard) {
		this.creditCard = creditCard;
		//TODO ¿Deberiamos desbloquearle al llamar a esta función?
		//TODO ¿Por que teniamos esto? this.status = UNLOGGED;
	}
	
	/**
	 * Sets the user status in order to log in, log out or be banned.
	 * @param status The status the user will have once the method is called.
	 */
	public void changeStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Returns the entire list of offers a host has created.
	 * Used to show those offers on the graphical user interface.
	 * @return List with the user's created offers.
	 */
	public List<Offer> seeOffers(){
		return this.createdOffers;
	}
	
	/**
	 * Returns the entire list of offers a guest has reserved/bought.
	 * Used to show those offers on the graphical user interface.
	 * @return List with the user's reserved/bought offers.
	 */
	public List<Offer> seeHistory(){
		return this.boughtOffers;
	}
	
	/**
	 * Adds an offer to a user's created or reserved/bought offers list,
	 * depending on the value of s.
	 * 
	 * @param o The offer to be added.
	 * @param s int that can be CREATE_OFFER (the offer is a created offer),
	 * or HIST_OFFER (the offer is a reserved/bought offer).
	 * @return boolean true if it has been added or false if it was already added.
	 */
	public boolean addOffer(Offer o, int s){
		if(s == CREATED_OFFER){
			if(createdOffers.contains(o)) {
				return false;
			}
			this.createdOffers.add(o);
		}
		else if(s == HIST_OFFER){
			if(boughtOffers.contains(o)) {
				return false;
			}
			this.boughtOffers.add(o);
			
			/*TODO quitar la oferta del array si deja de estar reservada*/
		}	
		return true;
	}
	
	/**
	 * Removes an offer from a user's created or reserved/bought offers list,
	 * depending on the value of s.
	 * 
	 * @param o The offer to be removed.
	 * @param s int that can be CREATE_OFFER (the offer is a created offer),
	 * or HIST_OFFER (the offer is a reserved/bought offer).
	 * @return boolean true if it was removed successfully or false 
	 * if the offer wasn't in the list.
	 */
	public boolean removeOffer(Offer o, int s) {
		if(s == CREATED_OFFER){
			if(createdOffers.contains(o)) {
				this.createdOffers.remove(o);
				return true;
			}
			return false;
		}
		else {
			if(boughtOffers.contains(o)) {
				this.boughtOffers.add(o);
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Adds a house to a user's houses list.
	 * 
	 * @param h The House to be added.
	 * @return boolean true if the house was added successfully 
	 * or false if was already in the list.
	 */
	public boolean addHouse(House h) {
		if(h == null || this.createdHouses.contains(h)) {
			return false;
		}
		
		this.createdHouses.add(h);
		return true;
	}
	
	/**
	 * Returns the RegisteredUser as a String
	 * so that it can be shown.
	 * 
	 * @return String with the RegisteredUser's data.
	 */
	@Override
	public String toString() {
		String result = this.name + this.surname + " has the ID: " + this.id + "and the credit card: " + this.creditCard + 
				"and has the password: " + this.password + "and status: " + this.status;
		return result;
	}
	
	/**
	 * Compare two RegisteredUser to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be a RegisteredUser.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof RegisteredUser)) {
			return false;
		}
		
		RegisteredUser u = (RegisteredUser) o;
		return this.id.equals(u.id);
	}
}
