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
import net.jxta.endpoint.MessageElement;
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

    private final static String SENDERNAME = "JxtaTalkSenderName";

    private final static String SENDERGROUPNAME = "GrpName";

    private final static String SENDERMESSAGE = "JxtaTalkSenderMessage";

    private InputPipe inputpipe;

    private OutputPipe outputpipe;

    private PipeService pipe;

    private DiscoveryService discovery;

    private PipeAdvertisement pipeAdv;

    private J3Group group;

    private int ping_id = 0;
    
    /**
     * the thread which creates (resolves) the output pipe and sends a message
     * once it's resolved
     */

    public void action() {
        try {

            discovery.publish(pipeAdv);
            sendMsg("ping");

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
     * @param gram
     *            message to send
     */
    public void sendMsg(String gram) {
        try {
            Message msg = new Message();
            msg.addMessageElement(null, new StringMessageElement(SENDERMESSAGE,
                    gram, null));
            msg.addMessageElement(null, new StringMessageElement(SENDERNAME,
                    StartJXTA.name, null));
            msg.addMessageElement(null, new StringMessageElement(
                    SENDERGROUPNAME, group.toString(), null));
            outputpipe.send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void pipeMsgEvent(PipeMsgEvent event) {
        Message msg = event.getMessage();
        try {
            String sender = getValue(msg, SENDERNAME, "Anonyme");
            String mesage = getValue(msg, SENDERMESSAGE, "??");
            J3MessagePipe.put(new J3Message(sender, mesage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getValue(Message msg, String tag, String defaut)
            throws Exception {

        MessageElement elem = msg.getMessageElement(null, tag);
        return elem == null ? defaut : new String(elem.getBytes(false));
    }

    /**
     * Generate uniquePipeID that is independantly unique within a group
     * 
     * @return The uniquePipeID value
     */
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

