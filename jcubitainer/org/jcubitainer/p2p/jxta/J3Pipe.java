package org.jcubitainer.p2p.jxta;

import java.io.IOException;
import java.util.Date;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.OutputPipeEvent;
import net.jxta.pipe.OutputPipeListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;

import org.jcubitainer.p2p.StartJXTA;
import org.jcubitainer.tools.Process;

/**
 *  This exapmle illustrates how to use the OutputPipeListener interface
 *
 */

public class J3Pipe extends Process implements OutputPipeListener,
        RendezvousListener {

    private final static String SenderMessage = "PipeListenerMsg";

    private PipeService pipe;

    private DiscoveryService discovery;

    private PipeAdvertisement pipeAdv;

    private J3GroupRDV group;

    private int ping_id = 0;

    //    private RendezVousService rendezvous;

    /**
     *  the thread which creates (resolves) the output pipe
     *  and sends a message once it's resolved
     */

    public void action() {
        try {
            // this step helps when running standalone (local sub-net without any redezvous setup)
            discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, null,
                    null, 1, null);
            // create output pipe with asynchronously
            // Send out the first pipe resolve call
            //            System.out.println("Attempting to create a OutputPipe");
            pipe.createOutputPipe(pipeAdv, this);
            // send out a second pipe resolution after we connect
            // to a rendezvous
            if (false) {//!rendezvous.isConnectedToRendezVous()) {
                System.out.println("Waiting for Rendezvous Connection");
                try {
                    wait();
                    System.out
                            .println("Connected to Rendezvous, attempting to create a OutputPipe");
                    pipe.createOutputPipe(pipeAdv, this);
                } catch (InterruptedException e) {
                    // got our notification
                }
            }
        } catch (IOException e) {
            System.out.println("OutputPipe creation failure");
            e.printStackTrace();
        }
    }

    /**
     *  by implementing OutputPipeListener we must define this method which
     *  is called when the output pipe is created
     *
     *@param  event  event object from which to get output pipe object
     */

    public void outputPipeEvent(OutputPipeEvent event) {

        //        System.out.println(" Got an output pipe event");
        OutputPipe op = event.getOutputPipe();
        Message msg = null;

        try {
            msg = new Message();
            Date date = new Date(System.currentTimeMillis());
            StringMessageElement sme = new StringMessageElement(SenderMessage,
                    ping_id + "ping de (" + StartJXTA.name + " ) : "
                            + date.toString(), null);
            System.out.println(ping_id + "ping de (" + StartJXTA.name + " ) : "
                    + date.toString());
            ping_id++;
            msg.addMessageElement(null, sme);
            op.send(msg);
        } catch (IOException e) {
            System.out.println("failed to send message");
            e.printStackTrace();
        }
        op.close();
        //pause();
        //        System.out.println("message sent");
    }

    /**
     *  rendezvousEvent the rendezvous event
     *
     *@param  event   rendezvousEvent
     */
    public synchronized void rendezvousEvent(RendezvousEvent event) {
        if (event.getType() == RendezvousEvent.RDVCONNECT) {
            notify();
        }
    }

    /**
     *  Starts jxta, and get the pipe, and discovery service
     */
    public J3Pipe(J3GroupRDV g) {
        super(10000);

        group = g;
        // get the pipe service, and discovery
        pipe = group.getPipeService();
        discovery = group.getDiscoveryService();

        pipeAdv = (PipeAdvertisement) AdvertisementFactory
                .newAdvertisement("jxta:PipeAdvertisement");
        pipeAdv.setPipeID(IDFactory.newPipeID((PeerGroupID) group
                .getPeerGroupID(), "foo".getBytes()));
        pipeAdv.setName(J3xta.JXTA_ID + "partie.");

        pipeAdv.setType(PipeService.PropagateType);

    }
}

