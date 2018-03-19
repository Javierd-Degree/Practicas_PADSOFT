package Comment;

public class ChangeComment extends Comment {
	private String text;
	
	public ChangeComment(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
