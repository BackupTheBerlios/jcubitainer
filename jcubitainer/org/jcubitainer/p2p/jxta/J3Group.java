/*
 * Created on 9 mars 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.p2p.jxta;
import java.util.ArrayList;
import java.util.Hashtable;

import net.jxta.credential.AuthenticationCredential;
import net.jxta.credential.Credential;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.StructuredDocument;
import net.jxta.exception.PeerGroupException;
import net.jxta.membership.Authenticator;
import net.jxta.membership.MembershipService;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.display.table.GroupTable;
import org.jcubitainer.display.table.NetworkDisplayTable;
import org.jcubitainer.tools.ProcessMg;

/**
 * @author metalm
 *
 */
public class J3Group implements Group {

	private PeerGroup peerGroup = null;
	private PeerGroup rootGroup = null;
	private DiscoveryService rootdiscoSvc = null;
	private static Hashtable knowPeerGroups = new Hashtable();
	private static Hashtable joinPeerGroups = new Hashtable();
	private Hashtable knowPeers = new Hashtable();
	private GroupTable gt = null;

	private static ProcessMg peerDiscoveryServiceProcess = null;

	/**
	 * 
	 */
	private J3Group(
		PeerGroup this_,
		PeerGroup proot,
		DiscoveryService pdiscoSvc) {
		super();
		rootGroup = proot;
		rootdiscoSvc = pdiscoSvc;
		peerGroup = this_;
	}

	protected static J3Group getInstance(PeerGroupAdvertisement groupAdv) {
		return (J3Group) knowPeerGroups.get(groupAdv.getPeerGroupID());
	}

	protected static J3Group getInstance(
		PeerGroupAdvertisement groupAdv,
		PeerGroup proot,
		DiscoveryService pdiscoSvc) {
		J3Group pg = (J3Group) knowPeerGroups.get(groupAdv.getPeerGroupID());
		if (pg != null)
			return pg;
		try {
			PeerGroup peerGroup_temp = proot.newGroup(groupAdv);
			pg = new J3Group(peerGroup_temp, proot, pdiscoSvc);
			pg.publishGroup();
			knowPeerGroups.put(groupAdv.getPeerGroupID(), pg);
			System.out.println("Groupe trouvé : " + groupAdv.getName());

			NetworkDisplayTable nd =
				NetworkDisplayTable.getNetworkDisplayForParties();

			GroupTable temp_gt = new GroupTable(pg, nd.getModel());
			pg.setGroupTableForDisplay(temp_gt);
			nd.addGroupTable(temp_gt);

			// On veut trouver ses peers :
			peerDiscoveryServiceProcess =
				new ProcessMg(new J3PeerDiscoveryListener(pg));
			peerDiscoveryServiceProcess.wakeUp();

		} catch (PeerGroupException e) {
			e.printStackTrace();
			System.out.println(
				"Impossible de créé le groupe : " + groupAdv.getName());
		}
		return pg;
	}

	public void publishGroup() {
		// Publication du groupe.
		try {
			rootdiscoSvc.publish(
				peerGroup.getPeerGroupAdvertisement(),
				DiscoveryService.GROUP);
			rootdiscoSvc.publish(
				peerGroup.getPeerAdvertisement(),
				DiscoveryService.PEER);
			rootdiscoSvc.remotePublish(
				peerGroup.getPeerGroupAdvertisement(),
				DiscoveryService.GROUP);
			rootdiscoSvc.remotePublish(
				peerGroup.getPeerAdvertisement(),
				DiscoveryService.PEER);
		} catch (Exception e) {
			System.out.println(
				"Failed to publish peer advertisement in the group ["
					+ peerGroup.getPeerGroupName()
					+ "]");
		}

	}

	public void joinThisGroup() {

		if (joinPeerGroups.containsKey(peerGroup.getPeerGroupID()))
			return;

		//System.out.println("Joining peer group...");

		StructuredDocument creds = null;

		try {
			// Generate the credentials for the Peer Group
			AuthenticationCredential authCred =
				new AuthenticationCredential(peerGroup, null, creds);

			// Get the MembershipService from the peer group
			MembershipService membership = peerGroup.getMembershipService();

			// Get the Authenticator from the Authentication creds
			Authenticator auth = membership.apply(authCred);

			// Check if everything is okay to join the group
			if (auth.isReadyForJoin()) {
				Credential myCred = membership.join(auth);

				System.out.println(
					"Successfully joined group "
						+ peerGroup.getPeerGroupName());

				/*				// display the credential as a plain text document.
								System.out.println("Credential: ");
								StructuredTextDocument doc =
									(StructuredTextDocument) myCred.getDocument(
										new MimeMediaType("text/plain"));
				
								StringWriter out = new StringWriter();
								doc.sendToWriter(out);
								System.out.println(out.toString());
								out.close();*/
				joinPeerGroups.put(peerGroup.getPeerGroupID(), peerGroup);
			} else {
				System.out.println("Failure: unable to join group");
			}
		} catch (Exception e) {
			System.out.println("Failure in authentication." + e);
		}
	}

	public DiscoveryService getPeerDiscoverService() {
		return peerGroup.getDiscoveryService();
	}

	public void addPeer(J3Peer peer) {
		knowPeers.put(peer.getPeerID(), peer);
		ArrayList al = new ArrayList(knowPeers.values());
		getGroupTableForDisplay().setFils(al);
	}

	/**
	 * @param peerID
	 * @return
	 */
	public boolean existePeer(PeerID peerID) {
		return knowPeers.containsKey(peerID);
	}

	public String toString() {

		return peerGroup.getPeerGroupName() + " (" + knowPeers.size() + ")";

	}

	public GroupTable getGroupTableForDisplay() {
		return gt;
	}

	public void setGroupTableForDisplay(GroupTable p) {
		gt = p;
	}

	public PeerGroupID getPeerGroupID(){
		return peerGroup.getPeerGroupID();
	}

}