package Comment;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import User.RegisteredUser;

/**
 * Class which implements a comment a user can make on an Offer,
 * using just some text.
 * It also has a list of answers so that a TextComment can have
 * more TextComments as answers.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class TextComment extends Comment implements Serializable{
	private static final long serialVersionUID = -1027844935905198927L;
	private String text;
	private RegisteredUser user;
	private List<TextComment> answers;
	
	/**
	 * Contructor of the class TextComent
	 * 
	 * @param text String with the text the user wants to post.
	 * @param user RegisteredUser who posts the comment.
	 */
	public TextComment(String text, RegisteredUser user) {
		super();
		this.text = text;
		this.user = user;
		this.answers = new ArrayList<>();
	}

	/**
	 * Returns the TextComment as a String
	 * so that it can be shown.
	 * 
	 * @return String with the comment's text.
	 */
	@Override
	public String toString() {
		String result = this.user.getName() +": " +this.text;
		for(TextComment t: this.answers) {
			result += "\n\t" +t ;
		}
		
		return result;
	}
	
	/**
	 * Method that returns the comment text, without the user.
	 * @return comment text.
	 */
	public String getComment() {
		return this.text;
	}
	
	/**
	 * Method that returns the list of answers to the comment.
	 * 
	 * @return List with the answers.
	 */
	public List<TextComment> getAnswers(){
		return this.answers;
	}
	
	/**
	 * Method that returns the name of the user who posted
	 * the comment.
	 * @return String with a user name.
	 */
	public String getUserName() {
		return this.user.getName();
	}
	
	/**
	 * Answer a TextComment.
	 * 
	 * @param text Text the user wants to answer.
	 * @param user RegisteredUser who wants to answer.
	 */
	public void answerComment(String text, RegisteredUser user) {
		TextComment answer = new TextComment(text, user);
		this.answers.add(answer);
	}
	
	/**
	 * Compare two TextComment to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be a TextComment.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof TextComment)) {
			return false;
		}
		
		TextComment c = (TextComment) o;
		return this.text.equals(c.text) && this.user.equals(c.user)
				&& this.answers.equals(c.answers);
	}
}