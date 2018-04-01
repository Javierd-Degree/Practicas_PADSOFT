package JUnitTesting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import Comment.*;
import User.RegisteredUser;;

public class CommentTest {
	private static ChangeComment cComment;
	private static Rating rating;
	private static TextComment tComment;
	private static RegisteredUser user;
	
	@Before
	public void before() {
		user = new RegisteredUser("112", "Pedro", "Lopez", "1234567890123456", "Hello world");
		cComment = new ChangeComment("Explanation of the needed changes");
		rating = new Rating(4, user);
		tComment = new TextComment("Comentario de prueba", user);
	}
	
	@Test
	public void testEquals() {
		/*Test ChangeComment equals*/
		assertEquals(cComment.equals(cComment), true);
		assertEquals(cComment.equals(null), false);
		assertEquals(cComment.equals(new ChangeComment("Explanation of the needed changes")), true);
	
		/*Test Rating equals*/
		assertEquals(rating.equals(rating), true);
		assertEquals(rating.equals(null), false);
		assertEquals(rating.equals(new Rating(4, user)), true);
		
		/*Test TextComment equals*/
		assertEquals(tComment.equals(tComment), true);
		assertEquals(tComment.equals(null), false);
		assertEquals(tComment.equals(new TextComment("Comentario de prueba", user)), true);
	}

}
