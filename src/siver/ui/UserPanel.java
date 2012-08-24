package siver.ui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import siver.context.SiverContextCreator;
import siver.cox.control_policies.GearFocussed;
import siver.cox.control_policies.ControlPolicy;
import siver.cox.control_policies.DemoLaneChanging;
import siver.cox.control_policies.DemoChangingSpeed;

public class UserPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public UserPanel()  {
		initComponents();
	}
	
	private void initComponents() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBackground(new java.awt.Color(255, 0, 0));
		
		ArrayList<Class<? extends ControlPolicy>> controlPolicyClassesNames = new ArrayList<Class<? extends ControlPolicy>>();
		controlPolicyClassesNames.add(GearFocussed.class);
		controlPolicyClassesNames.add(DemoChangingSpeed.class);
		controlPolicyClassesNames.add(DemoLaneChanging.class);
		
		
		for(Class<? extends ControlPolicy> klass : controlPolicyClassesNames) {
			final Class<? extends ControlPolicy> controlPolicyClass = klass;
			
			JButton launchButton = new JButton();
			
			launchButton.setText("Launch Boat with " + controlPolicyClass.getSimpleName());
			launchButton.setActionCommand("launchBoat");
			launchButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	launchButtonActionPerformed(evt, controlPolicyClass);
	            }
	        });
			
			add(launchButton);
		}
	}
	
	private void launchButtonActionPerformed(java.awt.event.ActionEvent evt, Class<? extends ControlPolicy> controlPolicyClass) {
		SiverContextCreator.getBoatHouse().manualLaunch(controlPolicyClass);
	}
}
