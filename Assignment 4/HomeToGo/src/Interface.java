
import Application.Administrator;
import Application.Application;
import Date.ModificableDate;
import User.RegisteredUser;
import controllers.LoginController;
import controllers.SearchController;
import views.LoginWindow;
import views.SearchView;

public class Interface {

	public static void main(String[] args) {
		/**Application app = Application.getInstance();
		app.login(app.getUsers().get(0).getId(), app.getUsers().get(0).getPassword());
		app.getOffers().get(0).postComment(app.getUsers().get(0), 5);
		app.getOffers().get(0).postComment(app.getUsers().get(0), "Not bad");
		app.logout();
		
		Application app = Application.getInstance();
		app.login(app.getUsers().get(1).getId(), app.getUsers().get(1).getPassword());
		*/
		
		ModificableDate.setToday();
		Application app = Application.getInstance();
		System.out.println("Administrators: ");
		for(Administrator a: app.getAdmins()) {
			System.out.println("ID: "+ a.getId() + ", PASS: " +a.getPassword());
		}
		
		System.out.println("\nUsers: ");
		for(RegisteredUser u: app.getUsers()) {
			System.out.println("ID: "+u.getId()+", PASS: "+u.getPassword()+", Type: "+u.getType()+", Status: "+u.getStatus());
		}
		
		LoginWindow login = new LoginWindow();
		LoginController cont = new LoginController(login);
		login.setController(cont);
		
		SearchView s = new SearchView(false);
		SearchController controller = new SearchController(s);
		s.setController(controller);
		login.setSecondaryView(s);
		
		login.setVisible(true);
	}
}