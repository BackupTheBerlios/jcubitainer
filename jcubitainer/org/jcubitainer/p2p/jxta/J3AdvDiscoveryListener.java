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
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.tools.Process;

public class J3AdvDiscoveryListener extends Process implements
        DiscoveryListener {

    DiscoveryService discoveryService = null;

    /**
     * 
     */
    public J3AdvDiscoveryListener(DiscoveryService ds) {
        super(3000);
        ds.addDiscoveryListener(this);
        discoveryService = ds;
    }

    public void discoveryEvent(DiscoveryEvent ev) {
        //		System.out.println("-");
        DiscoveryResponseMsg res = ev.getResponse();
        String aRes = res.getPeerAdv();
        DiscoveryResponseMsg theDiscoveryResponseMsg = ev.getResponse();

        PeerGroupAdvertisement adv = null;
        Enumeration theAdvertisementEnumeration = theDiscoveryResponseMsg
                .getAdvertisements();

        if (null != theAdvertisementEnumeration) {
            while (theAdvertisementEnumeration.hasMoreElements()) {
                try {
                    adv = adv = (PeerGroupAdvertisement) theAdvertisementEnumeration
                            .nextElement();

                    //System.out.println(" Adv = " + adv.getName());
                } catch (Exception e) {
                    //System.out.print("#");
                }
            }
        }

    }

    public void action() {
        try {
            //System.out.println("Sending a Discovery Message");
            // look for any peer group
            discoveryService.getRemoteAdvertisements(null,
                    DiscoveryService.ADV, null, null, 5, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}