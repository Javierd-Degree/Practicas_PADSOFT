package Comment;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import User.RegisteredUser;

public class TextComment extends Comment implements Serializable{
	private static final long serialVersionUID = -1027844935905198927L;
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
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof TextComment)) {
			return false;
		}
		
		TextComment c = (TextComment) o;
		return this.text.equals(c.text) && this.user.equals(c.user)
				&& this.answers.equals(c.answers);
	}
}