/*
 * Created on 25 mai 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.jcubitainer.p2p;

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

    static public String name = null;

    //    private JFrame frame2 = null;

    private static ProcessMg manager = new ProcessMg(new StartJXTA());

    public StartJXTA() {
        super(1000);
        setPriority(Thread.MIN_PRIORITY);
    }

    public void action() throws InterruptedException {
        //Démarrage de JXTA :
        if (connect == null) {
            connect = new J3xtaConnect();
            //Pour être à l'écoute des autres :
            connect.addGroupListener();
        }
        //        if (frame2 == null) {
        //            frame2 = new JFrame("J3XTainer Network 0.3");
        //            frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //            NetworkDisplay nd = new NetworkDisplay();
        //            frame2.getContentPane().add(nd);
        //            frame2.setResizable(false);
        //            frame2.pack();
        //            frame2.setVisible(true);
        //        }
    }

    public static void wakeUp(String n) {
        name = n;
        manager.wakeUp();
    }

}