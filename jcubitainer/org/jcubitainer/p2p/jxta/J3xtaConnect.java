/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : May 5, 2004                                  *
 * Author : Mounès Ronan metalm@users.berlios.de                       *
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

/* History & changes **************************************************
 *                                                                     *
 ******** May 5, 2004 **************************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.p2p.jxta;

import net.jxta.discovery.DiscoveryService;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;

import org.jcubitainer.tools.ProcessMg;

public class J3xtaConnect {

    private PeerGroup root = null;

    private DiscoveryService rootDiscoveryService = null;

    private ProcessMg groupDiscoveryServiceProcess = null;

    //	private ProcessMg peerDiscoveryServiceProcess = null;
    private ProcessMg advDiscoveryServiceProcess = null;

    private J3Group j3root = null;

    private ProcessMg group = null;

    //private J3GroupDiscoveryListener groupDiscoveryListener = null;

    public J3xtaConnect() {
        try {
            J3xta.setStatut(J3xta.JXTA_STATUT_CONNECT);
            System.out.println("Connexion à JXTA !");
            root = PeerGroupFactory.newNetPeerGroup();
            rootDiscoveryService = root.getDiscoveryService();
            System.out.println("Connecté à JXTA !");
            //			j3root = J3Group.getInstance(root.getPeerGroupAdvertisement(),root,rootDiscoveryService);
            //			j3root.joinThisGroup();
            J3xta.setStatut(J3xta.JXTA_STATUT_ON);
        } catch (PeerGroupException e) {
            System.out.println("fatal error : group creation failure");
            e.printStackTrace();
            J3xta.setStatut(J3xta.JXTA_STATUT_ERROR);
        }
    }

    public void addGroupListener() {

        // On lance le service qui va devoir trouver les groupes :
        groupDiscoveryServiceProcess = new ProcessMg(
                new J3GroupDiscoveryListener(rootDiscoveryService, root));
        groupDiscoveryServiceProcess.wakeUp();

        //		peerDiscoveryServiceProcess =
        //			new ProcessMg(new J3PeerDiscoveryListener(rootDiscoveryService));
        //		peerDiscoveryServiceProcess.wakeUp();

        // On lance le service qui va devoir trouver les advs :
        advDiscoveryServiceProcess = new ProcessMg(new J3AdvDiscoveryListener(
                rootDiscoveryService));
        advDiscoveryServiceProcess.wakeUp();

        J3GroupRDV rdv = new J3GroupRDV(root, rootDiscoveryService);
        group = new ProcessMg(rdv);
        group.wakeUp();
        //		try {
        //			Thread.sleep(2000);
        //		} catch (InterruptedException e) {
        //			e.printStackTrace();
        //		}
        //System.out.println("On veut joindre le groupe créé.");

        // On veut se connecter sans le forcer !!
        //        ((J3GroupRDV) group.getProcess()).joinThisGroup();

        // Ouverture du pipe :
        rdv.createPipe();
    }
}