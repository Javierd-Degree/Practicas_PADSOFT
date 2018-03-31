package Offer;

import java.io.Serializable;
import java.time.LocalDate;

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
	 * @param enDate LocalDate which indicates when the offer ends.
	 * @param totalPrice double with the total price the offer costs.
	 */
	public HolidayOffer(double deposit, LocalDate startDate, RegisteredUser host, House house, LocalDate endDate, double totalPrice) {
		super(deposit, startDate, host, house);
		this.endDate = endDate;
		this.totalPrice = totalPrice;
		/*TODO Anadir al array del usuario y del sistema  al crearlo, dentro de System*/
	}
	
	/**
	 * Compare two HolidayOffer to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be a HolidayOffer.
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
	 * Return the total price of a holiday offer.
	 * 
	 * @return double with the total price the user needs to pay.
	 */
	public double getPrice() {
		return this.totalPrice + this.getDeposit();
	}
}
