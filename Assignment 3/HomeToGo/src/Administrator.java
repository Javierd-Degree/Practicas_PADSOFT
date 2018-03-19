
public class Administrator {
	private int id;
	private String name;
	private String surname;
	private String password;
	
	
	public Administrator(int id, String name, String surname, String password) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.password = password;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
