/*
 * Created on 22 févr. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager.process;

import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaTexte;
import org.jcubitainer.tools.Process;

/**
 * @author MetalM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TexteProcess extends Process {

	private int boucle = 0;
	MetaTexte mt = null;
	public static final float MAX_BOUCLE = 40f;

	public TexteProcess(MetaBoard mb) {
		super(100);
		mt = mb.getTexte();
	}

	/* (non-Javadoc)
	 * @see org.jcubitainer.tools.Process#action()
	 */
	public void action() throws InterruptedException {
		if (boucle > 0) {
			boucle--;
			float alpha = ((float) boucle) / MAX_BOUCLE;
			if (alpha > 0.5)
				alpha = 0.5f;
			mt.setAlpha(alpha);
			mt.setDisplay(true);
		} else {
			mt.setDisplay(false);
			pause();
		}
	}

	/**
	 * @param i
	 */
	public void setBoucle(int i) {
		boucle = i;
	}

}
