package siver.ui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import siver.agents.BoatHouse;
import siver.agents.boat.*;

public class UserPanel extends JPanel {
	
	private BoatHouse boathouse;
	
	public UserPanel(BoatHouse bh)  {
		boathouse = bh;
		initComponents();
	}
	
	private void initComponents() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBackground(new java.awt.Color(255, 0, 0));
		
		ArrayList<String> coxClassNames = new ArrayList<String>();
		coxClassNames.add(Cox.class.getName());
		coxClassNames.add(StartStopCox.class.getName());
		coxClassNames.add(LaneChangeCox.class.getName());
		
		
		for(String className : coxClassNames) {
			final String coxClassName = className;
			
			JButton launchButton = new JButton();
			
			launchButton.setText("Launch Boat with " + coxClassName);
			launchButton.setActionCommand("launchBoat");
			launchButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	launchButtonActionPerformed(evt, coxClassName);
	            }
	        });
			
			add(launchButton);
		}
	}
	
	private void launchButtonActionPerformed(java.awt.event.ActionEvent evt, String coxClassName) {
		boathouse.launchBoat(coxClassName);
	}
}
