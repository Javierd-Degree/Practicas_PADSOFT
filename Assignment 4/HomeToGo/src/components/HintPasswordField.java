package components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;

public class HintPasswordField extends JPasswordField implements FocusListener {
	private static final long serialVersionUID = 9103381074774663712L;
	private final String hint;
	private boolean showingHint;

	public HintPasswordField(final String hint, int n) {
		super(hint, n);
		this.hint = hint;
		this.showingHint = true;
		this.setHorizontalAlignment(JPasswordField.CENTER);
		super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText("");
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
		}
	}


	@SuppressWarnings("deprecation")
	@Override
	/*We use the method getPassword, but as we override getText, we 
	 * get this warning which makes no sense here.*/
	public String getText() {
		return showingHint ? "" : String.valueOf(super.getPassword());
	}
	
	@Override
	public void setText(String text) {
		if(text.equals("")){
			super.setText(hint);
			showingHint = true;
		}else {
			showingHint = false;
			super.setText(text);
		}
	}
}