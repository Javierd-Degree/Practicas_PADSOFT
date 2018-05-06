package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import Offer.Offer;
 
public class OfferRenderer extends JPanel implements ListCellRenderer<Offer> {
 
	private static final long serialVersionUID = 7468273940233272052L;

	private int mode;
	private JLabel nameLabelText;
	private JLabel ratingLabelText;
	private JLabel charsTitle;
    private JLabel charsLabelText;
    private JTextArea infoLabelText;
    
    private JPanel adminPanel;
    private JButton approveButton;
    private JButton denyButton;
    private JButton askChangesButton;
 
    public OfferRenderer(int mode) {
    	super();
    	this.mode = mode;
        setLayout(new BorderLayout(2, 2));
        
        nameLabelText = new JLabel();
        ratingLabelText = new JLabel();
        charsTitle = new JLabel("Characteristics: ");
        charsLabelText = new JLabel();
        infoLabelText = new JTextArea();
        infoLabelText.setEditable(false);
        
        /*Put some margin on the name*/
        nameLabelText.setBorder(new EmptyBorder(12, 0, 0, 0));
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(nameLabelText, BorderLayout.NORTH);
        upperPanel.add(ratingLabelText, BorderLayout.CENTER);
        
        /*We do not want the characteristics text to be bold.*/
        Font f = charsLabelText.getFont();
        charsLabelText.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
        /*Put some bottom border*/
        charsLabelText.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        /*We do not want the information text to be bold.*/
        f = infoLabelText.getFont();
        infoLabelText.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
        /*Put some right border*/
        infoLabelText.setBorder(new EmptyBorder(0, 0, 0, 8));
        
        JPanel charsPanel = new JPanel(new GridLayout(0, 1));        
        charsPanel.add(charsTitle);
        charsPanel.add(charsLabelText);
        
        adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        approveButton = new JButton("Approve offer");
        approveButton.setActionCommand("APPROVE");
        askChangesButton = new JButton("Ask for changes");
        askChangesButton.setActionCommand("ASK_CHANGES");
        denyButton = new JButton("Deny offer");
        denyButton.setActionCommand("DENY");
        adminPanel.add(approveButton);
        adminPanel.add(askChangesButton);
        adminPanel.add(denyButton);
        
        JPanel down = new JPanel(new GridLayout(0, 1));
        down.add(charsPanel);
        if(mode == SearchResultsView.ADMINISTRATOR) {
        	down.add(adminPanel);
        }
        
        add(upperPanel, BorderLayout.NORTH);
        add(down, BorderLayout.SOUTH);
        add(infoLabelText, BorderLayout.EAST);
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends Offer> list,
    		Offer offer, int index, boolean isSelected, boolean cellHasFocus) {
 
    	/*If the user is not logged in, or if it is an administrator,
    	 * he just see the offer name.*/
    	nameLabelText.setText(offer.getName());
    	nameLabelText.setForeground(Color.decode("#82B1FF"));
    	OfferView.setOfferNameAndStyle(offer, nameLabelText, this.mode);
    	
    	if(offer.calculateRating() == -1) {
    		ratingLabelText.setText(" Rating: No available");
    	}else {
    		ratingLabelText.setText(" Rating: " + offer.calculateRating()+"/5");
    	}
    	
    	//System.out.println(offer.getName()+": "+offer.getHouse().getCharacteristics().size());
    	this.charsLabelText.setText(offer.getHouse().getHouseCharsStyled());
    	
    	if(mode != SearchResultsView.NOT_LOGGED) {
    		this.infoLabelText.setText(offer.getInfo(true));
    	}else {
    		this.infoLabelText.setText(offer.getInfo(false));
    	}

    	// set Opaque to change background color of JLabel
    	nameLabelText.setOpaque(true);
    	ratingLabelText.setOpaque(true);
    	charsTitle.setOpaque(true);
    	charsLabelText.setOpaque(true);
    	infoLabelText.setOpaque(true);
    	adminPanel.setOpaque(true);
     
        // when select item
        if (isSelected) {
        	nameLabelText.setBackground(list.getSelectionBackground());
        	ratingLabelText.setBackground(list.getSelectionBackground());
        	charsTitle.setBackground(list.getSelectionBackground());
        	charsLabelText.setBackground(list.getSelectionBackground());
        	infoLabelText.setBackground(list.getSelectionBackground());
        	adminPanel.setBackground(list.getSelectionBackground());
            setBackground(list.getSelectionBackground());
        } else { // when don't select
        	nameLabelText.setBackground(list.getBackground());
        	ratingLabelText.setBackground(list.getBackground());
        	charsTitle.setBackground(list.getBackground());
        	charsLabelText.setBackground(list.getBackground());
        	infoLabelText.setBackground(list.getBackground());
        	adminPanel.setBackground(list.getBackground());
            setBackground(list.getBackground());
        }
        
        /*We need to delimit the cells*/
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
 
        return this;
    }
}