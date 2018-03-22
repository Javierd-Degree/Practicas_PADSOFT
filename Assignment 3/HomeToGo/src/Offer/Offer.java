package Offer;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import User.RegisteredUser;
import Comment.*;

public abstract class Offer {
	private double deposit;
	private int status;
	private Date startDate;
	private Date lastModifiedDate;
	private RegisteredUser host;
	private RegisteredUser guest;
	private List<Comment> comments;
	
	private final static int WAITING = 0;
	private final static int TO_CHANGE = -1;
	private final static int DENIED = -2;
	private final static int AVAILABLE = 1;
	private final static int RESERVED = 2;
	private final static int BOUGHT = 3;
	
	public Offer(double deposit, Date startDate, RegisteredUser host) {
		this.deposit = deposit;
		this.status = WAITING;
		this.startDate = startDate;
		this.lastModifiedDate = new Date();
		this.host = host;
		this.guest = null;
		this.comments = new ArrayList<>();
		
		/*TODO A�adir al array del usuario y del sistema*/
	}
	
	public void setLastModifiedDate(Date date) {
		this.lastModifiedDate = date;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void approveOffer() {
		this.status = AVAILABLE;
		this.lastModifiedDate = new Date();
	}
	
	public void denyOffer() {
		this.status = DENIED;
		this.lastModifiedDate = new Date();
	}
	
	public void askForChanges(String text) {
		ChangeComment comment = new ChangeComment(text);
		this.status = TO_CHANGE;
		this.lastModifiedDate = new Date();
		comments.add(comment);
	}
	
	public void reserveOffer(RegisteredUser guest) {
		this.status = RESERVED;
		this.lastModifiedDate = new Date();
		this.guest = guest;
		/*TODO
		 * Check if this works*/
		guest.addOffer(this, RegisteredUser.HIST_OFFER);
	}
	
	public void buyOffer(RegisteredUser guest) {
		this.status = BOUGHT;
		this.lastModifiedDate = new Date();
		this.guest = guest;
		/*TODO
		 * Check if this works
		 * CALL THE PAYMENT SYSTEM*/
		guest.addOffer(this, RegisteredUser.HIST_OFFER);
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
	
	public void postComment(RegisteredUser user, String text) {
		TextComment comment = new TextComment(text, user);
		this.comments.add(comment);
	}
	
	public void postComment(RegisteredUser user, int rating) {
		Rating comment = new Rating(rating, user);
		this.comments.add(comment);
	}
	
	public double getPrice() {
		return -1;
	}
	
	public RegisteredUser getHost() {
		return host;
	}
	
	public RegisteredUser getGuest() {
		return guest;
	}
	
	public boolean equals(Offer o) {
		
		return this.deposit == o.deposit && this.status == o.status &&
				this.startDate.equals(o.startDate) && this.lastModifiedDate.equals(o.lastModifiedDate)
				&& this.host.equals(o.host) && this.guest.equals(o.guest) && this.comments.equals(o.comments);
		
	}
}
