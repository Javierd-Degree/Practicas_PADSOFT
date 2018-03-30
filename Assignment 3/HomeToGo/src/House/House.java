package House;
import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;

public class House implements Serializable{
	private static final long serialVersionUID = 2184888442338750600L;
	private String id;
	private Map<String, String> characteristics;
	
	
	public House(String id) {
		this.id = id;
		this.characteristics = new HashMap<>();
	}
	
	public String getId() {
		return id;
	}
	
	public Map<String, String> getCharacteristics() {
		return characteristics;
	}
	
	public boolean addCharacteristic(String name, String value) {
		if(this.characteristics.containsKey(name)) {
			return false;
		}
		
		this.characteristics.put(name, value);
		return true;
	}
	
	public boolean removeCharacteristic(String name) {
		if(this.characteristics.containsKey(name)) {
			this.characteristics.remove(name);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof House)) {
			return false;
		}
		House h = (House) o;
		
		return  this.id.equals(h.id) && this.characteristics.equals(h.characteristics);
	}
	
}
