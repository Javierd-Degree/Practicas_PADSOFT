package Offer;

import java.io.Serializable;
import java.time.LocalDate;

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
	
	/**
	 * Constructor of the LivingOffer class.
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
		
		/*TODO Anadir al array del usuario y del sistema, al crearlo, dentro de System*/
	}
	
	/**
	 * Compare two LivingOffer to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be a LivingOffer.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof LivingOffer)) {
			return false;
		}
		
		LivingOffer h = (LivingOffer) ob;
		return super.equals(h) && this.pricePerMonth == h.pricePerMonth;
	}
	
	/**
	 * Return the total price of a holiday offer.
	 * 
	 * @return double with the total price the user needs to pay.
	 */
	public double getPrice() {
		return this.pricePerMonth + this.getDeposit();
	}
}
