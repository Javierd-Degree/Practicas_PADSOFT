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
	
	

}
