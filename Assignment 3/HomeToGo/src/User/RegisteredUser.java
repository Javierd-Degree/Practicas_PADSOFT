package User;
import java.util.*;

import House.House;
import Offer.*;

public class RegisteredUser {
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
	
	
	public RegisteredUser(int id, String name, String surname, String creditCard, String password, int status,
			List<Offer> boughtOffers, List<Offer> createdOffers, List<House> createdHouses) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.creditCard = creditCard;
		this.password = password;
		this.status = status;
		this.boughtOffers = boughtOffers;
		this.createdOffers = createdOffers;
		this.createdHouses = createdHouses;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Offer> getBoughtOffers() {
		return boughtOffers;
	}
	public void setBoughtOffers(List<Offer> boughtOffers) {
		this.boughtOffers = boughtOffers;
	}
	public List<Offer> getCreatedOffers() {
		return createdOffers;
	}
	public void setCreatedOffers(List<Offer> createdOffers) {
		this.createdOffers = createdOffers;
	}
	public List<House> getCreatedHouses() {
		return createdHouses;
	}
	public void setCreatedHouses(List<House> createdHouses) {
		this.createdHouses = createdHouses;
	}
	
	
	public void changeCreditCard(String creditCard) {
		this.creditCard=creditCard;
		this.status=UNLOGGED;
	}
	
	public void changeStatus(int status) {
		this.setStatus(status);
	}
	
	public List<Offer> seeOffers(){
		return this.getCreatedOffers();
	}
	
	public List<Offer> seeHistory(){
		return this.getBoughtOffers();
	}
	
	public void addOffer(Offer o, int s){
		if(s==CREATED_OFFER){
			this.createdOffers.add(o);
		}
		else {
			this.boughtOffers.add(o);
			
			/*TODO quitar la oferta del array si deja de estar reservada*/
		}	
	}
	
	public boolean equals(RegisteredUser u) {
		
		return this.id == u.id;
	}
}
