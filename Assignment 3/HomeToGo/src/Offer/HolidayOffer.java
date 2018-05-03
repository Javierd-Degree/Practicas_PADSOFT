package Offer;

import java.io.Serializable;
import java.time.LocalDate;

import Exceptions.DateRangeException;
import Exceptions.NotAvailableOfferException;
import House.House;
import User.RegisteredUser;

/**
 * Class which implements a holiday offer, by adding an end date 
 * and a total price to Offer.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class HolidayOffer extends Offer implements Serializable{
	private static final long serialVersionUID = -3546137102202274577L;
	private LocalDate endDate;
	private double totalPrice;
	
	/**
	 * Constructor of the HolidayOffer class.
	 * 
	 * @param deposit double with the money the offer's deposit costs.
	 * @param startDate LocalDate which indicates when the offer starts.
	 * @param host RegisteredUser who creates the offer.
	 * @param house House in which the offer takes place.
	 * @param endDate LocalDate which indicates when the offer ends.
	 * @param totalPrice double with the total price the offer costs.
	 * 
	 * @throws DateRangeException if the start date is after the end date.
	 */
	public HolidayOffer(double deposit, LocalDate startDate, RegisteredUser host, House house, LocalDate endDate, double totalPrice) 
		throws DateRangeException{
		super(deposit, startDate, host, house);
		
		if(startDate.isAfter(endDate)) {
			throw new DateRangeException();
		}
		
		this.endDate = endDate;
		this.totalPrice = totalPrice;
	}
	
	/**
	 * Setter of the offer's ending date in case the administrator asks for
	 * changes on the offer.
	 *  
	 * @param d LocalDate with the new offer's ending date.
	 * @throws NotAvailableOfferException If the offer is not waiting to be changed.
	 */
	public void setEndDate(LocalDate d) throws NotAvailableOfferException {
		if(this.getStatus() != TO_CHANGE && this.getStatus() != WAITING) {
			throw new NotAvailableOfferException();
		}
		
		this.endDate = d;
		this.setWaiting();
	}
	
	/**
	 * Setter of the offer's total price date in case the administrator asks for
	 * changes on the offer.
	 *  
	 * @param t double with the new offer's total price.
	 * @throws NotAvailableOfferException If the offer is not waiting to be changed.
	 */
	public void setTotalPrice(double t) throws NotAvailableOfferException {
		if(this.getStatus() != TO_CHANGE && this.getStatus() != WAITING) {
			throw new NotAvailableOfferException();
		}
		
		this.totalPrice = t;
		this.setWaiting();
	}
	
	/**
	 * Getter of the end date of the HolidayOffer.
	 * @return offer's ending date.
	 */
	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Compare two HolidayOffer to know if they are the same one.
	 * 
	 * @param ob Object we want to compare, must be a HolidayOffer.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof HolidayOffer)) {
			return false;
		}
		
		HolidayOffer h = (HolidayOffer) ob;
		return super.equals(h) && this.totalPrice == h.totalPrice && this.endDate.equals(h.endDate);
	}
	
	/**
	 * Return the commissions an offer has.
	 * For holiday offers is 2% of the offer total price.
	 * @return double of the offer commissions.
	 */
	public double commissions() {
		return 0.02;
	}
	
	/**
	 * Return the total price of a holiday offer.
	 * 
	 * @return double with the total price the user needs to pay.
	 */
	public double getPrice() {
		return this.totalPrice + this.getDeposit();
	}
	
	/**
	 * Returns the offer's price for the stance, without the deposit.
	 * @return offer's total price without the deposit.
	 */
	public double getTotalPrice() {
		return this.totalPrice;
	}
	
	/**
	 * Method that allows us to clone an offer.
	 * It just copy the offer content, not the state
	 * (reserved, the guest, etc).
	 * 
	 * @param o Offer to be cloned.
	 * @return A copy of the original offer
	 */
	public HolidayOffer clone() {
		HolidayOffer offer;
		try {
			offer = new HolidayOffer(getDeposit(), getStartDate(), getHost(), getHouse(), getEndDate(), getTotalPrice());
		} catch (DateRangeException e) {
			//Nunca va a pasar
			return null;
		}
		return offer;
	}
	
	/**
	 * Method that allow us to get a simple offer name.
	 * @return String with the offer name.
	 */
	@Override
	public String getName() {
		return "Holiday offer in house "+this.getHouse().getId()+".";
	}
	
	/**
	 * Method that allows us to get some basic offer
	 * information.
	 * @param logged Boolean that indicates whether the user is logged in 
	 * or not, in order to show more information.
	 * @return String with some offer information.
	 */
	@Override
	public String getInfo(boolean logged) {
		String result = "";
		result += "Start date:\t\t" + this.getStartDate()+"\n";
		result += "End date:\t\t" + this.getEndDate() +"\n";
		if(logged) {
			result += "Deposit:\t\t" + this.getDeposit()+"\n";
			result += "Price:\t\t" + (this.getPrice()-this.getDeposit());
		}
		
		return result;
	}
}
