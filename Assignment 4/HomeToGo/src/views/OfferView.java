package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Application.Application;
import Comment.ChangeComment;
import Comment.Comment;
import Date.ModificableDate;
import Offer.Offer;
import User.RegisteredUser;
import controllers.CommentCellController;

public class OfferView extends JPanel{

	private static final long serialVersionUID = -1542170420708668044L;
	
	private Offer offer;
	private int mode;
	private JLabel nameLabelText;
	private JLabel ratingLabelText;
	private JLabel charsTitle;
    private JLabel charsLabelText;
    private JTextArea infoLabelText;
    
    private JButton payButton;
    private JButton reserveButton;
    private JButton commentButton;
    private JButton rateButton;
    
    JPanel comments;
    JScrollPane commentScroll;
    
	public OfferView(Offer offer, int mode) {
		super();
		this.offer = offer;
		this.mode = mode;
		
		setLayout(new BorderLayout(10, 10));
		add(createOfferView(offer), BorderLayout.CENTER);
		add(createCommentView(offer), BorderLayout.SOUTH);
		
	}
	
	public JPanel createOfferView(Offer offer) {
		JPanel view = new JPanel();
		view.setBorder(BorderFactory.createTitledBorder("Offer information"));
		view.setLayout(new GridBagLayout());
		
        nameLabelText = new JLabel();
        ratingLabelText = new JLabel();
        charsTitle = new JLabel("Characteristics: ");
        charsLabelText = new JLabel();
        infoLabelText = new JTextArea();
        payButton = new JButton("Pay offer directly");
        payButton.setActionCommand("PAY");
        reserveButton = new JButton("Reserve offer");
        reserveButton.setActionCommand("RESERVE");
        
        /*Put some margin on the name*/
        nameLabelText.setBorder(new EmptyBorder(12, 0, 0, 0));
        nameLabelText.setFont(nameLabelText.getFont().deriveFont(24.0f));
        setOfferNameAndStyle(offer, nameLabelText, this.mode);
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(nameLabelText, BorderLayout.NORTH);
        upperPanel.add(ratingLabelText, BorderLayout.CENTER);
        if(offer.calculateRating() == -1) {
    		ratingLabelText.setText(" Rating: No available");
    	}else {
    		ratingLabelText.setText(" Rating: " + offer.calculateRating()+"/5");
    	}
        
        
        /*We do not want the characteristics text to be bold.*/
        Font f = charsLabelText.getFont();
        charsLabelText.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
        /*Put some bottom border*/
        charsLabelText.setBorder(new EmptyBorder(0, 0, 8, 0));
        charsLabelText.setText(offer.getHouse().getHouseCharsStyled());	
        
        /*We do not want the information text to be bold.*/
        infoLabelText.setOpaque(false);
        infoLabelText.setEditable(false);
        f = infoLabelText.getFont();
        infoLabelText.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
        /*Put some right border*/
        infoLabelText.setBorder(new EmptyBorder(0, 0, 0, 8));
        infoLabelText.setText(offer.getInfo(true));
        
        JPanel charsPanel = new JPanel(new GridLayout(0, 1));        
        charsPanel.add(charsTitle);
        charsPanel.add(charsLabelText);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        
        if(this.mode == SearchResultsView.LOGGED_SEARCH || this.mode == SearchResultsView.GUEST_HISTORY) {
        	Object o = Application.getInstance().searchLoggedIn();
            if(this.offer.getStatus() == Offer.RESERVED 
            		&& (o instanceof RegisteredUser) && ((RegisteredUser)o).equals(offer.getGuest())) {
            	payButton.setText("Pay offer");
            	buttonsPanel.add(payButton);
            }else if(this.offer.getStatus() == Offer.AVAILABLE) {
            	buttonsPanel.add(payButton);
            	buttonsPanel.add(reserveButton);
            }
        }
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 4, 0, 4);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        view.add(upperPanel, c);
        
        c.gridx = 2;
        c.weightx = 1.0;
        c.insets = new Insets(40, 4, 0, 4);
        c.anchor = GridBagConstraints.NORTHEAST;
        view.add(infoLabelText, c);
        
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0.0;
        c.insets = new Insets(0, 4, 0, 4);
        c.anchor = GridBagConstraints.SOUTHWEST;
        view.add(charsTitle, c);
        
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 2;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(8, 4, 0, 4);
        view.add(charsLabelText, c);
        
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.SOUTHEAST;
        view.add(buttonsPanel, c);
            
