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

package org.jcubitainer.aspect;

import java.util.Enumeration;

import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.NetworkManager;
import org.jcubitainer.p2p.StartJXTA;
import org.jcubitainer.p2p.jxta.*;
import org.jcubitainer.display.DisplayBoard;
import org.jcubitainer.display.infopanel.*;
import org.jcubitainer.manager.*;
import org.jcubitainer.p2p.jxta.util.*;
import org.jcubitainer.manager.process.*;

public aspect RefreshNetworkAspect {

    public static String MALUS_PIECE = "MALUS!PIECE";
    
    public static String MALUS_LINE = "MALUS!LINE";

    public static String MALUS_SPEED = "MALUS!SPEED";

    pointcut showStatut() : call(void J3xta.setStatut(..));

	after() : showStatut() {
	    int s = J3xta.getStatut();
	    DisplayInfo di = DisplayInfo.getThis();
	    di.setNetwork(s);
	}
	
	pointcut showMessage() : call(void J3MessagePipe.put(..));

	after() : showMessage() {
	    J3Message message = J3MessagePipe.drop();
	    if ( message != null && !StartJXTA.name.equals(message.getWho()) ){
	        // On ne veut pas recevoir ces propres messages !
	        DisplayBoard.getThis().getMetabox().getTexte().setTexte(
	                message.getWho() + ":" + message.getWhat());
	        if ( MALUS_PIECE.equals(message.getWhat()))
	            ((MoveBoard)DisplayBoard.getThis()).newPiece();
	        if ( MALUS_LINE.equals(message.getWhat())) {
	            ((MoveBoard)DisplayBoard.getThis()).newLine();
	            ((MoveBoard)DisplayBoard.getThis()).newLine();
	        }
	        if ( MALUS_SPEED.equals(message.getWhat()))
                ((ChuteProcess)Game.getGameService().getTimer().getProcess()).setFast();
	    }
	}

	pointcut endGame() : call(void J3PeerManager.remove(..));

	after() : endGame() {
	    if( J3PeerManager.size() == 0) {
	        NetworkManager.endGame();
	        DisplayInfo.getThis().setRechercheVisible(true);
	    }
	}

	pointcut newGame() : call(void J3PeerManager.addPeer(..));

	after() : newGame() {
        NetworkManager.startGame();
	    DisplayInfo di = DisplayInfo.getThis();
        di.setRechercheVisible(false);
        di.setHitDisplayWithNoBorder(di.getMetaInfo().getHit());
	}

	pointcut envoyerMalusPiece() : call(void Bonus.deletePiece());

	after() : envoyerMalusPiece() {
	    sendMsg(MALUS_PIECE);
	}

	pointcut envoyerMalusLine() : call(void Bonus.deleteLine());

	after() : envoyerMalusLine() {
	    sendMsg(MALUS_LINE);
	}

	pointcut envoyerMalusSpeed() : call(void Bonus.slow());

	after() : envoyerMalusSpeed() {
	    sendMsg(MALUS_SPEED);
	}
	
	public static void sendMsg(String malus) {
        if (NetworkManager.isNetworkOn()) {
            Enumeration liste = J3Group.getJ3Groups();
            J3Pipe pipe = null;
            if (liste != null) 
                if(liste.hasMoreElements()){
                    J3Group group = (J3Group)liste.nextElement();
                    pipe = group.getPipe();
                }
            if ( pipe != null ) 
                pipe.sendMsg(malus);
        }                    
	}
}
