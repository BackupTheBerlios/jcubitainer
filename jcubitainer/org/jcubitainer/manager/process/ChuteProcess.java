/*
 * Created on 14 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager.process;

import java.util.Collections;
import java.util.List;

import org.jcubitainer.manager.Game;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.move.MoveBoard;
import org.jcubitainer.tools.PieceComparator;
import org.jcubitainer.tools.Process;

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ChuteProcess extends Process {

	MetaBoard metabox = null;
	MoveBoard dbox = null;
	boolean slow = false;
	int boucle = 0;

	/**
	 *  
	 */
	public ChuteProcess(MoveBoard pb) {
		super(1800);
		metabox = pb.getMetabox();
		dbox = pb;
		setPriority(MAX_PRIORITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jcubitainer.manager.tools.Process#action()
	 */
	public void action() throws InterruptedException {

		if (slow) {
			boucle--;
			if (boucle < 0) {
				slow = false;
				Game.getGameService().getBonus().stopSlow();
				boucle = 0;
			}
		}
		dbox.getMetaInfo().addScore(1);

		synchronized (metabox.getPieces_mouvantes()) {
			List liste = metabox.getPieces_mouvantes();
			Collections.sort(liste, new PieceComparator());
			((MoveBoard) dbox).getMovepiece().downPieces(liste, boucle);
		}
		//dbox.repaint();
	}

	public void setSlow() {
		slow = true;
		boucle = 60;
	}

}
