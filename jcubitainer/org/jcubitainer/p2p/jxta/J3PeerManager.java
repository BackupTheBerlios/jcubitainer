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

/* History & changes **************************************************
 *                                                                     *
 ******** December 13, 2004 ********************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.p2p.jxta;

import java.util.Enumeration;
import java.util.Hashtable;

import org.jcubitainer.tools.Process;
import org.jcubitainer.tools.ProcessMg;

public class J3PeerManager extends Process {

    static Hashtable peers_int = new Hashtable(20);

    static Hashtable peers_name = new Hashtable(20);

    static ProcessMg manager = new ProcessMg(new J3PeerManager());

    public J3PeerManager() {
        super(15 * 1000);
    }

    public void action() throws InterruptedException {
        synchronized (peers_int) {
            Enumeration keys = peers_int.keys();
            while (keys.hasMoreElements()) {
                String peer_id = (String) keys.nextElement();
                J3Peer peer = getPeer(peer_id);
                int nb = getInt(peer);
                if (nb < 1) {
                    System.out.println("! Suppression du peer non actif : "
                            + peer.getName());
                    remove(peer);
                } else
                    put(peer, String.valueOf(nb - 1));
            }
        }
    }

    public static void addPeer(J3Peer peer) {
        manager.wakeUp();
        int nb = getInt(peer);
        if (nb == 0) {
            System.out.println("! Nouveau Peer actif : " + peer.getName());
            put(peer, String.valueOf(3));
        } else if (nb < 11)
            put(peer, String.valueOf(nb + 1));
    }

    private static int getInt(J3Peer p) {
        String temp = (String) peers_int.get(p.toString());
        int nb = 0;
        try {
            nb = Integer.valueOf(temp).intValue();
        } catch (NumberFormatException e) {
        }
        return nb;
    }

    private static void remove(J3Peer peer) {
        peers_int.remove(peer.toString());
        peers_name.remove(peer.toString());
    }

    private static void put(J3Peer peer, String s) {
        peers_int.put(peer.toString(), s);
        peers_name.put(peer.toString(), peer);
    }

    private static J3Peer getPeer(String s) {
        return (J3Peer) peers_name.get(s);
    }

    public static int size() {
        return peers_name.size();
    }

    public static Enumeration getAll() {
        return peers_name.elements();
    }
}