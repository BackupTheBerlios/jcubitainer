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

import java.util.Enumeration;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PeerAdvertisement;

import org.jcubitainer.tools.Process;

public class J3PeerDiscoveryListener extends Process implements
        DiscoveryListener {

    DiscoveryService discoveryService = null;

    J3Group group = null;

    /**
     * 
     */
    public J3PeerDiscoveryListener(J3Group gr) {
        super(10000);
        group = gr;
        discoveryService = group.getPeerDiscoverService();
        discoveryService.addDiscoveryListener(this);
    }

    public void discoveryEvent(DiscoveryEvent ev) {
        //        System.out.println("discoveryEvent : J3PeerDiscoveryListener");
        //		DiscoveryResponseMsg res = ev.getResponse();
        //		String aRes = res.getPeerAdv();
        DiscoveryResponseMsg theDiscoveryResponseMsg = ev.getResponse();

        PeerAdvertisement adv = null;
        Enumeration theAdvertisementEnumeration = theDiscoveryResponseMsg
                .getAdvertisements();

        if (null != theAdvertisementEnumeration) {
            while (theAdvertisementEnumeration.hasMoreElements()) {
                try {
                    adv = (PeerAdvertisement) theAdvertisementEnumeration
                            .nextElement();
                    if (!group.existePeer(adv.getPeerID())
                            && adv.getPeerGroupID().equals(
                                    group.getPeerGroupID())) {

                        J3Peer peer = new J3Peer(adv, group);
                        group.addPeer(peer);
                        System.out.println("Découverte du Peer = "
                                + adv.getName() + "/" + adv.getPeerID());
                        // On va maintenant se connecter au peer avec Pipe :
                        peer.createPipeListener();

                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    //					System.out.println("#" );
                }
            }
        }

    }

    public void action() {
        try {
            //System.out.println("Sending a Discovery Message");
            // look for any peer group
            discoveryService.getRemoteAdvertisements(null,
                    DiscoveryService.PEER, null, null, 5, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}