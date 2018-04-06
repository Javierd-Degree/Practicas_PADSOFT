package Offer;

import java.time.LocalDate;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import User.RegisteredUser;
import User.UserType;
import Exceptions.*;
import Comment.*;
import Date.ModificableDate;
import House.House;
import Application.Application;
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
	public static final int NOT_AVAILABLE = -2;
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
	}
	
	/**
	 * Method that validates an offer and solves possible problems.
	 * In case the offer is reserved since five or more days, but 
	 * the guest has not paid we mark it as available,or as not 
	 * available if the offer has already started.
	 * 
	 * An offer is not valid just when more than five days have passes
	 * since the administrator asked for changes.
	 * 
	 * @return
	 */
	public boolean isValid(LocalDate todayDate) {
		/*The host has not made the necessary changes*/
		if(this.status == TO_CHANGE && lastModifiedDate.plusDays(5).isBefore(todayDate)) {
			/*The offer is denied and deleted if needed on Application*/
			return false;
		}
		/*The guest has not paid*/
		if(this.status == RESERVED && lastModifiedDate.plusDays(5).isBefore(todayDate)) {
			this.status = AVAILABLE;
			this.lastModifiedDate = LocalDate.now();
			this.guest.removeOffer(this, RegisteredUser.HIST_OFFER);
			this.guest = null;
		}
		
		if(startDate.isBefore(todayDate) && this.status != BOUGHT) {
			if(this.status == RESERVED) {
				this.guest.removeOffer(this, RegisteredUser.HIST_OFFER);
				this.guest = null;
			}
			
			this.status = NOT_AVAILABLE;
			this.lastModifiedDate = LocalDate.now();
		}
		
		return true;
	}
	
	/**
	 * Setter of the offer's starting date in case the administrator asks for
	 * changes on the offer.
	 *  
	 * @param l new offer's starting date.
	 * @throws NotAvailableOfferException If the offer is not waiting to be changed.
	 */
	public void setStartDate(LocalDate l) throws NotAvailableOfferException {
		if(this.status != TO_CHANGE) {
			throw new NotAvailableOfferException();
		}
		
		this.startDate = l;
		this.lastModifiedDate = LocalDate.now();
		this.status = WAITING;
	}
	
	/**
	 * Setter of the offer's deposit in case the administrator asks for
	 * changes on the offer.
	 *  
	 * @param d double with the new offer's deposit.
	 * @throws NotAvailableOfferException If the offer is not waiting to be changed.
	 */
	public void setDeposit(double d) throws NotAvailableOfferException {
		if(this.status != TO_CHANGE) {
			throw new NotAvailableOfferException();
		}
		
		this.deposit = d;
		this.lastModifiedDate = LocalDate.now();
		this.status = WAITING;
	}
	
	/**
	 * Setter of the offer's house in case the administrator asks for
	 * changes on the offer.
	 *  
	 * @param h new offer's house.
	 * @throws NotAvailableOfferException If the offer is not waiting to be changed.
	 */
	public void setHouse(House h) throws NotAvailableOfferException {
		if(this.status != TO_CHANGE) {
			throw new NotAvailableOfferException();
		}
		
		this.house = h;
		this.lastModifiedDate = LocalDate.now();
		this.status = WAITING;
	}
	
	/**
	 * Updates the offer's lasModifiedDate, which allows the system to know 
	 * if more than five days since the changes were asked or since a reservation
	 * was made have passed.
	 * 
	 * @param date LocalDate when the last modification was made.
	 */
	protected void setLastModifiedDate(LocalDate date) {
		this.lastModifiedDate = date;
	}
	
	/**
	 * Protected function used by LivingOffer and HolidayOffer
	 * to notify they have made changes on the offer.
	 * 
	 * @param date LocalDate when the last modification was made.
	 */
	protected void setWaiting() {
		this.status = WAITING;
		this.lastModifiedDate = LocalDate.now();
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
	 * If it was already approved, we just exit the function,
	 * in order to avoid a silly exception.
	 * 
	 * @throws NotAvailableOfferException if the offer can't be denied
	 * because it is already approved/reserved/bought.
	 */
	public void approveOffer() throws NotAvailableOfferException {
		if(this.status == AVAILABLE) return;
		
		if(this.status != WAITING && this.status != TO_CHANGE) {
			throw new NotAvailableOfferException();
		}
		this.status = AVAILABLE;
		this.lastModifiedDate = LocalDate.now();
	}
	
	/**
	 * Deny an offer which the administrator consider it is not valid,
	 * or because it is not reserved an the host wants to remove it.
	 * The offer will be marked as DENIED.
	 * If it was already denied, we just exit the function, in
	 * order to avoid a silly exception.
	 * 
	 * @throws NotAvailableOfferException if the offer can't be denied
	 * because it is already approved/reserved/bought, or if it is
	 * a valid offer.
	 */
	public void denyOffer() throws NotAvailableOfferException {
		if(this.status == DENIED) return;
		
		if(this.status != WAITING && this.status != TO_CHANGE
				&& this.isValid(ModificableDate.getModifiableDate())) {
			throw new NotAvailableOfferException();
		}
		this.status = DENIED;
		this.lastModifiedDate = LocalDate.now();
	}
	
	/**
	 * Ask for changes on an offer in case the Administrator consider
	 * it is not valid to be published.
	 * The admin writes a comment so that the host knows what to change.
	 * Although the Offer was previously on the status TO_CHANGE, the
	 * administrator may want to add more comments.
	 * 
	 * @param text The comment that the administrator wants to write.
	 * @throws NotAvailableOfferException if the offer can't be changed because
	 * it is already approved/reserved/bought/denied.
	 */
	public void askForChanges(String text) throws NotAvailableOfferException {
		if(this.status == TO_CHANGE) return;
		
		if(this.status != WAITING && this.status != TO_CHANGE) {
			throw new NotAvailableOfferException();
		}
		
		ChangeComment comment = new ChangeComment(text);
		this.status = TO_CHANGE;
		this.lastModifiedDate = LocalDate.now();
		comments.add(comment);
	}
	
	/**
	 * Reserve an offer so that it is marked as RESERVED, its Guest
	 * is set to guest, and the offer is added to the guest's
	 * bought/reserved offers list.
	 * 
	 * @param guest The RegisteredUser who wants to reserve the offer.
	 * @throws NotAvailableOfferException in case the offer is not available.
	 */
	public void reserveOffer(RegisteredUser guest) throws NotAvailableOfferException{
		if(this.status != AVAILABLE) {
			throw new NotAvailableOfferException();
		}else if(guest.getType() == UserType.HOST) {
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
	 * Pay the guest and the host.
	 * 
	 * If the guest credit card is not valid, he is banned, the offer is
	 * marked as available, and he is logged out.
	 * 
	 * If the host credit card is not valid, he is banned and the money is
	 * added to his debt money.
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
				|| this.status == TO_CHANGE || this.status == NOT_AVAILABLE) {
			throw new NotAvailableOfferException();
		}else if(guest.getType() == UserType.HOST) {
			throw new NotAvailableOfferException();
		}
		
		
		try {
			TeleChargeAndPaySystem.charge(guest.getCreditCard(), subject, -this.getPrice());
			TeleChargeAndPaySystem.charge(host.getCreditCard(), subject, this.getPrice()*(1 - this.commissions()));
		} catch (InvalidCardNumberException e) {
			if(!TeleChargeAndPaySystem.isValidCardNumber(guest.getCreditCard())) {
				/*If the guest credit card is not valid,*/
				guest.changeStatus(RegisteredUser.BANNED);
				guest.removeOffer(this, RegisteredUser.HIST_OFFER);
				this.guest = null;
				this.status = AVAILABLE;
				Application.getInstance().logout();
			}
			if(!TeleChargeAndPaySystem.isValidCardNumber(host.getCreditCard())){
				/*If the host credit card is not valid*/
				host.sumDebtMoney(this.getPrice()*(1 - this.commissions()));
				host.changeStatus(RegisteredUser.BANNED);
				this.status = BOUGHT;
				this.lastModifiedDate = LocalDate.now();
				this.guest = guest;
			}
			return;
		}
		
		this.status = BOUGHT;
		this.lastModifiedDate = LocalDate.now();
		this.guest = guest;
		/*In case it is already added, it would just return false.*/
		guest.addOffer(this, RegisteredUser.HIST_OFFER);
	}
	
	/**
	 * Return the commissions an offer has.
	 * @return double of the offer commissions.
	 */
	public abstract double commissions();
	
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
		return (num == 0) ? 0 : (sum/num);
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
	 *Transforms the offer data into a string so that it can be printed.
	 *
	 *@return String with the offer's data.
	 */
	@Override
	public String toString() {
		if(this instanceof LivingOffer) {
			return "This living offer is from " + this.host.getName() + " in the house " + this.house + ", has the price " + this.getPrice() + " and is currently " + this.status;
		}else {
			return "This holiday offer is from " + this.host.getName() + " in the house " + this.house + ", has the price " + this.getPrice() + " and is currently " + this.status;
		}
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
