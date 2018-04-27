
import controllers.LoginController;
import views.LoginWindow;

public class Interface {

	public static void main(String[] args) {
		//Application app = Application.getInstance();
		LoginWindow login = new LoginWindow();
		LoginController cont = new LoginController(login);
		login.setController(cont);
		login.setVisible(true);
	}
}
