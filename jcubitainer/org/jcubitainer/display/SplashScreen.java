package org.jcubitainer.display;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import org.jcubitainer.display.theme.Theme;

/*
 * Created on 26 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SplashScreen extends JWindow {

    public SplashScreen() {
        super();
        ImageIcon icon = new ImageIcon(Theme.getCurrent().getImage(
                "isplash"));
        if (icon != null) {
            getContentPane().add(new JLabel(icon, JLabel.CENTER));
            Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
            setSize(icon.getIconWidth(), icon.getIconHeight());
            setLocation((screensize.width - icon.getIconWidth()) / 2,
                    (screensize.height - icon.getIconHeight()) / 2);
        }
    }

    public void hide(int after) {
        try {
            Thread.sleep(after);
        } catch (InterruptedException e) {
        }

        setVisible(false);
    }

}