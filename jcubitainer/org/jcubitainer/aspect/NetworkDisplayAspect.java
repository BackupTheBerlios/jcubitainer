/*
 * Created on 23 mars 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.aspect;

import org.jcubitainer.display.table.NetworkDisplay;
import java.awt.event.ActionEvent;

/**
 * @author MetalM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect NetworkDisplayAspect {

	pointcut evenementKey(NetworkDisplay nd) : call(
		void NetworkDisplay.action(..))
		&& target(nd);

	after(NetworkDisplay nd) : evenementKey(nd) {
		System.out.println("action :" + nd.getLastAction());
	}

}
