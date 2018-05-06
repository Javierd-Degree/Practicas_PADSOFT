package Comment;

import java.io.Serializable;

/**
 * Class which implements a comment the administrator can make
 * on an offer in order to let the host know what he would need
 * to change if he wants his offer to be approved.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class ChangeComment extends Comment implements Serializable{
	private static final long serialVersionUID = -814635662535770669L;
	private String text;
	
	/**
	 * Constructor of the ChangeComment class.
	 * 
	 * @param text String that the administrator wants to sends to the host.
	 */
	public ChangeComment(String text) {
		this.text = text;
	}

	/**
	 * Returns the ChangeComment as a String
	 * so that it can be shown.
	 * 
	 * @return String with the comment's text.
	 */
	@Override
	public String toString() {
		return text;
	}
	
	/**
	 * Method that returns the comment text, without the user.
	 * @return comment text.
	 */
	public String getComment() {
		return this.text;
	}
	
	/**
	 * Method that returns the name of the user who posted
	 * the comment.
	 * @return String with a user name.
	 */
	public String getUserName() {
		return "Administrator";
	}
	
	/**
	 * Compare two ChangeComment to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be a ChangeComment.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ChangeComment)) {
			return false;
		}
		
		ChangeComment c = (ChangeComment) o;
		return this.text.equals(c.text);
	}
}
