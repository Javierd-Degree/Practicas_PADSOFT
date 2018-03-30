package User;
import java.io.Serializable;
import java.util.*;

import House.House;
import Offer.*;


public class RegisteredUser implements Serializable{
	private static final long serialVersionUID = 5875960192364410941L;
	private int id;
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
	
	
	public RegisteredUser(int id, String name, String surname, String creditCard,
			String password) {
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
	
	public String getCreditCard() {
		return creditCard;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public List<House> getCreatedHouses() {
		return createdHouses;
	}
	
	public void changeCreditCard(String creditCard) {
		this.creditCard = creditCard;
		//TODO ¿Por que teniamos esto? this.status = UNLOGGED;
	}
	
	public void changeStatus(int status) {
		this.setStatus(status);
	}
	
	public List<Offer> seeOffers(){
		return this.createdOffers;
	}
	
	public List<Offer> seeHistory(){
		return this.boughtOffers;
	}
	
	public boolean addOffer(Offer o, int s){
		if(s == CREATED_OFFER){
			if(createdOffers.contains(o)) {
				return false;
			}
			this.createdOffers.add(o);
		}
		else {
			if(boughtOffers.contains(o)) {
				return false;
			}
			this.boughtOffers.add(o);
			
			/*TODO quitar la oferta del array si deja de estar reservada*/
		}	
		return true;
	}
	
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
	
	public boolean addHouse(House h) {
		if(h == null || this.createdHouses.contains(h)) {
			return false;
		}
		
		this.createdHouses.add(h);
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof RegisteredUser)) {
			return false;
		}
		
		RegisteredUser u = (RegisteredUser) o;
		return this.id == u.id;
	}
}
