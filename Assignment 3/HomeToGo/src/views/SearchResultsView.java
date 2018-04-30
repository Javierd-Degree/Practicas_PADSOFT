package views;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import Offer.Offer;

public class SearchResultsView extends JPanel{
	
	private static final long serialVersionUID = 30014404710481924L;
	
	private int mode;
	private List<Offer> offers;
	private JList<Offer> offersList;
	
	public static final int NOT_LOGGED = -1;
	public static final int LOGGED_SEARCH = 0;
	public static final int HOST_CREATED = 1;
	public static final int GUEST_HISTORY = 2;
	public static final int ADMINISTRATOR = 3;
	
	public SearchResultsView(List<Offer> offers, int mode) {
		super();
		this.mode = mode;
		this.offers = offers;
		
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Offers"));
        // Create list and set to scrollpane and add to panel
        add(new JScrollPane((offersList = createListOffers())),
                BorderLayout.CENTER);
	}
 
    private JList<Offer> createListOffers() {
        // Create List model
        DefaultListModel<Offer> model = new DefaultListModel<>();
        
        if(mode == ADMINISTRATOR) {
        	for(Offer o: offers) {
        		if(o.getStatus() == Offer.WAITING) {
        			model.addElement(o);
        		}
            }
        }else if(mode == NOT_LOGGED || mode == LOGGED_SEARCH) {
        	for(Offer o: offers) {
        		if(o.getStatus() != Offer.DENIED && o.getStatus() != Offer.TO_CHANGE 
        				&& o.getStatus() != Offer.WAITING) {
        			model.addElement(o);
        		}
            }
        }else if(mode == HOST_CREATED || mode == GUEST_HISTORY) {
        	for(Offer o: offers) {
            	model.addElement(o);
            }
        }
        
        // Create JList with model
        JList<Offer> list = new JList<Offer>(model);
        // Set cell renderer 
        list.setCellRenderer(new OfferRenderer(this.mode));
        return list;
    }
    
    public int getMode() {
    	return this.mode;
    }
    
    public JList<Offer> getList(){
    	return this.offersList;
    }
    
    public void setController(ListSelectionListener c) {
    	offersList.addListSelectionListener(c);
	}
}
