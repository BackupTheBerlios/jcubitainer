/*
 * Created on 25 mai 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.jcubitainer.p2p;

import java.io.File;

import org.jcubitainer.p2p.jxta.J3xta;
import org.jcubitainer.p2p.jxta.J3xtaConnect;
import org.jcubitainer.tools.Process;
import org.jcubitainer.tools.ProcessMg;

/**
 * @author mounes
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StartJXTA extends Process {

    static J3xtaConnect connect = null;

    static private String name = null;

    static private String peer_ID = null;

    private static ProcessMg manager = new ProcessMg(new StartJXTA());

    private static File config_dir = null;

    public StartJXTA() {
        super(1000);
        setPriority(Thread.MIN_PRIORITY);
    }

    public void action() throws InterruptedException {
        //Démarrage de JXTA :
        if (connect == null) {
            connect = new J3xtaConnect(config_dir);
            //Pour être à l'écoute des autres :
            connect.addGroupListener();
        }
    }

    public static void wakeUp(String n, String suffix_group,
            File configuration_dir) {
        J3xta.setSuffix(suffix_group);
        config_dir = configuration_dir;
        name = n;
        manager.wakeUp();
    }

    public static String getPeerName() {
        return name;
    }

    public static void setPeer_ID(String peer_ID) {
        StartJXTA.peer_ID = peer_ID;
    }

    public static String getPeer_ID() {
        return peer_ID;
    }
}