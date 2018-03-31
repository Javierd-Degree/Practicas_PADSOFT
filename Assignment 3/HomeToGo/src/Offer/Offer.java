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

/**
 * Abstract class which implements an offer that has a deposit,
 * start date, house, host, guest a list of comments, and a status.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
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
	
	/**
	 * Constructor of the Offer class.
	 * 
	 * @param deposit double with the money the offer's deposit costs.
	 * @param startDate LocalDate which indicates when the offer starts.
	 * @param host RegisteredUser who creates the offer.
	 * @param house House in which the offer takes place.
	 */
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
	
	/**
	 * Updates the offer's lasModifiedDate, which allows the system to know 
	 * if more than five days since the changes were asked or since a reservation
	 * was made have passed.
	 * 
	 * @param date LocalDate when the las modificaton was made.
	 */
	public void setLastModifiedDate(LocalDate date) {
		this.lastModifiedDate = date;
	}
	
	/**
	 * Getter of the user status that shows if it's waiting to be approved,
	 * reserved, bought, denied.
	 * 
	 * @return the offer status, which can be: WAITING, TO_CHANGE, DENIED, 
	 * AVAILABLE, RESERVED, BOUGHT.
	 */
	public int getStatus() {
		return this.status;
	}
	
	/**
	 * Approve an offer which was waiting to be approved.
	 * The offer will be marked as AVAILABLE.
	 */
	public void approveOffer() {
		this.status = AVAILABLE;
		this.lastModifiedDate = LocalDate.now();
	}
	
	/**
	 * Deny an offer.
	 * The offer will be marked as DENIED.
	 */
	public void denyOffer() {
		/*TODO Mirar que hacer cuando esta reservad etc*/
		this.status = DENIED;
		this.lastModifiedDate = LocalDate.now();
	}
	
	/**
	 * Ask for changes on an offer in case the Administrator consider
	 * it is not valid to be published.
	 * The admin writes a comment so that the host knows what to change.
	 * 
	 * @param text The comment that the administrator wants to write.
	 */
	public void askForChanges(String text) {
		/*TODO Mirar que hacer cuando esta reservad etc*/
		ChangeComment comment = new ChangeComment(text);
		this.status = TO_CHANGE;
		this.lastModifiedDate = LocalDate.now();
		comments.add(comment);
	}
	
	/**
	 * Reserve an offer so that it is marked as RESERVED, its Guest
	 * is set to guest, and the offer is added to the guest's
	 * bought/reserved offers list.
	 * We do not make sure if the user is a host or a guest, as
	 * this restriction will be implemented using the graphical
	 * user interface.
	 * 
	 * @param guest The RegisteredUser who wants to reserve the offer.
	 * @throws NotAvailableOfferException in case the offer is not available.
	 */
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
	
	/**
	 * Buy an offer so that it is marked as BOUGHT, its Guest
	 * is set to guest, and the offer is added to the guest's
	 * bought/reserved offers list (in case it was not added before).
	 * We do not make sure if the user is a host or a guest, as
	 * this restriction will be implemented using the graphical
	 * user interface.
	 * 
	 * @param guest The RegisteredUser who wants to buy the offer.
	 * @param subject String which describes the bought.
	 * @throws NotAvailableOfferException in case the offer is not available,
	 * or if the offer is reserved by a different user than the one who wants
	 * to buy it.
	 * @throws FailedInternetConnectionException if the subject starts with W or w.
	 * @throws OrderRejectedException
	 */
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
		/*In case it is already added, it would just return false.*/
		guest.addOffer(this, RegisteredUser.HIST_OFFER);
	}
	
	/**
	 * Calculate the average rating of all the offer's comments.
	 * 
	 * @return double with the average rating.
	 */
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
	
	/**
	 * Post a TextComment into an offer.
	 * 
	 * @param user RegisteredUsers who wrote the comment.
	 * @param text String to be posted on the comment.
	 * @return true.
	 */
	public boolean postComment(RegisteredUser user, String text) {
		TextComment comment = new TextComment(text, user);
		this.comments.add(comment);
		return true;
	}
	
	/**
	 * Post a rating into an offer.
	 * 
	 * @param user RegisteredUsers who rated the offer.
	 * @param rating int from zero to five.
	 * @return false if the rating is not an int from zero to five, true otherwise.
	 */
	public boolean postComment(RegisteredUser user, int rating) {
		if(rating < 0 || rating > 5) {
			return false;
		}
		
		Rating comment = new Rating(rating, user);
		this.comments.add(comment);
		return true;
	}
	
	/**
	 * Post an answer to another offer's TextComment.
	 * 
	 * @param user RegisteredUsers who wrote the comment answer.
	 * @param text  String to be posted on the comment answer.
	 * @param answerTo Comment the user wants to answer to.
	 * @return false if the answerTo comment was not in the offer, true otherwise.
	 */
	public boolean postComment(RegisteredUser user, String text, TextComment answerTo) {
		if(answerTo == null || !this.comments.contains(answerTo)) {
			return false;
		}
		
		answerTo.answerComment(text, user);
		return true;
	}
	
	/**
	 * Abstract function that returns the total price of an offer.
	 * 
	 * @return double with the total price the user needs to pay.
	 */
	public abstract double getPrice();
	
	/**
	 * Getter of the offer's host.
	 * @return RegisteredUser which created the offer.
	 */
	public RegisteredUser getHost() {
		return host;
	}
	
	/**
	 * Getter of the offer's guest.
	 * @return RegisteredUser which has reserved/bought the offer.
	 */
	public RegisteredUser getGuest() {
		return guest;
	}
	
	/**
	 * Getter of the offer's house.
	 * 
	 * @return House of the offer.
	 */
	public House getHouse() {
		return house;
	}
	
	/**
	 * Getter of the offer's deposit the guest needs to pay.
	 * @return double with the offer's deposit price.
	 */
	public double getDeposit() {
		return this.deposit;
	}
	
	/**
	 * Getter of the LocalDate when the offer starts.
	 * 
	 * @return LocalDate with the date the offer starts.
	 */
	public LocalDate getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Compare two Offer to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be an Offer.
	 * @return boolean true if they are the same, false otherwise.
	 */
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
