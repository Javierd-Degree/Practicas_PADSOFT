package House;
import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Class which implements a house as an id and a set of characteristics.
 * 
 * @author Javier Delgado del Cerro
 * @author Javier Lopez Cano
 */
public class House implements Serializable{
	private static final long serialVersionUID = 2184888442338750600L;
	private String id;
	private Map<String, String> characteristics;
	
	/**
	 * Constructor of the House class.
	 * 
	 * @param id String with the unique id of the created house.
	 */
	public House(String id) {
		this.id = id;
		this.characteristics = new HashMap<>();
	}
	
	/**
	 * Getter of the house's id.
	 * @return String with unique id of the house.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Getter of the house's set of characteristics.
	 * Used to display all those characteristics on the
	 * graphical user interface.
	 * 
	 * @return a Map<String, String> with the name and value 
	 * of the house's characteristics.
	 */
	public Map<String, String> getCharacteristics() {
		return characteristics;
	}
	
	/**
	 * Add a characteristic to a house.
	 * 
	 * @param name String that describes the characteristic.
	 * @param value String with the value of the characteristic.
	 * 
	 * @return boolean false the house already has a characteristic
	 * with the same name, true otherwise.
	 */
	public boolean addCharacteristic(String name, String value) {
		if(this.characteristics.containsKey(name)) {
			return false;
		}
		
		this.characteristics.put(name, value);
		return true;
	}
	
	/**
	 * Remove a characteristic from a house given its name.
	 * 
	 * @param name String with the name of the characteristic to be removed.
	 * @return boolean true if it was successfully removed, false if was not
	 * on the house's characteristics.
	 */
	public boolean removeCharacteristic(String name) {
		if(this.characteristics.containsKey(name)) {
			this.characteristics.remove(name);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the House as a String
	 * so that it can be shown.
	 * 
	 * @return String with the House's data.
	 */
	@Override
	public String toString() {
		String result = "House with ID: " + this.id + "\n" + this.characteristics;
		return result;
	}
	
	/**
	 * Compare two House to know if they are the same one.
	 * 
	 * @param o Object we want to compare, must be a House.
	 * @return boolean true if they are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof House)) {
			return false;
		}
		House h = (House) o;
		
		return  this.id.equals(h.id);
	}
	
}
