/*
 * Created on 25 mai 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.jcubitainer.aspect;

import org.jcubitainer.p2p.jxta.*;
import org.jcubitainer.display.DisplayBoard;
import org.jcubitainer.display.infopanel.*;
import org.jcubitainer.p2p.jxta.util.*;

/**
 * @author mounes
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public aspect RefreshNetworkAspect {

	pointcut showStatut() : call(void J3xta.setStatut(..));

	after() : showStatut() {
	    int s = J3xta.getStatut();
	    DisplayInfo di = DisplayInfo.getThis();
	    di.setNetwork(s);
	}
	
	pointcut showMessage() : call(void J3MessagePipe.put(..));

	after() : showMessage() {
	    J3Message message = J3MessagePipe.drop();
	    if ( message != null ) 	        
	        DisplayBoard.getThis().getMetabox().getTexte().setTexte(
	                message.getWho() + ":" + message.getWhat());
	}

	pointcut endGame() : call(void J3PeerManager.remove(..));

	after() : endGame() {
	    if( J3PeerManager.size() == 0)
	        DisplayBoard.getThis().getMetabox().getTexte().setTexte("Personne !");
	}

	pointcut newGame() : call(void J3PeerManager.put(..));

	after() : newGame() {
	    if( J3PeerManager.size() != 0)
	        DisplayBoard.getThis().getMetabox().getTexte().setTexte("Game OK !");
	}
}
