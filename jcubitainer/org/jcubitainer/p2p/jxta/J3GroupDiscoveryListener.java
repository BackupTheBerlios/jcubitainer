/*
 * Created on 8 mars 2004
 *
 */
package org.jcubitainer.p2p.jxta;

import java.util.Enumeration;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.tools.Process;

/**
 * @author metalm
 *
 */
public class J3GroupDiscoveryListener
	extends Process
	implements DiscoveryListener {

	DiscoveryService discoveryService = null;
	private PeerGroup rootGroup = null;

	/**
	 * 
	 */
	public J3GroupDiscoveryListener(DiscoveryService ds, PeerGroup proot) {
		super(5000);
		ds.addDiscoveryListener(this);
		discoveryService = ds;
		rootGroup = proot;
	}

	public void discoveryEvent(DiscoveryEvent ev) {
		//		System.out.println("-");
		DiscoveryResponseMsg res = ev.getResponse();
		String aRes = res.getPeerAdv();
		DiscoveryResponseMsg theDiscoveryResponseMsg = ev.getResponse();

		PeerGroupAdvertisement adv = null;
		Enumeration theAdvertisementEnumeration =
			theDiscoveryResponseMsg.getAdvertisements();

		if (null != theAdvertisementEnumeration) {
			while (theAdvertisementEnumeration.hasMoreElements()) {
				try {
					adv =
						(PeerGroupAdvertisement) theAdvertisementEnumeration
							.nextElement();
					//System.out.println(" Peer Group = " + adv.getName());
					if (adv.getName().startsWith(J3xta.JXTA_ID)) {
						//						System.out.println(
						//							"Peergroup à nous : " + adv.getName());

						J3Group J3g =
							J3Group.getInstance(
								adv,
								rootGroup,
								discoveryService);

						//J3g.joinThisGroup();
					}
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
			discoveryService.getRemoteAdvertisements(
				null,
				DiscoveryService.GROUP,
				null,
				null,
				5,
				null);

			//			discoveryService.getLocalAdvertisements(
			//				DiscoveryService.GROUP,
			//				null,
			//				null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
