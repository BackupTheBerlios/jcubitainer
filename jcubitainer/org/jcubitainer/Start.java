package org.jcubitainer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jcubitainer.display.DisplayPiece;
import org.jcubitainer.display.JCubitainerFrame;
import org.jcubitainer.display.SplashScreen;
import org.jcubitainer.display.infopanel.DisplayInfo;
import org.jcubitainer.display.theme.ThemeError;
import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.Configuration;
import org.jcubitainer.manager.Game;
import org.jcubitainer.manager.PieceFactory;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.p2p.jxta.J3xtaConnect;

/*
 * Created on 14 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Start {

    static SplashScreen ss = null;

    static J3xtaConnect connect = null;

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        int largeur = 15;
        int hauteur = 40;
        // Affichage de la boite :
        MetaBoard mb = new MetaBoard(largeur, hauteur);
        MetaInfo mi = new MetaInfo();
        MoveBoard db = new MoveBoard(mb, mi);
        db.setPreferredSize(new Dimension(largeur * DisplayPiece.LARGEUR_PIECE
                + 9, hauteur * DisplayPiece.HAUTEUR_PIECE));
        // Affichage des infos :
        DisplayInfo di = new DisplayInfo(mi);
        di.setPreferredSize(new Dimension(200, 50));
        new PieceFactory(mb);

        JCubitainerFrame frame = new JCubitainerFrame("JCubitainer 0.2", db);
        frame.setIconImage(ThemeManager.getCurrent().getImage("ilogo"));
        //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Configuration.save();
                System.exit(0);
            }
        });

        frame.setResizable(false);
        //frame.setUndecorated(true);
        frame.getContentPane().add(db, BorderLayout.WEST);
        frame.getContentPane().add(di, BorderLayout.EAST);
        frame.addKeyListener(db);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxX = (screenSize.width - frame.getWidth()) / 2;
        int maxY = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(maxX, maxY);
        // Seulement pour le JDK 1.4 :
        //frame.setLocationRelativeTo(null);
        /*
         * GraphicsDevice device = GraphicsEnvironment
         * .getLocalGraphicsEnvironment() .getDefaultScreenDevice();
         */
        new Game(db, mi);
        ss.hide(2000);
        frame.setVisible(true);

        //        JFrame frame2 = new JFrame("J3XTainer Network 0.3");
        //        frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //        NetworkDisplay nd = new NetworkDisplay();
        //        frame2.getContentPane().add(nd);
        //        frame2.setResizable(false);
        //        frame2.pack();
        //        frame2.setVisible(true);

        //NetworkDisplayTable parties = NetworkDisplayTable
        //        .getNetworkDisplayForParties();
        // Démarrage de JXTA :
        // connect = new J3xtaConnect();
        // Pour être à l'écoute des autres :
        // connect.addGroupListener();
    }

    public static void main(String[] args) {

        // Chargement du fichier de configuration s'il existe :
        new Configuration();

        // Chargement du thème par défaut :
        try {
            new ThemeManager();
        } catch (ThemeError e1) {
            e1.printStackTrace();
        }
        ss = new SplashScreen();
        ss.setVisible(true);
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
        //	public void run() {
        createAndShowGUI();
        //	}
        //});
    }
}