package org.jcubitainer.tools;

import org.jcubitainer.display.table.DisplayPlayer;
import org.jcubitainer.p2p.jxta.J3Peer;

public class PeerContainer extends DisplayPlayer {

    J3Peer peer = null;

    public PeerContainer(J3Peer ppeer) {
        super(ppeer.getName());
        peer = ppeer;
    }

}