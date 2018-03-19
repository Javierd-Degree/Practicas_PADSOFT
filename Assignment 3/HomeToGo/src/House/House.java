package House;
import java.util.List;

public class House {
	private String id;
	private List<Characteristic> characteristics;
	
	
	public House(String id, List<Characteristic> characteristics) {
		this.id = id;
		this.characteristics = characteristics;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(List<Characteristic> characteristics) {
		this.characteristics = characteristics;
	}
	
	
	public void addCharacteristic(Characteristic c) {
		this.characteristics.add(c);
	}
	
	public void removeCharacteristic(Characteristic c) {
		for(int i = 0; i < this.characteristics.size(); i++) {
			if(this.characteristics.get(i) == c) {
				this.characteristics.remove(i);
			}
		}
	}
	
	
	
}
