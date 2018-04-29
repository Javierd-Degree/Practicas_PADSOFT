package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EtchedBorder;

import Comment.Comment;
import Comment.TextComment;
import Offer.Offer;


public class CommentRenderer extends JPanel implements ListCellRenderer<Comment>{

	private static final long serialVersionUID = 7948855521108789151L;

	/*All the comments showed at a moment must be from the same offer*/
	private static Offer offer;
	private Comment comment;
	private JLabel nameLabel;
	private JLabel commentLabel;
	private JButton answerButton;
	
	public CommentRenderer(Offer offer) {
		super();
		setLayout(new BorderLayout(4, 4));
		
		CommentRenderer.offer = offer;
		
		nameLabel = new JLabel();
		commentLabel = new JLabel();
		answerButton = new JButton("Aswer");
		answerButton.setActionCommand("ANSWER_COMMENT");
		
		add(nameLabel, BorderLayout.NORTH);
		add(commentLabel, BorderLayout.CENTER);
		add(answerButton, BorderLayout.EAST);
		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Comment> list, Comment comment, 
			int index, boolean isSelected, boolean cellHasFocus) {
		
		this.comment = comment;
		
		nameLabel.setText(comment.getUserName());
		commentLabel.setText(comment.getComment());
		
		if((comment instanceof TextComment)) {
			answerButton.setVisible(true);
		}else {
			answerButton.setVisible(false);
		}
		
		nameLabel.setOpaque(true);
		commentLabel.setOpaque(true);
		nameLabel.setBackground(list.getBackground());
		commentLabel.setBackground(list.getBackground());
		setBackground(list.getBackground());
		
		/*We need to delimit the cells*/
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		return this;
	}
	
	public Comment getComment() {
		return this.comment;
	}
	
	public Offer getOffer() {
		return CommentRenderer.offer;
	}
	
	public void setController(ActionListener e) {
		answerButton.addActionListener(e);
	}
	

}
