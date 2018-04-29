
import Application.Application;
import controllers.LoginController;
import views.LoginWindow;

public class Interface {

	public static void main(String[] args) {
		/**Application app = Application.getInstance();
		app.login(app.getUsers().get(0).getId(), app.getUsers().get(0).getPassword());
		app.getOffers().get(0).postComment(app.getUsers().get(0), 5);
		app.getOffers().get(0).postComment(app.getUsers().get(0), "Not bad");
		app.logout();*/
		
		Application app = Application.getInstance();
		app.login(app.getUsers().get(0).getId(), app.getUsers().get(0).getPassword());
		
		LoginWindow login = new LoginWindow();
		LoginController cont = new LoginController(login);
		login.setController(cont);
		login.setVisible(true);
	}
}