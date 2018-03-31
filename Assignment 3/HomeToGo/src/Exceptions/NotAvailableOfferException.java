package Exceptions;

/**
 * Exception used to notify that the user who wants to buy an offer
 * is not the same who reserved it, for example, or that the offer is
 * not available to buy/reserve.
 *
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class NotAvailableOfferException extends Exception{
	private static final long serialVersionUID = -327091315929793748L;
	
	/**
	 * Method that allows us to print information about the exception.
	 * 
	 * @return String with the exception's information.
	 */
	@Override
	public String toString() {
		return "Offer not available for this user";
	}
}
