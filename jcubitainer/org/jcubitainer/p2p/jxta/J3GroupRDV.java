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

import java.io.StringWriter;
import java.util.Date;

import net.jxta.credential.AuthenticationCredential;
import net.jxta.credential.Credential;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredTextDocument;
import net.jxta.membership.Authenticator;
import net.jxta.membership.MembershipService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.ModuleImplAdvertisement;

import org.jcubitainer.tools.Process;

public class J3GroupRDV extends Process implements Group {

    public static final String NAME = "Partie#";

    public static final String DESCRIPTION = "J3xtainer Groupe V0.3";

    private PeerGroup peerGroup = null;

    private PeerGroup rootGroup = null;

    private DiscoveryService discoSvc = null;

    /**
     * 
     */
    public J3GroupRDV(PeerGroup proot, DiscoveryService pdiscoSvc) {
        super(180000);
        rootGroup = proot;
        discoSvc = pdiscoSvc;
        //		PeerGroupID peerGroupID = net.jxta.id.IDFactory.newPeerGroupID();

        try {
            ModuleImplAdvertisement implAdv = rootGroup
                    .getAllPurposePeerGroupImplAdvertisement();
            peerGroup = rootGroup.newGroup(null, implAdv, J3xta.JXTA_ID + NAME
                    + new Date(), DESCRIPTION);

            peerGroup.getRendezVousService().startRendezVous();

            System.out.println("Groupe créé :" + peerGroup.getPeerGroupName());
            //System.out.println("Groupe détail :" + peerGroup.toString());

        } catch (Exception e) {
            System.err.println("Group creation failed");
            System.err.println(e);
        }

    }

    public void publishGroup() {
        // Publication du groupe.
        try {
            discoSvc.publish(peerGroup.getPeerGroupAdvertisement(),
                    DiscoveryService.GROUP);
            discoSvc.publish(peerGroup.getPeerAdvertisement(),
                    DiscoveryService.PEER);
            discoSvc.remotePublish(peerGroup.getPeerGroupAdvertisement(),
                    DiscoveryService.GROUP);
            discoSvc.remotePublish(peerGroup.getPeerAdvertisement(),
                    DiscoveryService.PEER);
        } catch (Exception e) {
            System.out
                    .println("Failed to publish peer advertisement in the group ["
                            + peerGroup.getPeerGroupName() + "]");
        }

    }

    public void action() throws InterruptedException {
        //		System.out.println("Publication");
        publishGroup();

    }

    public void joinThisGroup() {
        System.out.println("Joining peer group...");

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

                // display the credential as a plain text document.
                System.out.println("Credential: ");
                StructuredTextDocument doc = (StructuredTextDocument) myCred
                        .getDocument(new MimeMediaType("text/plain"));

                StringWriter out = new StringWriter();
                doc.sendToWriter(out);
                System.out.println(out.toString());
                out.close();
            } else {
                System.out.println("Failure: unable to join group");
            }
        } catch (Exception e) {
            System.out.println("Failure in authentication." + e);
        }

    }

}