/*
 * Created on 22 févr. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.aspect;

import org.jcubitainer.meta.*;
import org.jcubitainer.manager.*;
import org.jcubitainer.manager.process.TexteProcess;

/**
 * @author MetalM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect TexteDisplayAspect {

	pointcut showLevelText() : call(void MetaTexte.setTexte(..));

	after() : showLevelText() {
		(
			(TexteProcess)
				(Game.getGameService().getTextTimer().getProcess())).setBoucle(
			(int) TexteProcess.MAX_BOUCLE);
		Game.getGameService().getTextTimer().wakeUp();
	}

}
