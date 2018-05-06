package Exceptions;

/**
 * Exception used to notify that the start date and
 * end date of a holiday offer does not make sense.
 *
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class DateRangeException extends Exception{
	private static final long serialVersionUID = -7873901013555643021L;

	/**
	 * Method that allows us to print information about the exception.
	 * 
	 * @return String with the exception's information.
	 */
	@Override
	public String toString() {
		return "The offer's start and end dates do not make sense.";
	}
}
