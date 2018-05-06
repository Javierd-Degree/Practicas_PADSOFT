package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Application;
import Comment.TextComment;
import User.RegisteredUser;
import views.CommentCellView;

public class CommentCellController implements ActionListener{
	private CommentCellView view;
	
	public CommentCellController(CommentCellView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object logged = Application.getInstance().searchLoggedIn();
		switch(e.getActionCommand()) {
		case "ANSWER_COMMENT":
			if (!(logged instanceof RegisteredUser)) {
				JOptionPane.showMessageDialog(new JFrame("Error"), 
						"An administrator cannot answer offers.");
				return;
			}
			
			if(!(view.getComment() instanceof TextComment)) {
				JOptionPane.showMessageDialog(new JFrame("Error"),
						"Upps, something bad happened, but anyway, nobody is "
						+ "perfect. Try again later.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			TextComment c = (TextComment) view.getComment();

			String comment = JOptionPane.showInputDialog(view,
					"Write your answer.");
			if (comment == null || comment.equals("")) {
				return;
			}

			view.getOffer().postComment((RegisteredUser) logged, comment, (TextComment)view.getComment());
			view.addAnswer(c.getAnswers().get(c.getAnswers().size()-1));
			break;
		
		}
		
	}

}