        return view;
	}
	
	public static void setOfferNameAndStyle(Offer offer, JLabel nameLabelText, int mode) {
		if(mode == SearchResultsView.LOGGED_SEARCH) {
    		if(offer.getStatus() == Offer.AVAILABLE) {
        		nameLabelText.setText(offer.getName()+" (Available)");
            	nameLabelText.setForeground(Color.decode("#33CC00"));
        	}else {
        		nameLabelText.setText(offer.getName()+" (Not available)");
            	nameLabelText.setForeground(Color.decode("#FF6600"));
        	}
    		
    	}else if(mode == SearchResultsView.GUEST_HISTORY) {
    		if(offer.getStartDate().isBefore(ModificableDate.getModifiableDate())) {
    			nameLabelText.setText(offer.getName()+" (Passed)");
            	nameLabelText.setForeground(Color.decode("#FF6600"));
    		}else if(offer.getStatus() == Offer.RESERVED) {
    			nameLabelText.setText(offer.getName()+" (Pending payment)");
            	nameLabelText.setForeground(Color.decode("#33CC00"));
    		}else {
    			nameLabelText.setText(offer.getName()+" (Paid)");
            	nameLabelText.setForeground(Color.decode("#33CC00"));
    		}
    		
    	}else if(mode == SearchResultsView.HOST_CREATED) {
    		if(offer.getStatus() == Offer.DENIED || offer.getStatus() == Offer.NOT_AVAILABLE) {
    			nameLabelText.setText(offer.getName()+" (Denied)");
            	nameLabelText.setForeground(Color.decode("#FF6600"));
            	
    		}else if(offer.getStatus() == Offer.WAITING) {
    			nameLabelText.setText(offer.getName()+" (Waiting)");
            	nameLabelText.setForeground(Color.decode("#33CC00"));
            	
    		}else if(offer.getStatus() == Offer.TO_CHANGE) {
    			nameLabelText.setText(offer.getName()+" (To change)");
            	nameLabelText.setForeground(Color.decode("#33CC00"));
            	
    		}else if(offer.getStartDate().isBefore(ModificableDate.getModifiableDate())) {
    			if(offer.getStatus() == Offer.BOUGHT) {
    				nameLabelText.setText(offer.getName()+" (Passed, sold)");
                	nameLabelText.setForeground(Color.decode("#FF6600"));
    			}else {
    				nameLabelText.setText(offer.getName()+" (Passed, not sold)");
                	nameLabelText.setForeground(Color.decode("#FF6600"));
    			}
    		}else if(offer.getStatus() == Offer.RESERVED) {
    			nameLabelText.setText(offer.getName()+" (Pending payment)");
            	nameLabelText.setForeground(Color.decode("#FF6600"));
    		}else if(offer.getStatus() == Offer.BOUGHT){
    			nameLabelText.setText(offer.getName()+" (Sold)");
            	nameLabelText.setForeground(Color.decode("#FF6600"));
    		}else {
    			nameLabelText.setText(offer.getName());
            	nameLabelText.setForeground(Color.decode("#33CC00"));
    		}
    	}
	}
	
	public JPanel createCommentView(Offer offer) {
		JPanel view = new JPanel();
		view.setMaximumSize(new Dimension(1280, 280));
		view.setPreferredSize(new Dimension(1280, 280));
		view.setBorder(BorderFactory.createTitledBorder("Comments/Ratings."));
		view.setLayout(new BorderLayout(6, 6));
		
		JPanel buttons = new JPanel(new FlowLayout());
		
		commentButton = new JButton("Comment");
		commentButton.setActionCommand("COMMENT");
		rateButton = new JButton("Rate");
		rateButton.setActionCommand("RATE");
		buttons.add(commentButton);
		buttons.add(rateButton);
		
		view.add(buttons, BorderLayout.EAST);
		
		//comments = new JPanel(new GridLayout(0, 1));
		comments = new JPanel();
		comments.setLayout(new BoxLayout(comments, BoxLayout.Y_AXIS));
		for(Comment c: this.offer.getComments()) {
        	if(!(c instanceof ChangeComment)) {
        		addComment(c, false);
        	}
        }
		
		commentScroll = new JScrollPane(comments);
		commentScroll.setMaximumSize(new Dimension(100, 100));
		view.add(commentScroll, BorderLayout.CENTER);
		
		return view;
	}
	
	public void addComment(Comment c, boolean update) {
		CommentCellView v = new CommentCellView(c, this.offer, comments);
		CommentCellController cont = new CommentCellController(v);
		v.setController(cont);
		comments.add(v);
		if(update) {
			comments.revalidate();
		}
	}
	
	public Offer getOffer() {
		return this.offer;
	}
	
	public void hidePayButton() {
		this.payButton.setVisible(false);
		this.repaint();
	}
	
	public void hideReserveButton() {
		this.reserveButton.setVisible(false);
		this.repaint();
	}
	
	public void setController(ActionListener c) {
		payButton.addActionListener(c);
		reserveButton.addActionListener(c);
		commentButton.addActionListener(c);
		rateButton.addActionListener(c);
	} 

}
