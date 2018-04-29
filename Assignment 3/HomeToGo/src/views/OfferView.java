package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Comment.ChangeComment;
import Comment.Comment;
import Offer.Offer;
import controllers.CommentAnswerController;

public class OfferView extends JPanel{

	private static final long serialVersionUID = -1542170420708668044L;
	
	private Offer offer;
	private JLabel nameLabelText;
	private JLabel charsTitle;
    private JLabel charsLabelText;
    private JTextArea infoLabelText;
    
    private JButton payButton;
    private JButton reserveButton;
    private JButton commentButton;
    private JButton rateButton;
    
	public OfferView(Offer offer) {
		super();
		this.offer = offer;
		
		setLayout(new BorderLayout(10, 10));
		add(createOfferView(offer), BorderLayout.CENTER);
		add(createCommentView(offer), BorderLayout.SOUTH);
		
	}
	
	public JPanel createOfferView(Offer offer) {
		JPanel view = new JPanel();
		view.setLayout(new BorderLayout(2, 2));
        
        nameLabelText = new JLabel();
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
        if(offer.getStatus() == Offer.AVAILABLE) {
    		nameLabelText.setText(offer.getName()+" (Available)");
        	nameLabelText.setForeground(Color.decode("#33CC00"));
    	}else {
    		nameLabelText.setText(offer.getName()+" (Not available)");
        	nameLabelText.setForeground(Color.decode("#FF6600"));
    	}
        
        /*We do not want the characteristics text to be bold.*/
        Font f = charsLabelText.getFont();
        charsLabelText.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
        /*Put some bottom border*/
        charsLabelText.setBorder(new EmptyBorder(0, 0, 8, 0));
        charsLabelText.setText(offer.getHouse().getHouseCharsStyled());	
        
        /*We do not want the information text to be bold.*/
        infoLabelText.setOpaque(false);
        f = infoLabelText.getFont();
        infoLabelText.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
        /*Put some right border*/
        infoLabelText.setBorder(new EmptyBorder(0, 0, 0, 8));
        infoLabelText.setText(offer.getInfo(true));
        
        JPanel charsPanel = new JPanel(new GridLayout(0, 1));        
        charsPanel.add(charsTitle);
        charsPanel.add(charsLabelText);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        if(this.offer.getStatus() == Offer.RESERVED) {
        	buttonsPanel.add(payButton);
        }else if(this.offer.getStatus() == Offer.AVAILABLE) {
        	buttonsPanel.add(payButton);
        	buttonsPanel.add(reserveButton);
        }
        
        view.add(nameLabelText, BorderLayout.NORTH);
        view.add(charsPanel, BorderLayout.CENTER);
        view.add(buttonsPanel, BorderLayout.SOUTH);
        view.add(infoLabelText, BorderLayout.EAST);
            
        return view;
	}
	
	
	public JPanel createCommentView(Offer offer) {
		JPanel view = new JPanel();
		view.setLayout(new BorderLayout(6, 6));
		
		JPanel buttons = new JPanel(new FlowLayout());
		
		commentButton = new JButton("Comment");
		commentButton.setActionCommand("COMMENT");
		rateButton = new JButton("Rate");
		rateButton.setActionCommand("RATE");
		buttons.add(commentButton);
		buttons.add(rateButton);
		
		
		view.add(new JLabel("Comments/Rating: "), BorderLayout.NORTH);
		view.add(buttons, BorderLayout.EAST);
		view.add(new JScrollPane(createListComments()),
                BorderLayout.CENTER);
		
		return view;
	}
	
	private JList<Comment> createListComments() {
        // Create List model
        DefaultListModel<Comment> model = new DefaultListModel<>();
        // Add item to model
        for(Comment c: this.offer.getComments()) {
        	if(!(c instanceof ChangeComment)) {
        		model.addElement(c);
        	}
        }
        // Create JList with model
        JList<Comment> list = new JList<>(model);
        
        // Set cell renderer 
        CommentRenderer customRenderer = new CommentRenderer(this.offer);
        list.setCellRenderer(customRenderer);
        
        CommentAnswerController controller = new CommentAnswerController(customRenderer);
        customRenderer.setController(controller);
        
        return list;
    }
	
	public Offer getOffer() {
		return this.offer;
	}
	
	public void setController(ActionListener c) {
		payButton.addActionListener(c);
		reserveButton.addActionListener(c);
		commentButton.addActionListener(c);
		rateButton.addActionListener(c);
	} 

}
