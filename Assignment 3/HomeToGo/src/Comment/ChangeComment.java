package Comment;

import java.io.Serializable;

public class ChangeComment extends Comment implements Serializable{
	private static final long serialVersionUID = -814635662535770669L;
	private String text;
	
	public ChangeComment(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
