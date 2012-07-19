package siver.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import siver.agents.BoatHouse;

public class UserPanel extends JPanel {
	
	private BoatHouse boathouse;
	
	public UserPanel(BoatHouse bh)  {
		boathouse = bh;
		initComponents();
	}
	
	private void initComponents() {
		
		setBackground(new java.awt.Color(255, 0, 0));
		
		JButton launchButton = new JButton();
		
		launchButton.setText("Launch Boat");
		launchButton.setActionCommand("launchBoat");
		launchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	launchButtonActionPerformed(evt);
            }
        });
		
		add(launchButton);
	}
	
	private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		boathouse.launchBoat();
	}
}
