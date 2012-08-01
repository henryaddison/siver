package siver.ui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import siver.boat.*;
import siver.cox.brains.BasicBrain;
import siver.cox.brains.LaneChangeBrain;
import siver.cox.brains.StartStopBrain;
import siver.river.BoatHouse;

public class UserPanel extends JPanel {
	
	private BoatHouse boathouse;
	
	public UserPanel(BoatHouse bh)  {
		boathouse = bh;
		initComponents();
	}
	
	private void initComponents() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBackground(new java.awt.Color(255, 0, 0));
		
		ArrayList<Class> coxBrainClassesNames = new ArrayList<Class>();
		coxBrainClassesNames.add(BasicBrain.class);
		coxBrainClassesNames.add(StartStopBrain.class);
		coxBrainClassesNames.add(LaneChangeBrain.class);
		
		
		for(Class klass : coxBrainClassesNames) {
			final Class coxBrainClass = klass;
			
			JButton launchButton = new JButton();
			
			launchButton.setText("Launch Boat with " + coxBrainClass.getName());
			launchButton.setActionCommand("launchBoat");
			launchButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	launchButtonActionPerformed(evt, coxBrainClass);
	            }
	        });
			
			add(launchButton);
		}
	}
	
	private void launchButtonActionPerformed(java.awt.event.ActionEvent evt, Class coxBrainClass) {
		boathouse.launchBoat(coxBrainClass);
	}
}
