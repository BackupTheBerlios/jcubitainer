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
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InfoImage extends JLabel {

    /**
     * @param arg0
     */
    public InfoImage(Image icon, Container container) {
        super();
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setForeground(Color.white);
        setIcon(new ImageIcon(icon));
        container.add(this);
    }
}