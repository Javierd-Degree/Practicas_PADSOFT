package Comment;

import java.io.Serializable;

/**
 * Abstract class which implements a comment.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public abstract class Comment implements Serializable{
	private static final long serialVersionUID = -2299846560027428850L;

	/**
	 * Void constructor of the Comment class.
	 */
	public Comment() {
		
	}
	
	/**
	 * Abstract method that would compare two Comment to know if 
	 * they are the same one.
	 * 
	 * @param o Object we want to compare, must be a Comment.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public abstract boolean equals(Object o);
	
	/**
	 * Method that provides a simple String for a comment.
	 * @return String with the comment content.
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Method that returns the comment text, without the user.
	 * @return comment text.
	 */
	public abstract String getComment();
	
	/**
	 * Method that returns the name of the user who posted
	 * the comment.
	 * @return String with a user name.
	 */
	public abstract String getUserName();
}
