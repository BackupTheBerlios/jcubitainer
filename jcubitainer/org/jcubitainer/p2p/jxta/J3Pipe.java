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
 ******** December 12, 2004 ********************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.p2p.jxta;

import java.io.IOException;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.id.IDFactory;
import net.jxta.pipe.InputPipe;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;

import org.jcubitainer.p2p.StartJXTA;
import org.jcubitainer.p2p.jxta.util.J3Message;
import org.jcubitainer.p2p.jxta.util.J3MessagePipe;
import org.jcubitainer.tools.Process;

public class J3Pipe extends Process implements PipeMsgListener {

    public final static String SENDERNAME = "JxtaTalkSenderName";

    public final static String SENDERGROUPNAME = "GrpName";

    public final static String SENDERMESSAGE = "Jxta Talk Sender Message";

    public final static String SYSTEMMESSAGE = "Jx3Message";

    public final static String PEERID = "Peer_ID";

    private InputPipe inputpipe;

    private OutputPipe outputpipe;

    private PipeService pipe;

    private DiscoveryService discovery;

    private PipeAdvertisement pipeAdv;

    private J3Group group;

    private int ping_id = 0;

    private static final String MESSAGE_PING = "ping";

    private static final String MESSAGE_REMOVE = "remove";

    /**
     * the thread which creates (resolves) the output pipe and sends a message
     * once it's resolved
     */

    public void action() {
        try {

            discovery.publish(pipeAdv);
            sendMsg(MESSAGE_PING, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts jxta, and get the pipe, and discovery service
     */
    public J3Pipe(J3Group g) {
        super(10000);

        group = g;

        pipe = g.getPipeService();

        // get the pipe service, and discovery
        discovery = group.getDiscoveryService();

        pipeAdv = (PipeAdvertisement) AdvertisementFactory
                .newAdvertisement(PipeAdvertisement.getAdvertisementType());

        pipeAdv.setPipeID(getUniquePipeID());
        pipeAdv.setName(J3xta.JXTA_ID + "PIPE" + StartJXTA.name);
        pipeAdv.setType(PipeService.PropagateType);

        try {
            inputpipe = pipe.createInputPipe(pipeAdv, this);
            outputpipe = pipe.createOutputPipe(pipeAdv, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Enqueue messages
     * 
     * @param message
     *            message to send
     */
    private void sendMsg(String message, boolean system) {
        try {
            Message msg = new Message();
            msg.addMessageElement(null, new StringMessageElement(SENDERMESSAGE,
                    message, null));
            msg.addMessageElement(null, new StringMessageElement(SENDERNAME,
                    StartJXTA.name, null));
            msg.addMessageElement(null, new StringMessageElement(
                    SENDERGROUPNAME, group.toString(), null));
            msg.addMessageElement(null, new StringMessageElement(PEERID, group
                    .getPeerID().toString(), null));

            if (system)
                msg.addMessageElement(null, new StringMessageElement(
                        SYSTEMMESSAGE, "yes", null));
            outputpipe.send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void sendMsg(String message) {
        sendMsg(message, false);
    }

    public void pipeMsgEvent(PipeMsgEvent event) {
        J3Message mes = new J3Message(event.getMessage());

        if (mes.isSystem()) {
            if (MESSAGE_PING.equals(mes.getWhat())
                    && !StartJXTA.name.equals(mes.getWho())) {
                J3Peer peer = new J3Peer(mes.getPeer_id(), mes.getWho());
                J3PeerManager.addPeer(peer);
            }
        } else
            J3MessagePipe.put(mes);
    }

    private PipeID getUniquePipeID() {

        byte[] preCookedPID = { (byte) 0xD1, (byte) 0xD1, (byte) 0xD1,
                (byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1,
                (byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1,
                (byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1 };

        PipeID id = (PipeID) IDFactory.newPipeID(group.getPeerGroupID(),
                preCookedPID);

        return id;

    }

}

