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

/*******************************************************************************
 * History & changes * ******* May 5, 2004
 * ************************************************** - First release *
 ******************************************************************************/

package org.jcubitainer.p2p.jxta;

import java.util.Enumeration;

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

	/**
	 *  
	 */
	public J3GroupDiscoveryListener(DiscoveryService ds, PeerGroup proot) {
		super(10000);
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
					                    System.out.println(" Peer Group = " + adv.getName());
					if (adv.getName().startsWith(J3xta.JXTA_ID)) {
//						System.out.println("Peergroup à nous : "
//								+ adv.getName());
						J3Group J3g = J3Group.getInstance(adv, rootGroup,
								discoveryService);

						J3g.joinThisGroup();
					}
				} catch (Exception e) {
				}
			}
		}

	}

	public void action() {
		try {
			discoveryService.addDiscoveryListener(this);
			//System.out.println("Sending a Discovery Message");
			// look for any peer group
			discoveryService.getRemoteAdvertisements(null,
					DiscoveryService.GROUP, null, null, 5);

			//			discoveryService.getLocalAdvertisements(
			//				DiscoveryService.GROUP,
			//				null,
			//				null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}