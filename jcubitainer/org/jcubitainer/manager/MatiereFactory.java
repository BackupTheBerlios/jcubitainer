/*
 * Created on 14 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager;

import java.awt.Image;

import org.jcubitainer.display.theme.Theme;

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
        if (pmatiere > Theme.getCurrent().getMAX_MATIERE())
                pmatiere = Theme.getCurrent().getMAX_MATIERE();
        if (fix)
            return Theme.getCurrent().getImage("if" + (pmatiere - 1));
        else
            return Theme.getCurrent().getImage("i" + (pmatiere - 1));
    }

    public static Image getActiveColor(int pmatiere) {
        if (pmatiere > Theme.getCurrent().getMAX_MATIERE())
                pmatiere = Theme.getCurrent().getMAX_MATIERE();
        return Theme.getCurrent().getImage("ia" + (pmatiere - 1));
    }

    public static Image getOmbre() {
        return Theme.getCurrent().getImage("iombre");
    }

}