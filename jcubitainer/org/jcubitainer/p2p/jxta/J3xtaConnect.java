/*
 * Created on 8 mars 2004
 *
 */
package org.jcubitainer.p2p.jxta;

import net.jxta.discovery.DiscoveryService;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;

import org.jcubitainer.tools.ProcessMg;

/**
 * @author metalm
 *
 */
public class J3xtaConnect {

	public static final int JXTA_STATUT_OFF = 0;
	public static final int JXTA_STATUT_ON = 1;
	public static final int JXTA_STATUT_ERROR = 2;
	private int statut = JXTA_STATUT_OFF;
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
			root = PeerGroupFactory.newNetPeerGroup();
			rootDiscoveryService = root.getDiscoveryService();
			System.out.println("Connecté à JXTA !");
//			j3root = J3Group.getInstance(root.getPeerGroupAdvertisement(),root,rootDiscoveryService);
//			j3root.joinThisGroup();
			setStatut(JXTA_STATUT_ON);			
		} catch (PeerGroupException e) {
			System.out.println("fatal error : group creation failure");
			e.printStackTrace();
			setStatut(JXTA_STATUT_ERROR);
		}
	}

	/**
	 * @return
	 */
	public int getStatut() {
		return statut;
	}

	/**
	 * @param i
	 */
	public void setStatut(int i) {
		statut = i;
	}

	public void addGroupListener() {

		groupDiscoveryServiceProcess =
			new ProcessMg(
				new J3GroupDiscoveryListener(rootDiscoveryService, root));
		groupDiscoveryServiceProcess.wakeUp();

//		peerDiscoveryServiceProcess =
//			new ProcessMg(new J3PeerDiscoveryListener(rootDiscoveryService));
//		peerDiscoveryServiceProcess.wakeUp();

		advDiscoveryServiceProcess =
			new ProcessMg(new J3AdvDiscoveryListener(rootDiscoveryService));
		advDiscoveryServiceProcess.wakeUp();

//		group = new ProcessMg(new J3GroupRDV(root, rootDiscoveryService));
//		group.wakeUp();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		//System.out.println("On veut joindre le groupe créé.");
		//((J3GroupRDV) group.getProcess()).joinThisGroup();
	}
}
