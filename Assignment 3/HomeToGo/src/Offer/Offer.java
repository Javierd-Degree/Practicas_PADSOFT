package Offer;

import java.time.LocalDate;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import User.RegisteredUser;
import Exceptions.*;
import Comment.*;
import House.House;
import es.uam.eps.padsof.telecard.*;

public abstract class Offer implements Serializable{
	private static final long serialVersionUID = -6572064457556207983L;
	private double deposit;
	private int status;
	private LocalDate startDate;
	private LocalDate lastModifiedDate;
	private RegisteredUser host;
	private RegisteredUser guest;
	private House house;
	private List<Comment> comments;
	
	public static final int WAITING = 0;
	public static final int TO_CHANGE = -1;
	public static final int DENIED = -2;
	public static final int AVAILABLE = 1;
	public static final int RESERVED = 2;
	public static final int BOUGHT = 3;
	
	public Offer(double deposit, LocalDate startDate, RegisteredUser host, House house) {
		this.deposit = deposit;
		this.status = WAITING;
		this.startDate = startDate;
		this.lastModifiedDate = LocalDate.now();
		this.host = host;
		this.guest = null;
		this.house = house;
		this.comments = new ArrayList<>();
		
		/*TODO Anadir al array del usuario y del sistema*/
	}
	
	public void setLastModifiedDate(LocalDate date) {
		this.lastModifiedDate = date;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void approveOffer() {
		this.status = AVAILABLE;
		this.lastModifiedDate = LocalDate.now();
	}
	
	public void denyOffer() {
		/*TODO Mirar que hacer cuando esta reservad etc*/
		this.status = DENIED;
		this.lastModifiedDate = LocalDate.now();
	}
	
	public void askForChanges(String text) {
		ChangeComment comment = new ChangeComment(text);
		this.status = TO_CHANGE;
		this.lastModifiedDate = LocalDate.now();
		comments.add(comment);
	}
	
	public void reserveOffer(RegisteredUser guest) throws NotAvailableOfferException{
		/**TODO make sure the user id a guest?*/
		if(this.status != AVAILABLE) {
			throw new NotAvailableOfferException();
		}
		
		this.status = RESERVED;
		this.lastModifiedDate = LocalDate.now();
		this.guest = guest;
		guest.addOffer(this, RegisteredUser.HIST_OFFER);
	}
	
	public void buyOffer(RegisteredUser guest, String subject) throws NotAvailableOfferException,
			FailedInternetConnectionException,
		    OrderRejectedException {
		
		if(this.status == RESERVED && !this.guest.equals(guest)) {
			throw new NotAvailableOfferException();
		}else if(this.status == BOUGHT || this.status == WAITING || this.status == DENIED 
				|| this.status == TO_CHANGE) {
			throw new NotAvailableOfferException();
		}
		
		
		try {
			TeleChargeAndPaySystem.charge(guest.getCreditCard(), subject, this.getPrice());
		} catch (InvalidCardNumberException e) {
			/*TODO The user should be unlogged.*/
			guest.changeStatus(RegisteredUser.BANNED);
			throw e;
		}
		
		this.status = BOUGHT;
		this.lastModifiedDate = LocalDate.now();
		this.guest = guest;
	}
	
	public double calculateRating() {
		double sum = 0;
		int num = 0;
		for(Comment c: comments) {
			if(c instanceof Rating) {
				sum += ((Rating)c).getRating();
				num++;
			}
		}
		return sum/num;
	}
	
	public boolean postComment(RegisteredUser user, String text) {
		TextComment comment = new TextComment(text, user);
		this.comments.add(comment);
		return true;
	}
	
	public boolean postComment(RegisteredUser user, int rating) {
		if(rating < 0 || rating > 5) {
			return false;
		}
		
		Rating comment = new Rating(rating, user);
		this.comments.add(comment);
		return true;
	}
	
	public boolean postComment(RegisteredUser user, String text, TextComment answerTo) {
		if(answerTo == null || !this.comments.contains(answerTo)) {
			return false;
		}
		
		answerTo.answerComment(text, user);
		return true;
	}
	
	public abstract double getPrice();
	
	public RegisteredUser getHost() {
		return host;
	}
	
	public RegisteredUser getGuest() {
		return guest;
	}
	
	public House getHouse() {
		return house;
	}
	
	public double getDeposit() {
		return this.deposit;
	}
	
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof Offer)) {
			return false;
		}
		
		Offer o = (Offer) ob;
		if(this.guest == null && o.guest == null) {
			return this.deposit == o.deposit && this.status == o.status &&
					this.startDate.equals(o.startDate) && this.lastModifiedDate.equals(o.lastModifiedDate)
					&& this.host.equals(o.host) && this.comments.equals(o.comments)
					&& this.house.equals(o.house);
		}else if(this.guest == null && o.guest == null) {
			/*If just one of them is null, the offers are obviously not the same.*/
			return false;
		}
		
		return this.deposit == o.deposit && this.status == o.status &&
				this.startDate.equals(o.startDate) && this.lastModifiedDate.equals(o.lastModifiedDate)
				&& this.host.equals(o.host) && this.guest.equals(o.guest) && this.comments.equals(o.comments)
				&& this.house.equals(o.house);
		
	}
}
