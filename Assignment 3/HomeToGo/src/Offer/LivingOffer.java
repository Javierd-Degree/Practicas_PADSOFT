package Offer;

import java.io.Serializable;
import java.time.LocalDate;

import Exceptions.DateRangeException;
import Exceptions.NotAvailableOfferException;
import House.House;
import User.RegisteredUser;


/**
 * Class which implements a living offer, by adding a price per month 
 * to Offer.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class LivingOffer extends Offer implements Serializable{
	private static final long serialVersionUID = 6202450079263161446L;
	private double pricePerMonth;
	private int nMonths;
	public final static int defaultNMonths =  5;
	
	/**
	 * Constructor of the LivingOffer class with five months for the offer.
	 * 
	 * @param deposit double with the money the offer's deposit costs.
	 * @param startDate LocalDate which indicates when the offer starts.
	 * @param host RegisteredUser who creates the offer.
	 * @param house House in which the offer takes place.
	 * @param pricePerMonth double with the monthly price the offer costs.
	 */
	public LivingOffer(double deposit, LocalDate startDate, RegisteredUser host, House house, double pricePerMonth) {
		super(deposit, startDate, host, house);
		this.pricePerMonth = pricePerMonth;
		/*DO NOT CHANGE THIS VALUE, otherwise, you need to modifify the add method in Application.*/
		this.nMonths = defaultNMonths;
	}
	
	/**
	 * Constructor of the LivingOffer class with a custom number of
	 * months for the offer.
	 * 
	 * @param deposit double with the money the offer's deposit costs.
	 * @param startDate LocalDate which indicates when the offer starts.
	 * @param host RegisteredUser who creates the offer.
	 * @param house House in which the offer takes place.
	 * @param pricePerMonth double with the monthly price the offer costs.
	 * @param nMonths number of months the living offer is going to be availabe.
	 * 
	 * @throws DateRangeException if the number of months is 0 or less.
	 */
	public LivingOffer(double deposit, LocalDate startDate, RegisteredUser host, House house, double pricePerMonth, int nMonths) throws DateRangeException{
		super(deposit, startDate, host, house);
		this.pricePerMonth = pricePerMonth;
		this.nMonths = nMonths;
		
		if(nMonths <= 0) {
			throw new DateRangeException();
		}
	}
	
	/**
	 * Setter of the offer's price per month in case the administrator asks for
	 * changes on the offer.
	 *  
	 * @param d double with the new offer's new price per month.
	 * @throws NotAvailableOfferException If the offer is not waiting to be changed.
	 */
	public void setPricePerMonth(double d) throws NotAvailableOfferException {
		if(this.getStatus() != TO_CHANGE && this.getStatus() != WAITING) {
			throw new NotAvailableOfferException();
		}
		
		this.pricePerMonth = d;
		this.setWaiting();
	}
	
	/**
	 * Setter of the offer's number of months in case the administrator asks for
	 * changes on the offer.
	 *  
	 * @param n int with the offer's new number of months.
	 * @throws NotAvailableOfferException If the offer is not waiting to be changed.
	 */
	public void setNumberMonths(int n) throws NotAvailableOfferException {
		if(this.getStatus() != TO_CHANGE && this.getStatus() != WAITING) {
			throw new NotAvailableOfferException();
		}
		
		this.nMonths = n;
		this.setWaiting();
	}
	
	/**
	 * Compare two LivingOffer to know if they are the same one.
	 * 
	 * @param ob Object we want to compare, must be a LivingOffer.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof LivingOffer)) {
			return false;
		}
		
		LivingOffer h = (LivingOffer) ob;
		return super.equals(h) && this.pricePerMonth == h.pricePerMonth
				&& this.nMonths == h.nMonths;
	}
	
	/**
	 * Return the commissions an offer has.
	 * For living offers is 0.1% of the offer total price.
	 * @return double of the offer commissions.
	 */
	public double commissions() {
		return 0.001;
	}
	
	/**
	 * Return the total price of a living offer.
	 * 
	 * @return double with the total price the user needs to pay.
	 */
	public double getPrice() {
		return this.pricePerMonth + this.getDeposit();
	}
	
	/**
	 * Returns the price per month of a living offer.
	 * 
	 * @return offer's price per month.
	 */
	public double getPricePerMonth() {
		return this.pricePerMonth;
	}
	
	/**
	 * Returns the number of months of a living offer.
	 * 
	 * @return number of months.
	 */
	public int getNumberMonths() {
		return this.nMonths;
	}
	
	/**
	 * Method that allow us to get a simple offer name.
	 * @return String with the offer name.
	 */
	@Override
	public String getName() {
		return "Living offer in house "+this.getHouse().getId()+".";
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
		if(logged) {
			result += "Deposit:\t\t" + this.getDeposit()+"\n";
			result += "Price per month:\t" + (this.getPrice()-this.getDeposit())+"\n";
		}
		
		return result;
	}
}
