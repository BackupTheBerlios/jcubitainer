/*
 * Created on 15 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.tools;

import java.util.Comparator;

import org.jcubitainer.meta.MetaPiece;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PieceComparator implements Comparator {

	/**
	 * 
	 */
	public PieceComparator() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		return compare((MetaPiece) arg0, (MetaPiece) arg1);
	}

	public int compare(MetaPiece arg0, MetaPiece arg1) {
		if (arg0.getPosition_y() < arg1.getPosition_y())
			return 1;
		else
			return -1;
	}

}
