/*
 * Created on 21 févr. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.tools;

import java.awt.Image;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

import org.jcubitainer.display.JCubitainerFrame;

/**
 * @author MetalM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Ressources {

	public static Image getImage(String nom_image) {
		ImageIcon ii = Ressources.getImageIcon(nom_image);
		return ii == null ? null : ii.getImage();
	}

	public static ImageIcon getImageIcon(String nom_image) {
		java.net.URL imgURL = JCubitainerFrame.class.getResource(nom_image);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			return null;
		}
	}

	public static InputStream getConfigInputStream(String fichier) {
		return JCubitainerFrame.class.getResourceAsStream("/config/" + fichier);
	}

	public static URL getURL(String fichier) {
		return JCubitainerFrame.class.getResource(fichier);
	}

	public static InputStream getInputStream(String fichier) {
		return JCubitainerFrame.class.getResourceAsStream(fichier);
	}

}
