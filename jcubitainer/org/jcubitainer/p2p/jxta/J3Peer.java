/*
 * Created on 10 mars 2004
 *
 */
package org.jcubitainer.p2p.jxta;

import net.jxta.peer.PeerID;
import net.jxta.protocol.PeerAdvertisement;

/**
 * @author metalm
 *
 */
public class J3Peer {

	PeerAdvertisement adv = null;

	/**
	 * @param adv
	 */
	public J3Peer(PeerAdvertisement padv) {
		adv = padv;
	}

	public PeerID getPeerID() {
		return adv.getPeerID();
	}

	public String toString() {
		return adv.getName();
	}

}
