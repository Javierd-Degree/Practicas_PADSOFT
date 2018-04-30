package views;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public interface WindowInterface {
	
	public void setController(ActionListener c);
	public void setSecondaryView(JPanel view);
	public JPanel getSecondaryView();

}
