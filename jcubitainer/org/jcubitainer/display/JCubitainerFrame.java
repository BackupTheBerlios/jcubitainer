/*
 * Created on 20 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.display;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import org.jcubitainer.key.MoveBoard;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JCubitainerFrame extends JFrame {

	MoveBoard mb = null;
	final static Color bg = Color.white;
	final static Color fg = Color.black;

	/**
	 * @param string
	 * @param db
	 */
	public JCubitainerFrame(String string, MoveBoard p) {
		super(string);
		mb = p;
		init();
	}

	public void init() {
		//Initialize drawing colors
		setBackground(bg);
		setForeground(fg);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics arg0) {
		mb.paint(arg0);
		super.paint(arg0);
	}

}
