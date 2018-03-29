package Offer;

import java.io.Serializable;
import java.util.Date;

import House.House;
import User.RegisteredUser;

public class HolidayOffer extends Offer implements Serializable{
	private static final long serialVersionUID = -3546137102202274577L;
	private Date endDate;
	private double totalPrice;
	
	public HolidayOffer(double deposit, Date startDate, RegisteredUser host, House house, Date endDate, double totalPrice) {
		super(deposit, startDate, host, house);
		this.endDate = endDate;
		this.totalPrice = totalPrice;
		/*TODO Anadir al array del usuario y del sistema  al crearlo, dentro de System*/
	}
	
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof HolidayOffer)) {
			return false;
		}
		
		HolidayOffer h = (HolidayOffer) ob;
		return super.equals(h) && this.totalPrice == h.totalPrice && this.endDate.equals(h.endDate);
	}
}
