/*
 * Created on 8 mars 2004
 *
 */
package org.jcubitainer.p2p.jxta;

import java.util.Enumeration;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PeerAdvertisement;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.tools.Process;

/**
 * @author metalm
 *
 */
public class J3PeerDiscoveryListener
	extends Process
	implements DiscoveryListener {

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
		//		System.out.println("-");
//		DiscoveryResponseMsg res = ev.getResponse();
//		String aRes = res.getPeerAdv();
		DiscoveryResponseMsg theDiscoveryResponseMsg = ev.getResponse();

		PeerAdvertisement adv = null;
		Enumeration theAdvertisementEnumeration =
			theDiscoveryResponseMsg.getAdvertisements();

		if (null != theAdvertisementEnumeration) {
			while (theAdvertisementEnumeration.hasMoreElements()) {
				try {
					adv =
						(PeerAdvertisement) theAdvertisementEnumeration
							.nextElement();
					if (!group.existePeer(adv.getPeerID()) && adv.getPeerGroupID().equals(group.getPeerGroupID())) {

						J3Peer peer = new J3Peer(adv);
						group.addPeer(peer);
						System.out.println(
							"Découverte du Peer = "
								+ adv.getName()
								+ "/"
								+ adv.getPeerID());
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
			discoveryService.getRemoteAdvertisements(
				null,
				DiscoveryService.PEER,
				null,
				null,
				5,
				null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
