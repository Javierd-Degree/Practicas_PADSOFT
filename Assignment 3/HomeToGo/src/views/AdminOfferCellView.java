package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import Offer.Offer;

public class AdminOfferCellView extends JPanel{

	private static final long serialVersionUID = 666664455051002349L;
	
	private Offer offer;
	private JPanel parent;
	
	private JLabel nameLabelText;
	private JLabel charsTitle;
    private JLabel charsLabelText;
    private JTextArea infoLabelText;
    
    private JPanel adminPanel;
    private JButton approveButton;
    private JButton denyButton;
    private JButton askChangesButton;
	
	public AdminOfferCellView(Offer offer, JPanel parent) {
		super();
		this.offer = offer;
		this.parent = parent;
		this.setBackground(Color.WHITE);
		
		setLayout(new BorderLayout(2, 2));
        
        nameLabelText = new JLabel();
        charsTitle = new JLabel("Characteristics: ");
        charsLabelText = new JLabel();
        infoLabelText = new JTextArea();
        infoLabelText.setEditable(false);
        
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
        infoLabelText.setOpaque(false);
        
        JPanel charsPanel = new JPanel(new GridLayout(0, 1));        
        charsPanel.add(charsTitle);
        charsPanel.add(charsLabelText);
        charsPanel.setBackground(Color.WHITE);
        
        adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        adminPanel.setBackground(Color.WHITE);
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
        down.setBackground(Color.WHITE);
        down.add(charsPanel);
        down.add(adminPanel);
        
        add(nameLabelText, BorderLayout.NORTH);
        add(down, BorderLayout.SOUTH);
        add(infoLabelText, BorderLayout.EAST);
        
        
        /*Now we need to set up the layout content*/
        nameLabelText.setText(offer.getName());
    	nameLabelText.setForeground(Color.decode("#82B1FF"));
    	charsLabelText.setText(offer.getHouse().getHouseCharsStyled());
    	infoLabelText.setText(offer.getInfo(true));
    	setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	}
	
	public void setController(ActionListener c) {
		approveButton.addActionListener(c);
    	denyButton.addActionListener(c);
    	askChangesButton.addActionListener(c);
	}
	
	public Offer getOffer() {
		return this.offer;
	}
	
	public JPanel getParent() {
		return this.parent;
	}
	
	
}
