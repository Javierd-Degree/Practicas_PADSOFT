package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.AdminPanelView;

public class AdminPanelController implements ActionListener{
	
	AdminPanelView view;
	
	public AdminPanelController(AdminPanelView view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
		case "APPROVE":
			System.out.println("Aprovamos la oferta");
			
			break;
		case "ASK_CHANGES":
			System.out.println("Queremos cambios. REVOLUSIO");
			
			break;
			
		case "DENY":
			System.out.println("Denegamos la oferta");
			
			break;
			
		}
		
	}

}
