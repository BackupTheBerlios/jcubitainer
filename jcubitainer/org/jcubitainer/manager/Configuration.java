/*
 * Created on 29 avr. 2004
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package org.jcubitainer.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

/**
 * @author mounes
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class Configuration extends Properties {

    static File conf = null;

    static Configuration c = null;

    private static final String DIR = ".jcubitainer";

    /**
     *  
     */
    public Configuration() {
        super();
        conf = new File(System.getProperty("user.home") + File.separator + DIR
                + File.separator + "conf.txt");
        loadConf();
        c = this;
    }

    private void loadConf() {
        if (conf.canRead()) {
            try {

                FileInputStream fileinputstream = new FileInputStream(conf);
                load(fileinputstream);
                fileinputstream.close();
                System.out.println(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le fichier de configuration n'existe pas !");
        }
    }

    public static void setPropertie(String key, String value) {
        c.setProperty(key, value);
    }

    public static String getProperties(String key) {
        return c.getProperty(key);
    }

    public static void save() {
        try {
            new File(System.getProperty("user.home") + File.separator + DIR)
                    .mkdirs();

            System.out.println("Sauvegarde du fichier de configuration !");
            FileOutputStream out = new FileOutputStream(conf);
            c.store(out, new Date().toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}