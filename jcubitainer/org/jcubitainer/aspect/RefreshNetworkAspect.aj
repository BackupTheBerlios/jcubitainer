/*
 * Created on 25 mai 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.jcubitainer.aspect;

import org.jcubitainer.p2p.jxta.J3xta;
import org.jcubitainer.display.infopanel.*;

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

}
