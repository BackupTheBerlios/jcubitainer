/*
 * Created on 14 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager;

import java.awt.Image;

import org.jcubitainer.display.theme.ThemeManager;

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatiereFactory {

    /**
     *  
     */

    public static Image getColorByMatiere(int pmatiere, boolean fix) {
        if (pmatiere > ThemeManager.getCurrent().getMAX_MATIERE())
                pmatiere = ThemeManager.getCurrent().getMAX_MATIERE();
        if (fix)
            return ThemeManager.getCurrent().getImage("if" + (pmatiere - 1));
        else
            return ThemeManager.getCurrent().getImage("i" + (pmatiere - 1));
    }

    public static Image getActiveColor(int pmatiere) {
        if (pmatiere > ThemeManager.getCurrent().getMAX_MATIERE())
                pmatiere = ThemeManager.getCurrent().getMAX_MATIERE();
        return ThemeManager.getCurrent().getImage("ia" + (pmatiere - 1));
    }

    public static Image getOmbre() {
        return ThemeManager.getCurrent().getImage("iombre");
    }

}