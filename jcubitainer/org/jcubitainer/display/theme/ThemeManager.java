/*
 * Created on 28 avr. 2004
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package org.jcubitainer.display.theme;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jcubitainer.manager.Configuration;
import org.jcubitainer.tools.Ressources;

/**
 * @author mounes
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class ThemeManager {

    private static List theme = new ArrayList();

    private static String current = null;

    /**
     *  
     */
    public ThemeManager() throws ThemeError {
        super();
        theme.add("/ressources/themes/theme_basic.zip");
        theme.add("/ressources/themes/theme_gnome.zip");
        //        theme.addAll(Ressources.getFiles("/ressources/themes/"));
        reload();
    }

    public static void swithTheme() throws ThemeError {
        reload();
    }

    public static void reload() throws ThemeError {
        InputStream is = null;
        String f = null;
        if (current == null) {
            // Recherche du th�me dans le fichier de configuration :
            String key = Configuration.getProperties("theme");
            // Si on ne trouve pas le th�me :
            f = (String) theme.get(0);
            Iterator i = theme.iterator();
            while (i.hasNext()) {
                String temp = (String) i.next();
                if (temp.equals(key)) f = temp;
            }
        } else {
            int pos = theme.indexOf(current) + 1;
            if (pos >= theme.size()) pos = 0;
            f = (String) theme.get(pos);
        }
        if (f != null) {
            is = Ressources.getInputStream(f);
            new Theme(is);
            current = f;
            Configuration.setPropertie("theme", current);
        }
    }
}