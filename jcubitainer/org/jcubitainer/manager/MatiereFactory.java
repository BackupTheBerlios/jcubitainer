/*
 * Created on 14 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager;

import java.awt.Image;

import org.jcubitainer.tools.Ressources;

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatiereFactory {

	private static Image[] images =
		{
			Ressources.getImage("/logo/10.gif"),
			Ressources.getImage("/logo/11.gif"),
			Ressources.getImage("/logo/12.gif"),
			Ressources.getImage("/logo/13.gif"),
			Ressources.getImage("/logo/14.gif"),
			Ressources.getImage("/logo/15.gif"),
			Ressources.getImage("/logo/16.gif")};

	private static Image[] image_actives =
		{
			Ressources.getImage("/logo/10_actif.gif"),
			Ressources.getImage("/logo/11_actif.gif"),
			Ressources.getImage("/logo/12_actif.gif"),
			Ressources.getImage("/logo/13_actif.gif"),
			Ressources.getImage("/logo/14_actif.gif"),
			Ressources.getImage("/logo/15_actif.gif"),
			Ressources.getImage("/logo/16_actif.gif")};

	private static Image[] image_fixes =
		{
			Ressources.getImage("/logo/10_fix.gif"),
			Ressources.getImage("/logo/11_fix.gif"),
			Ressources.getImage("/logo/12_fix.gif"),
			Ressources.getImage("/logo/13_fix.gif"),
			Ressources.getImage("/logo/14_fix.gif"),
			Ressources.getImage("/logo/15_fix.gif"),
			Ressources.getImage("/logo/16_fix.gif")};

	private static Image ombre = Ressources.getImage("/logo/ombre.png");

	/**
	 *  
	 */

	public static Image getColorByMatiere(int pmatiere, boolean fix) {
		if (fix)
			return image_fixes[pmatiere];
		else
			return images[pmatiere];
	}

	public static Image getActiveColor(int pmatiere) {
		return image_actives[pmatiere];
	}

	public static Image getOmbre() {
		return ombre;
	}

}
