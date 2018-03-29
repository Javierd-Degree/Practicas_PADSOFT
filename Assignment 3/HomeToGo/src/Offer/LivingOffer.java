package Offer;

import java.io.Serializable;
import java.util.Date;

import House.House;
import User.RegisteredUser;

public class LivingOffer extends Offer implements Serializable{
	private static final long serialVersionUID = 6202450079263161446L;
	private double pricePerMonth;
	
	public LivingOffer(double deposit, Date startDate, RegisteredUser host, House house, double pricePerMonth) {
		super(deposit, startDate, host, house);
		this.pricePerMonth = pricePerMonth;
		
		/*TODO Anadir al array del usuario y del sistema*/
	}
}
