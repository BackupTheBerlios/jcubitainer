/*
 * Created on 14 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager;

import java.awt.Image;

import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.meta.MetaPiece;

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
        if (pmatiere > MetaPiece.MATIERE_MAX) pmatiere = MetaPiece.MATIERE_MAX;
        // En fonction du thème en cours :
        pmatiere = pmatiere % ThemeManager.getCurrent().getMAX_MATIERE();
        if (fix)
            return ThemeManager.getCurrent().getImage("if" + (pmatiere));
        else
            return ThemeManager.getCurrent().getImage("i" + (pmatiere));
    }

    public static Image getActiveColor(int pmatiere) {
        if (pmatiere > MetaPiece.MATIERE_MAX) pmatiere = MetaPiece.MATIERE_MAX;
        // En fonction du thème en cours :
        pmatiere = pmatiere % ThemeManager.getCurrent().getMAX_MATIERE();
        return ThemeManager.getCurrent().getImage("ia" + (pmatiere));
    }

    public static Image getOmbre() {
        return ThemeManager.getCurrent().getImage("iombre");
    }

}