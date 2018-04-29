package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

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
	
	private boolean logged;
	private JLabel nameLabelText;
	private JLabel charsTitle;
    private JLabel charsLabelText;
    private JTextArea infoLabelText;
 
    public OfferRenderer(boolean logged) {
    	super();
    	this.logged = logged;
        setLayout(new BorderLayout(2, 2));
        
        nameLabelText = new JLabel();
        charsTitle = new JLabel("Characteristics: ");
        charsLabelText = new JLabel();
        infoLabelText = new JTextArea();
        
        /*Put some margin on the name*/
        nameLabelText.setBorder(new EmptyBorder(12, 0, 0, 0));
        
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
        
        add(nameLabelText, BorderLayout.NORTH);
        add(charsPanel, BorderLayout.SOUTH);
        add(infoLabelText, BorderLayout.EAST);
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends Offer> list,
    		Offer offer, int index, boolean isSelected, boolean cellHasFocus) {
 
    	nameLabelText.setText(offer.getName());
    	nameLabelText.setForeground(Color.decode("#82B1FF"));
    	if(this.logged) {
    		if(offer.getStatus() == Offer.AVAILABLE) {
        		nameLabelText.setText(offer.getName()+" (Available)");
            	nameLabelText.setForeground(Color.decode("#33CC00"));
        	}else {
        		nameLabelText.setText(offer.getName()+" (Not available)");
            	nameLabelText.setForeground(Color.decode("#FF6600"));
        	}
    	}
    	
    	//System.out.println(offer.getName()+": "+offer.getHouse().getCharacteristics().size());
    	this.charsLabelText.setText(offer.getHouse().getHouseCharsStyled());
    	
    	this.infoLabelText.setText(offer.getInfo(this.logged));
    	
    	
    	
    	// set Opaque to change background color of JLabel
    	nameLabelText.setOpaque(true);
    	charsTitle.setOpaque(true);
    	charsLabelText.setOpaque(true);
    	infoLabelText.setOpaque(true);
     
        // when select item
        if (isSelected) {
        	nameLabelText.setBackground(list.getSelectionBackground());
        	charsTitle.setBackground(list.getSelectionBackground());
        	charsLabelText.setBackground(list.getSelectionBackground());
        	infoLabelText.setBackground(list.getSelectionBackground());
            setBackground(list.getSelectionBackground());
        } else { // when don't select
        	nameLabelText.setBackground(list.getBackground());
        	charsTitle.setBackground(list.getBackground());
        	charsLabelText.setBackground(list.getBackground());
        	infoLabelText.setBackground(list.getBackground());
            setBackground(list.getBackground());
        }
        
        /*We need to delimit the cells*/
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
 
        return this;
    }
}