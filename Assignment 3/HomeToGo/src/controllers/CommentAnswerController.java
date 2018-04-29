package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Application.Application;
import Comment.Comment;
import Comment.TextComment;
import Offer.Offer;
import User.RegisteredUser;
import views.CommentRenderer;

public class CommentAnswerController implements ActionListener{
	CommentRenderer renderer;
	
	public CommentAnswerController(CommentRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Recibo algo");
		
		Comment comment = renderer.getComment();
		Offer offer = renderer.getOffer();
		Object logged = Application.getInstance().searchLoggedIn();
		
		switch(arg0.getActionCommand()) {
		case "ANSWER_COMMENT":
			if (!(logged instanceof RegisteredUser)) {
				JOptionPane.showMessageDialog(new JFrame("Error"), 
						"An administrator cannot answer to comments.");
				break;
			}
			
			if(!(comment instanceof TextComment)) {
				JOptionPane.showMessageDialog(new JFrame("Error"), 
						"Only the text comments can be answered.");
				break;
			}
			
			String text = JOptionPane.showInputDialog(renderer,
					"Write your answer. It'll be displayed once you enter this window again.");
			if (text == null || text.equals("")) {
				break;
			}
			
			offer.postComment((RegisteredUser) logged, text, (TextComment) comment);
			
			break;
		}
		
	}
}
