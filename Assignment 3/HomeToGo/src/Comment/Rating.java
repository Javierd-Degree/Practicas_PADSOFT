package Comment;

import java.io.Serializable;
import User.RegisteredUser;

/**
 * Class which implements a rating comment a user can make on an Offer,
 * using just an int.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class Rating extends Comment implements Serializable{
	private static final long serialVersionUID = 8557705144350519069L;
	private int rating;
	private RegisteredUser user;
	
	/**
	 * Constructor of the Rating class.
	 * In this case, the rating has no limits, it goes from
	 * -2 147 483 648 to +2 147 483 647. The limits are
	 * imposed on Offer.
	 * 
	 * @param rating
	 * @param user
	 */
	public Rating(int rating, RegisteredUser user) {
		this.rating = rating;
		this.user = user;
	}
	
	/**
	 * Getter of the rating.
	 * 
	 * @return int with the rating.
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * Returns the Rating as a String
	 * so that it can be shown.
	 * 
	 * @return String with the Rating info.
	 */
	@Override
	public String toString() {
		
		return this.user.getName() +": " + getRating();
	}
	
	/**
	 * Compare two Rating to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be a Rating.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Rating)) {
			return false;
		}
		
		Rating c = (Rating) o;
		return this.user.equals(c.user) && this.rating == c.rating;
	}
}
