/*
 * Created on 28 avr. 2004
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package org.jcubitainer.display.theme;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
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

    private static Theme currentTheme = null;

    private static Hashtable cache = new Hashtable();

    /**
     *  
     */
    public ThemeManager() throws ThemeError {
        super();
        theme.add("/ressources/themes/theme_basic.zip");
        theme.add("/ressources/themes/theme_gnome.zip");
        theme.add("/ressources/themes/theme_xp.zip");
        //        theme.addAll(Ressources.getFiles("/ressources/themes/"));
        reload();
    }

    public synchronized static void swithTheme() throws ThemeError {
        reload();
    }

    public static void reload() throws ThemeError {
        InputStream is = null;
        String f = null;
        if (current == null) {
            // Recherche du thème dans le fichier de configuration :
            String key = Configuration.getProperties("theme");
            // Si on ne trouve pas le thème :
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
            // Chargement :
            Theme temp = (Theme) cache.get(f);
            if (temp == null) {
                temp = new Theme(is);
                cache.put(f, temp);
            }
            current = f;
            currentTheme = temp;
            Configuration.setPropertie("theme", current);
        }
    }

    /**
     * @return Returns the current.
     */
    public static Theme getCurrent() {
        return currentTheme;
    }

}