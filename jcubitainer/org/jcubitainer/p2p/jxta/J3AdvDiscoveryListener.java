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
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.tools.Process;

/**
 * @author metalm
 *
 */
public class J3AdvDiscoveryListener
	extends Process
	implements DiscoveryListener {

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
		Enumeration theAdvertisementEnumeration =
			theDiscoveryResponseMsg.getAdvertisements();

		if (null != theAdvertisementEnumeration) {
			while (theAdvertisementEnumeration.hasMoreElements()) {
				try {
					adv =
						adv =
							(PeerGroupAdvertisement) theAdvertisementEnumeration
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
			discoveryService.getRemoteAdvertisements(
				null,
				DiscoveryService.ADV,
				null,
				null,
				5,
				null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
