package Comment;
import User.RegisteredUser;

public class Rating extends Comment {
	private int rating;
	private RegisteredUser user;
	
	public Rating(int rating, RegisteredUser user) {
		this.rating = rating;
		this.user = user;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public RegisteredUser getUser() {
		return user;
	}
	
	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	
	
}
