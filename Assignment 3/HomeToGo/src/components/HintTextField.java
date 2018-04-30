package components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class HintTextField extends JTextField implements FocusListener {
	private static final long serialVersionUID = 9103381074774663712L;
	private String hint;
	private boolean showingHint;

	public HintTextField(final String hint, int n) {
		super(hint, n);
		this.hint = hint;
		this.showingHint = true;
		this.setHorizontalAlignment(JTextField.CENTER);
		super.addFocusListener(this);
	}
	
	public void setHint(String hint) {
		this.hint = hint;
		this.setText(hint);
		showingHint = true;
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

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}