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

package org.jcubitainer.p2p.jxta;

import java.util.Enumeration;
import java.util.HashSet;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.tools.Process;

public class J3GroupDiscoveryListener extends Process implements
		DiscoveryListener {

	DiscoveryService discoveryService = null;

	private PeerGroup rootGroup = null;

	HashSet knowsPeers = new HashSet();

	/**
	 *  
	 */
	public J3GroupDiscoveryListener(DiscoveryService ds, PeerGroup proot) {
		super(30000);
		discoveryService = ds;
		rootGroup = proot;
	}

	public void discoveryEvent(DiscoveryEvent ev) {

		DiscoveryResponseMsg theDiscoveryResponseMsg = ev.getResponse();

		PeerGroupAdvertisement adv = null;
		Enumeration theAdvertisementEnumeration = theDiscoveryResponseMsg
				.getAdvertisements();

		if (null != theAdvertisementEnumeration) {
			while (theAdvertisementEnumeration.hasMoreElements()) {
				try {
					adv = (PeerGroupAdvertisement) theAdvertisementEnumeration
							.nextElement();

					if (knowsPeers.add(adv.getID())) {
						if (adv.getName().startsWith(J3xta.getJXTA_ID())) {
							J3Group j3g = J3Group.getInstance(adv, rootGroup,
									discoveryService);
							if (true)//j3g.isRendezvous()
								j3g.joinThisGroup();
							else
								System.out.println("! Not RDV PeerGroup");
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public void action() {
		try {

			discoveryService.addDiscoveryListener(this);

			discoveryService.getRemoteAdvertisements(null,
					DiscoveryService.GROUP, "Name", J3xta.getJXTA_ID() + "*", 50,
					null);

			discoveryService.getLocalAdvertisements(DiscoveryService.GROUP,
					"Name", J3xta.getJXTA_ID() + "*");

			//			discoveryService.getLocalAdvertisements(
			//				DiscoveryService.GROUP,
			//				null,
			//				null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}