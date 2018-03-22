package House;
import java.util.Map;
import java.util.HashMap;

public class House {
	private String id;
	private Map<String, String> characteristics;
	
	
	public House(String id) {
		this.id = id;
		this.characteristics = new HashMap<>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getCharacteristics() {
		return characteristics;
	}
	public void addCharacteristic(String name, String value) {
		this.characteristics.put(name, value);
	}
	
	public void removeCharacteristic(String name) {
		this.characteristics.remove(name);
	}
	
	
	
}
