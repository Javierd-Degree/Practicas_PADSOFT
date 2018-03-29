package Offer;

import java.io.Serializable;
import java.time.LocalDate;

import House.House;
import User.RegisteredUser;

public class LivingOffer extends Offer implements Serializable{
	private static final long serialVersionUID = 6202450079263161446L;
	private double pricePerMonth;
	
	public LivingOffer(double deposit, LocalDate startDate, RegisteredUser host, House house, double pricePerMonth) {
		super(deposit, startDate, host, house);
		this.pricePerMonth = pricePerMonth;
		
		/*TODO Anadir al array del usuario y del sistema, al crearlo, dentro de System*/
	}
	
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof LivingOffer)) {
			return false;
		}
		
		LivingOffer h = (LivingOffer) ob;
		return super.equals(h) && this.pricePerMonth == h.pricePerMonth;
	}
	
	public double getPrice() {
		return this.pricePerMonth + this.getDeposit();
	}
}
