package views;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import Offer.Offer;

public class SearchResultsView extends JPanel{
	
	private static final long serialVersionUID = 30014404710481924L;
	
	private boolean logged;
	private List<Offer> offers;
	private JList<Offer> offersList;
	
	public SearchResultsView(List<Offer> offers, boolean logged) {
		super();
		this.logged = logged;
		this.offers = offers;
		
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
        add(new JLabel("Search results:\n"), BorderLayout.NORTH);
        // Create list and set to scrollpane and add to panel
        add(new JScrollPane((offersList = createListOffers())),
                BorderLayout.CENTER);
	}
 
    private JList<Offer> createListOffers() {
        // Create List model
        DefaultListModel<Offer> model = new DefaultListModel<>();
        // Add item to model
        for(Offer o: offers) {
        	model.addElement(o);
        }
        // Create JList with model
        JList<Offer> list = new JList<Offer>(model);
        // Set cell renderer 
        list.setCellRenderer(new OfferRenderer(this.logged));
        return list;
    }
    
    public boolean getLogged() {
    	return this.logged;
    }
    
    public JList<Offer> getList(){
    	return this.offersList;
    }
    
    public void setController(ListSelectionListener c) {
    	offersList.addListSelectionListener(c);
	}
}
