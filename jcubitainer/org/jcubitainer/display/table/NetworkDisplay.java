/*
 * Created on 11 mars 04
 *
 */
package org.jcubitainer.display.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author mounes
 *
 */
public class NetworkDisplay extends JPanel implements ActionListener {

	String lastAction = null;

	/**
	 * 
	 */
	public NetworkDisplay() {
		super();

		JPanel panel = new JPanel(new GridLayout(1, 0));
		JPanel panel2 = new JPanel(new GridLayout(1, 0));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		NetworkDisplayTable parties =
			NetworkDisplayTable.getNetworkDisplayForParties();

		panel.add(parties, BorderLayout.WEST);

		NetworkDisplayTable joueurs =
			NetworkDisplayTable.getNetworkDisplayForJoueurs();

		panel.add(joueurs, BorderLayout.EAST);

		panel.setBackground(Color.black);
		panel.setForeground(Color.black);

		setBackground(Color.black);
		setForeground(Color.black);

		// Ajout des boutons :
		JButton button = new JButton();
		button.setBackground(Color.black);
		button.setForeground(Color.white);
		button.setBorderPainted(false);

		JButton button2 = new JButton();
		button2.setBackground(Color.black);
		button2.setForeground(Color.white);
		button2.setBorderPainted(false);

		JButton button3 = new JButton();
		button3.setBackground(Color.black);
		button3.setForeground(Color.white);
		button3.setBorderPainted(false);

		JButton button4 = new JButton();
		button4.setBackground(Color.black);
		button4.setForeground(Color.white);
		button4.setBorderPainted(false);

		button.setLabel("Joindre la partie");
		button2.setLabel("Quitter la partie");
		button3.setLabel("Se déconnecter de JX3tainer");
		button4.setLabel("Créer une partie");
		parties.setEnabled(false);
		panel2.add(button);
		panel2.add(button2);
		panel2.add(button4);
		panel2.add(button3);
		// Ajout des raccourcis :
		button.setMnemonic(KeyEvent.VK_J); 
		button2.setMnemonic(KeyEvent.VK_Q); 
		button3.setMnemonic(KeyEvent.VK_D); 
		button4.setMnemonic(KeyEvent.VK_C);
		// Ajout des actions :
		button.setActionCommand("joindre");
		button2.setActionCommand("quitter");
		button3.setActionCommand("deconnecter");
		button4.setActionCommand("creer");
		button.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);

		add(panel, BorderLayout.NORTH);
		add(panel2);
	}

	public void actionPerformed(ActionEvent e) {
		// Actions, voir les aspects !
		action(e);
	} 
	
	public void action(ActionEvent e ) {
		lastAction = e.getActionCommand();
	}
	/**
	 * @return
	 */
	public String getLastAction() {
		return lastAction;
	}

}
