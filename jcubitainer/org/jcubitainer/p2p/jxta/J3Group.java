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
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jcubitainer.tools.ProcessMg;

public class J3Group {

    private PeerGroup peerGroup = null;

    private PeerGroup rootGroup = null;

    private DiscoveryService rootdiscoSvc = null;

    private static Hashtable knowPeerGroups = new Hashtable();

    private static Hashtable joinPeerGroups = new Hashtable();

    private static Hashtable knowPeers = new Hashtable();

    //    private GroupTable gt = null;

    private static ProcessMg peerDiscoveryServiceProcess = null;

    /**
     * 
     */
    private J3Group(PeerGroup this_, PeerGroup proot, DiscoveryService pdiscoSvc) {
        super();
        rootGroup = proot;
        rootdiscoSvc = pdiscoSvc;
        peerGroup = this_;
    }

    protected static J3Group getInstance(PeerGroupAdvertisement groupAdv) {
        return (J3Group) knowPeerGroups.get(groupAdv.getPeerGroupID());
    }

    protected static J3Group getInstance(PeerGroupAdvertisement groupAdv,
            PeerGroup proot, DiscoveryService pdiscoSvc) {
        J3Group pg = (J3Group) knowPeerGroups.get(groupAdv.getPeerGroupID());
        if (pg != null) return pg;
        try {
            PeerGroup peerGroup_temp = proot.newGroup(groupAdv);
            pg = new J3Group(peerGroup_temp, proot, pdiscoSvc);
            pg.publishGroup();
            knowPeerGroups.put(groupAdv.getPeerGroupID(), pg);
            System.out.println("Groupe trouvé : " + groupAdv.getName());

            // On veut trouver ses peers :
            peerDiscoveryServiceProcess = new ProcessMg(
                    new J3PeerDiscoveryListener(pg));
            peerDiscoveryServiceProcess.wakeUp();

        } catch (PeerGroupException e) {
            e.printStackTrace();
            System.out.println("Impossible de créé le groupe : "
                    + groupAdv.getName());
        }
        return pg;
    }

    public void publishGroup() {
        // Publication du groupe.
        try {
            rootdiscoSvc.publish(peerGroup.getPeerGroupAdvertisement());//,DiscoveryService.GROUP
            rootdiscoSvc.publish(peerGroup.getPeerAdvertisement());
            rootdiscoSvc.remotePublish(peerGroup.getPeerGroupAdvertisement());
            rootdiscoSvc.remotePublish(peerGroup.getPeerAdvertisement());
        } catch (Exception e) {
            System.out
                    .println("Failed to publish peer advertisement in the group ["
                            + peerGroup.getPeerGroupName() + "]");
        }

    }

    public synchronized void joinThisGroup() {

        if (joinPeerGroups.containsKey(peerGroup.getPeerGroupID())) return;

        //System.out.println("Joining peer group...");
        
        // On ne peut joindre un seul groupe pour l'instant !
        
        if (joinPeerGroups.size() > 0 ) return;

        StructuredDocument creds = null;

        try {
            // Generate the credentials for the Peer Group
            AuthenticationCredential authCred = new AuthenticationCredential(
                    peerGroup, null, creds);

            // Get the MembershipService from the peer group
            MembershipService membership = peerGroup.getMembershipService();

            // Get the Authenticator from the Authentication creds
            Authenticator auth = membership.apply(authCred);

            // Check if everything is okay to join the group
            if (auth.isReadyForJoin()) {
                Credential myCred = membership.join(auth);

                System.out.println("Successfully joined group "
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

                // Connection Ready !
                J3xta.setStatut(J3xta.JXTA_STATUT_ON);
                
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

    public PipeService getPipeService() {
        return peerGroup.getPipeService();
    }

    public void addPeer(J3Peer peer) {
        System.out.println(">>" + peer.getPeerID().toString());
        knowPeers.put(peer.getPeerID().toString(), peer);
        //        ArrayList al = new ArrayList(knowPeers.values());
        //        getGroupTableForDisplay().setFils(al);
    }

    /**
     * @param peerID
     * @return
     */
    public boolean existePeer(PeerID peerID) {
        System.out.println("??" + peerID.toString() + " : "
                + knowPeers.containsKey(peerID.toString()));
        return knowPeers.containsKey(peerID.toString());
    }

    public String toString() {

        return peerGroup.getPeerGroupName() + " (" + knowPeers + ")";

    }

    //    public GroupTable getGroupTableForDisplay() {
    //        return gt;
    //    }
    //
    //    public void setGroupTableForDisplay(GroupTable p) {
    //        gt = p;
    //    }

    public PeerGroupID getPeerGroupID() {
        return peerGroup.getPeerGroupID();
    }
    
    public static boolean isConnectToGroup() {
    	return !knowPeerGroups.isEmpty();
    }

}