package Comment;
import java.util.List;
import java.util.ArrayList;
import User.RegisteredUser;

public class TextComment extends Comment {
	private String text;
	private RegisteredUser user;
	private List<TextComment> answers;
	
	public TextComment(String text, RegisteredUser user) {
		super();
		this.text = text;
		this.user = user;
		this.answers = new ArrayList<>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}
	
	public void answerComment(String text, RegisteredUser user) {
		TextComment answer = new TextComment(text, user);
		this.answers.add(answer);
	}
}