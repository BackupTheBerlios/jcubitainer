package org.jcubitainer.p2p.jxta;

import java.util.Date;

import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.endpoint.Message.ElementIterator;
import net.jxta.id.IDFactory;
import net.jxta.impl.endpoint.WireFormatMessage;
import net.jxta.impl.endpoint.WireFormatMessageFactory;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.InputPipe;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.CountingOutputStream;
import net.jxta.util.DevNullOutputStream;

import org.jcubitainer.p2p.StartJXTA;
import org.jcubitainer.tools.Process;

/**
 * this application creates an instance of an input pipe,
 * and waits for msgs on the input pipe
 *
 */

public class J3PipeListener extends Process implements PipeMsgListener {

    private final static String SenderMessage = "PipeListenerMsg";

    private PipeService pipe;

    private PipeAdvertisement pipeAdv;

    private InputPipe pipeIn = null;

    private J3Group group = null;

    /**
     * Starts jxta
     *
     */
    public J3PipeListener(J3Group g) {
        super(20000);
        group = g;
        pipe = group.getPipeService();
        try {

            pipeAdv = (PipeAdvertisement) AdvertisementFactory
                    .newAdvertisement("jxta:PipeAdvertisement");

            pipeAdv.setPipeID(IDFactory.newPipeID((PeerGroupID) group
                    .getPeerGroupID(), "foo".getBytes()));
            pipeAdv.setName(J3xta.JXTA_ID + "partie.");

            pipeAdv.setType(PipeService.PropagateType);

        } catch (Exception e) {
            System.out.println("failed to read/parse pipe advertisement");
            e.printStackTrace();
        }
    }

    public void printMessageStats(Message msg, boolean verbose) {
        try {
            CountingOutputStream cnt;
            ElementIterator it = msg.getMessageElements();
            //            System.out
            //                    .println("------------------Begin Message---------------------");
            WireFormatMessage serialed = WireFormatMessageFactory.toWire(msg,
                    new MimeMediaType("application/x-jxta-msg"),
                    (MimeMediaType[]) null);
            //            System.out.println("Message Size :" + serialed.getByteLength());
            while (it.hasNext()) {
                MessageElement el = (MessageElement) it.next();
                String eName = el.getElementName();
                if ("PipeListenerMsg".equals(eName)) {
                    cnt = new CountingOutputStream(new DevNullOutputStream());
                    el.sendToStream(cnt);
                    long size = cnt.getBytesWritten();
                    //System.out.println("Element " + eName + " : " + size);
                    if (verbose) {
                        System.out.println(el + " et PONG de : "
                                + StartJXTA.name + " à " + new Date());
                    }
                }
            }
            //            System.out
            //                    .println("-------------------End Message----------------------");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * wait for msgs
     *
     */

    public void action() {

        try {
            // the following creates the inputpipe, and registers "this"
            // as the PipeMsgListener, when a message arrives pipeMsgEvent is called
            group.getPeerDiscoverService().publish(pipeAdv);
            group.getPeerDiscoverService().remotePublish(pipeAdv);
            pipeIn = pipe.createInputPipe(pipeAdv, this);
        } catch (Exception e) {
            return;
        }
        if (pipeIn == null) {
            System.out.println(" cannot open InputPipe");
        }
        //        System.out.println("Waiting for msgs on input pipe");
    }

    /**
     * By implementing PipeMsgListener, define this method to deal with
     * messages as they arrive
     */

    public void pipeMsgEvent(PipeMsgEvent event) {

        Message msg = null;
        try {
            // grab the message from the event
            msg = event.getMessage();
            if (msg == null) { return; }
            printMessageStats(msg, true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // get all the message elements
        Message.ElementIterator enum = msg.getMessageElements();
        if (!enum.hasNext()) { return; }

        // get the message element named SenderMessage
        MessageElement msgElement = msg.getMessageElement(null, SenderMessage);
        // Get message
        if (msgElement.toString() == null) {
            System.out.println("null msg received");
        } else {
            //            Date date = new Date(System.currentTimeMillis());
            //            System.out.println("Message received at :" + date.toString());
            //            System.out.println("Message  created at :" + msgElement.toString());
        }
    }

}

