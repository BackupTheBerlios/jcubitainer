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

package org.jxtainer;

import java.util.Enumeration;
import java.util.Hashtable;

import net.jxta.credential.AuthenticationCredential;
import net.jxta.credential.Credential;
import net.jxta.discovery.DiscoveryService;
import net.jxta.exception.PeerGroupException;
import net.jxta.membership.Authenticator;
import net.jxta.membership.MembershipService;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.tools.ProcessMg;

public class J3Group {

	private PeerGroup peerGroup = null;

	private PeerGroup rootGroup = null;

	private DiscoveryService rootdiscoSvc = null;

	private static Hashtable knowPeerGroups = new Hashtable();

	private static Hashtable joinPeerGroups = new Hashtable();

	private static ProcessMg peerDiscoveryServiceProcess = null;

	ProcessMg pipe_listener = null;
	
	private boolean rendezvous = false;
	
	J3Pipe pipe = null;

	/**
	 *  
	 */
	private J3Group(PeerGroup this_, PeerGroup peergroupPere,
			DiscoveryService pdiscoSvcPere) {
		super();
		rootGroup = peergroupPere;
		rootdiscoSvc = pdiscoSvcPere;
		peerGroup = this_;
		rendezvous = peerGroup.isRendezvous();
	}

	protected static J3Group getInstance(PeerGroupAdvertisement groupAdv,
			PeerGroup proot, DiscoveryService pdiscoSvc) {
		J3Group pg = (J3Group) knowPeerGroups.get(groupAdv.getPeerGroupID());
		if (pg != null)
			return pg;
		try {
			PeerGroup peerGroup_temp = proot.newGroup(groupAdv);
			pg = new J3Group(peerGroup_temp, proot, pdiscoSvc);
			pg.publishGroup();
			knowPeerGroups.put(groupAdv.getPeerGroupID(), pg);
			System.out.println("! Groupe trouv� : " + groupAdv.getName());
			StartJXTA.setPeer_ID(pg.getPeerID().toString());
		} catch (PeerGroupException e) {
			e.printStackTrace();
			System.out.println("! Impossible de cr�� le groupe : "
					+ groupAdv.getName());
		}
		return pg;
	}

	public void publishGroup() {
		// Publication du groupe.
		try {
			rootdiscoSvc.publish(peerGroup.getPeerGroupAdvertisement());
		} catch (Exception e) {
			System.out
					.println("! Failed to publish peer advertisement in the group ["
							+ peerGroup.getPeerGroupName() + "]");
		}

	}

	public synchronized void joinThisGroup() {

		if (joinPeerGroups.containsKey(peerGroup.getPeerGroupID()))
			return;

		//System.out.println("Joining peer group...");

		// On ne peut joindre un seul groupe pour l'instant !

		if (joinPeerGroups.size() > 0)
			return;

		try {
			// Generate the credentials for the Peer Group
			AuthenticationCredential authCred = new AuthenticationCredential(
					peerGroup, null, null);

			// Get the MembershipService from the peer group
			MembershipService membership = peerGroup.getMembershipService();

			// Get the Authenticator from the Authentication creds
			Authenticator auth = membership.apply(authCred);

			// Check if everything is okay to join the group
			if (auth.isReadyForJoin()) {
				Credential myCred = membership.join(auth);

				System.out.println("! Successfully joined group "
						+ peerGroup.getPeerGroupName());

				joinPeerGroups.put(peerGroup.getPeerGroupID(), peerGroup);

				// Connection Ready !
				J3xta.setStatut(J3xta.JXTA_STATUT_ON);
				
				// Rendez-vous service ?
				peerGroup.getRendezVousService().setAutoStart(true,120000);
				
				createPipeListener();

			} else {
				System.out.println("! Failure: unable to join group");
			}
		} catch (Exception e) {
			System.out.println("! Failure in authentication." + e);
		}
	}

	public DiscoveryService getPeerDiscoverService() {
		return peerGroup.getDiscoveryService();
	}

	public String toString() {
		return peerGroup.getPeerGroupName();
	}

	public PipeService getPipeService() {
		return peerGroup.getPipeService();
	}

	public PeerGroupID getPeerGroupID() {
		return peerGroup.getPeerGroupID();
	}

	public PeerID getPeerID() {
		return peerGroup.getPeerID();
	}

	public static boolean isConnectToGroup() {
		return !joinPeerGroups.isEmpty();
	}

	public void createPipeListener() {
		// On veut �couter l'arriv� des pipes
	    
	    pipe = new J3Pipe(this);	    
		pipe_listener = new ProcessMg(pipe);
		pipe_listener.wakeUp();
	}

	public boolean isJoinnedToGroup() {
		return joinPeerGroups.containsKey(getPeerGroupID());
	}

	public DiscoveryService getDiscoveryService() {
		return peerGroup.getDiscoveryService();
	}

	/**
	 * @return Returns the rendezvous.
	 */
	public boolean isRendezvous() {
		return rendezvous;
	}

    public J3Pipe getPipe() {
        return pipe;
    }
    
    public static Enumeration getJ3Groups() {
        return knowPeerGroups.elements();
    }
    
    public void quitGroup(){
        System.out.print("! On quitte le r�seau, ");
        pipe.sendMsg(J3Pipe.MESSAGE_REMOVE,true);
        System.out.println(" message envoy�.");
    }
}