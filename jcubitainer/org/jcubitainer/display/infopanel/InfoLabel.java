/*
 * Created on 20 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.display.infopanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InfoLabel extends JLabel {

	/**
	 * @param arg0
	 */
	public InfoLabel(String arg0, Container container) {
		super(arg0);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setForeground(Color.white);
		container.add(this);
	}
}
