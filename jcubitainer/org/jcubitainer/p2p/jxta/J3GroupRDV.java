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

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.p2p.StartJXTA;
import org.jcubitainer.tools.Process;
import org.jcubitainer.tools.ProcessMg;

public class J3GroupRDV extends Process {

	public static final String NAME = "Partie_";

	public static final String DESCRIPTION = "J3xtainer Groupe";

	private PeerGroup peerGroup = null;

	private PeerGroup rootGroup = null;

	private DiscoveryService discoSvc = null;

	private ProcessMg pipe = null;

	private PeerGroupAdvertisement adv = null;

	/**
	 *  
	 */
	public J3GroupRDV(PeerGroup proot, DiscoveryService pdiscoSvc) {
		super(3 * 60 * 1000);

		String group_name = J3xta.JXTA_ID + NAME + StartJXTA.getPeerName();

		rootGroup = proot;
		discoSvc = pdiscoSvc;

		try {
			ModuleImplAdvertisement implAdv = rootGroup
					.getAllPurposePeerGroupImplAdvertisement();

			PeerGroupAdvertisement newPGAdv = (PeerGroupAdvertisement) AdvertisementFactory
					.newAdvertisement(PeerGroupAdvertisement
							.getAdvertisementType());

			// Nouveau IDFactory :
			PeerGroupID id = IDFactory.newPeerGroupID();

			newPGAdv.setPeerGroupID(id);
			newPGAdv.setModuleSpecID(implAdv.getModuleSpecID());
			newPGAdv.setName(group_name);
			newPGAdv.setDescription(DESCRIPTION);
			peerGroup = rootGroup.newGroup(newPGAdv);

			peerGroup.getRendezVousService().startRendezVous();

			adv = peerGroup.getPeerGroupAdvertisement();

			System.out.println("! Groupe créé :" + peerGroup.getPeerGroupName());

		} catch (Exception e) {
			System.err.println("! Group creation failed");
			e.printStackTrace();
		}

	}

	public void publishGroup() {

		try {
			discoSvc.publish(adv, PeerGroup.DEFAULT_LIFETIME,
					PeerGroup.DEFAULT_EXPIRATION);
		} catch (Exception e) {
			System.out
					.println("! Failed to publish peer advertisement in the group ["
							+ peerGroup.getPeerGroupName() + "]");
		}

	}

	public void action() throws InterruptedException {
		//		System.out.println("Publication");
		publishGroup();

	}

	public DiscoveryService getDiscoveryService() {
		return peerGroup.getDiscoveryService();
	}

	public PeerGroupID getPeerGroupID() {
		return peerGroup.getPeerGroupID();
	}

	public String toString() {

		return peerGroup.getPeerGroupName();

	}
}