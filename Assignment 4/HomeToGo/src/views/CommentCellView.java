package views;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Comment.Comment;
import Comment.TextComment;
import Offer.Offer;
import controllers.CommentCellController;

public class CommentCellView extends JPanel{
	
	private static final long serialVersionUID = -2401989407941112894L;
	
	private Offer offer;
	private Comment comment;
	
	private JLabel nameLabel;
	private JLabel commentLabel;
	private JButton answerButton;
	
	private JPanel answers;
	private JPanel parent;

	public CommentCellView(Comment comment, Offer offer, JPanel parent) {
		super();
		setLayout(new BorderLayout(4, 4));
		
		this.offer = offer;
		this.comment = comment;
		this.parent = parent;
		//this.answers = new JPanel(new GridLayout(0, 1, 6, 0));
		
		answers = new JPanel();
		answers.setLayout(new BoxLayout(answers, BoxLayout.Y_AXIS));
		
		nameLabel = new JLabel();
		commentLabel = new JLabel();
		answerButton = new JButton("Aswer");
		answerButton.setActionCommand("ANSWER_COMMENT");
		
		add(nameLabel, BorderLayout.NORTH);
		add(commentLabel, BorderLayout.CENTER);
		add(answers, BorderLayout.SOUTH);
		if(comment instanceof TextComment) {
			/*Es un TextComment, anadimos*/
			for(TextComment c: ((TextComment)comment).getAnswers()) {
				addAnswer(c);
			}
			add(answerButton, BorderLayout.EAST);
		}
		
		nameLabel.setText(comment.getUserName());
		commentLabel.setText(comment.getComment());
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	}
	
	public void setController(ActionListener e) {
		answerButton.addActionListener(e);
	}
	
	public Offer getOffer() {
		return this.offer;
	}
	
	public Comment getComment() {
		return this.comment;
	}
	
	public void addAnswer(TextComment c) {
		CommentCellView v = new CommentCellView(c, this.offer, this);
		CommentCellController cont = new CommentCellController(v);
		v.setController(cont);
		this.answers.add(v);
		this.revalidate();
	}
	
	@Override
	public void validate() {
		this.answers.validate();
		this.answers.repaint();
		this.parent.revalidate();
	}
}
