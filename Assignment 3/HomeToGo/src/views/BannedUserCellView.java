package views;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import User.RegisteredUser;
import components.HintTextField;

public class BannedUserCellView extends JPanel{

	private static final long serialVersionUID = 6923524578307690486L;
	
	private RegisteredUser user;
	private JLabel userId;
	private HintTextField creditCardTextField;
	private JButton acceptButton;
	
	private JPanel parent;

	public BannedUserCellView(RegisteredUser user, JPanel parent) {
		super();
		this.user = user;
		this.parent = parent;
		setLayout(new FlowLayout(FlowLayout.LEFT, 6, 3));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		
		userId = new JLabel(user.getId());
		creditCardTextField = new HintTextField("New credit card", 20);
		acceptButton = new JButton("Unblock user");
		acceptButton.setActionCommand("UNBLOCK");
		add(userId);
		add(creditCardTextField);
		add(acceptButton);
	}
	
	public RegisteredUser getUser() {
		return this.user;
	}
	
	public String getCreditCard() {
		return this.creditCardTextField.getText();
	}
	
	public void setController(ActionListener e) {
		this.acceptButton.addActionListener(e);
	}
	
	public JPanel getParent() {
		return this.parent;
	}
	
}
