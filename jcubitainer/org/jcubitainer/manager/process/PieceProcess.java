/*
 * Created on 16 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager.process;

import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.PieceFactory;
import org.jcubitainer.meta.MetaPiece;
import org.jcubitainer.tools.Process;

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PieceProcess extends Process {

	MoveBoard dbox = null;
	PieceFactory dpf = null;
	private static PieceProcess this_ = null;
	/**
	 *  
	 */
	public PieceProcess(MoveBoard mb) {
		super(5000);
		dbox = mb;
		dpf = PieceFactory.getInstance();
		setPriority(MAX_PRIORITY);
		this_ = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jcubitainer.manager.tools.Process#action()
	 */
	public void action() throws InterruptedException {
		synchronized (dbox.getMetabox().getPieces_mouvantes()) {
			MetaPiece mp = dpf.getDisplayPiece(dbox.getMetaInfo().getNiveau());
			((MoveBoard) dbox).getMovepiece().addPiece(mp);
		}
	}

	public static PieceProcess getPieceService() {
		return this_;
	}

}
