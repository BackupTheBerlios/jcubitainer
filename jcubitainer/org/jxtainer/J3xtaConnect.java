/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : May 5, 2004                                  *
 * Author : Moun�s Ronan metalm@users.berlios.de                       *
 *                                                                     *
 *     http://jcubitainer.berlios.de/                                  *
 *                                                                     *
 * This code is released under the GNU GPL license, version 2 or       *
 * later, for educational and non-commercial purposes only.            *
 * If any part of the code is to be included in a commercial           *
 * software, please contact us first for a clearance at                *
 * metalm@users.berlios.de                                             *
 *                                                                     *
 *   This notice must remain intact in all copies of this code.        *
 *   This code is distributed WITHOUT ANY WARRANTY OF ANY KIND.        *
 *   The GNU GPL license can be found at :                             *
 *           http://www.gnu.org/copyleft/gpl.html                      *
 *                                                                     *
 ***********************************************************************/

/*******************************************************************************
 * History & changes * ******* May 5, 2004
 * ************************************************** - First release *
 ******************************************************************************/

package org.jxtainer;

import java.io.File;

import net.jxta.discovery.DiscoveryService;
import net.jxta.exception.ConfiguratorException;
import net.jxta.ext.config.Configurator;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;
import net.jxta.rendezvous.RendezVousService;

import org.jcubitainer.tools.ProcessMg;

public class J3xtaConnect {

    private PeerGroup root = null;

    private DiscoveryService rootDiscoveryService = null;

    private ProcessMg groupDiscoveryServiceProcess = null;

    private J3Group j3root = null;

    private ProcessMg group = null;

    private RendezVousService rdv_root;

    public J3xtaConnect(File configuration) {
        try {
            J3xta.setStatut(J3xta.JXTA_STATUT_CONNECT);
            System.out.println("! Connexion � JXTA !");

            // Configuration automatique :
            if (configuration != null)
                System
                        .setProperty("JXTA_HOME", configuration
                                .getAbsolutePath());

            File config_jxta = new File(Configurator.getHome(),
                    "PlatformConfig").getAbsoluteFile();

            if (!config_jxta.exists()) {
                try {
                    System.out
                            .println("! Cr�ation du fichier de configuration JXTA.");
                    String name = StartJXTA.getPeerName();
                    Configurator config = new Configurator(name,
                            "JXTAConfiguration", name, "monmotdepasse2005");
                    config.save();
                } catch (ConfiguratorException ce) {
                    System.out
                            .println("! Cr�ation du fichier de configuration JXTA impossible.");
                    // Impossible de faire une configuration automatique !
                }
            }

            root = PeerGroupFactory.newNetPeerGroup();
            rootDiscoveryService = root.getDiscoveryService();

            //          Extract the discovery and rendezvous services from our peer group
            rdv_root = root.getRendezVousService();

            // Wait until we connect to a rendezvous peer
            System.out.print("! On recherche un rendezvous");

            int boucle = 30 /*1 minute*/* 4;

            while (!rdv_root.isConnectedToRendezVous() && boucle-- > 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }
            }

            if (rdv_root.isConnectedToRendezVous())
                System.out.println("! Peer Rendez-vous trouv�.");
            else
                System.out.println("! Peer Rendez-vous non trouv�.");

            System.out.println("! Connect� � JXTA.");

        } catch (Exception e) {
            System.out.println("! fatal error : group creation failure");
            e.printStackTrace();
            J3xta.setStatut(J3xta.JXTA_STATUT_ERROR);
        }
    }

    public void addGroupListener() {

        // On lance le service qui va devoir trouver les groupes :
        groupDiscoveryServiceProcess = new ProcessMg(
                new J3GroupDiscoveryListener(rootDiscoveryService, root));
        groupDiscoveryServiceProcess.wakeUp();

        // On lance le service qui va devoir trouver les advs :
        //		advDiscoveryServiceProcess = new ProcessMg(new
        // J3AdvDiscoveryListener(
        //				rootDiscoveryService));
        //		advDiscoveryServiceProcess.wakeUp();

        // On va attendre 1 minute pour essayer de trouver un groupe JXtainer

        System.out.print("! On va essay� de trouver une partie.");
        try {
            int boucle = 60 * 5; // 5 minutes
            while (!J3Group.isConnectToGroup() && --boucle > 0) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!J3Group.isConnectToGroup()) {
            System.out.println("! Pas de groupe trouv� :-(");
            J3GroupRDV rdv = new J3GroupRDV(root, rootDiscoveryService);
            group = new ProcessMg(rdv);
            group.wakeUp();
        } else
            System.out.println("! Une partie trouv�e sur Internet.");
    }
}