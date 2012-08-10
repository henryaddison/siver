package siver.ui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import siver.context.SiverContextCreator;
import siver.cox.brains.BasicBrain;
import siver.cox.brains.CoxBrain;
import siver.cox.brains.LaneChangeBrain;
import siver.cox.brains.StartStopBrain;

public class UserPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public UserPanel()  {
		initComponents();
	}
	
	private void initComponents() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBackground(new java.awt.Color(255, 0, 0));
		
		ArrayList<Class<? extends CoxBrain>> coxBrainClassesNames = new ArrayList<Class<? extends CoxBrain>>();
		coxBrainClassesNames.add(BasicBrain.class);
		coxBrainClassesNames.add(StartStopBrain.class);
		coxBrainClassesNames.add(LaneChangeBrain.class);
		
		
		for(Class<? extends CoxBrain> klass : coxBrainClassesNames) {
			final Class<? extends CoxBrain> coxBrainClass = klass;
			
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
	
	private void launchButtonActionPerformed(java.awt.event.ActionEvent evt, Class<? extends CoxBrain> coxBrainClass) {
		SiverContextCreator.getBoatHouse().manualLaunch(coxBrainClass);
	}
}
