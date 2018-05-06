package Application;
import java.io.Serializable;


/**
 * Class which implements a system administrator, which has an id,
 * name, surname and password.
 * The administrator is not a Registered user because it would work
 * completely different on the graphical user interface.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class Administrator implements Serializable{
	private static final long serialVersionUID = -5008330488164364504L;
	private String id;
	private String name;
	private String surname;
	private String password;
	private boolean logged;
	
	/**
	 * Constructor of the Administrator class.
	 * @param id Unique id of the administrator.
	 * @param name String with the administrator's name.
	 * @param surname String with the administrator's surname.
	 * @param password String with the administrator's password.
	 */
	public Administrator(String id, String name, String surname, String password) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.logged = false;
	}
	
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getLogged() {
		return this.logged;
	}
	
	public void changeLogged(boolean logged) {
		this.logged = logged;
	}
	
	/**
	 * Method that turns the administrator's data into a string so that it can be printed.
	 * @return String with the administrator's data.
	 */
	@Override
	public String toString() {
		return this.name + " " + this.surname + ", is an administrator with ID: " + this.id;
	}
	
	/**
	 * Compare two Administrators to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be an Administrator.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Administrator)) {
			return false;
		}
		
		Administrator admin = (Administrator) o;
		return this.id.equals(admin.id);
	}
	
	

}
