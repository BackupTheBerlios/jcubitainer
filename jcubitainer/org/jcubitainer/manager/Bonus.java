/*
 * Created on 19 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager;

import org.jcubitainer.meta.MetaInfo;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Bonus {

	MetaInfo mi = null;

	/**
	 * 
	 */
	public Bonus(MetaInfo pdi) {
		super();
		mi = pdi;
		clean();
	}

	/**
	 * 
	 */
	public void clean() {
		mi.setBonus_des(1);
		mi.setBonus_sup(1);
		mi.setBonus_slow(1);
	}

	public boolean canDeletePiece() {
		return mi.getBonus_des() > 0;
	}

	public void deletePiece() {
		mi.setBonus_des(mi.getBonus_des() - 1);
	}

	public void bonusDeletePiece() {
		mi.setBonus_des(mi.getBonus_des() + 1);
	}

	public boolean canDeleteLine() {
		return mi.getBonus_sup() > 0;
	}

	public void deleteLine() {
		mi.setBonus_sup(mi.getBonus_sup() - 1);
	}

	public void bonusDeleteLine() {
		mi.setBonus_sup(mi.getBonus_sup() + 1);
	}

	public boolean canSlow() {
		return mi.getBonus_slow() > 0;
	}

	public void slow() {
		mi.setBonus_slow(mi.getBonus_slow() - 1);
	}

	public void stopSlow() {
		//di.activeSlow(false);
	}

	public void bonusSlow() {
		mi.setBonus_slow(mi.getBonus_slow() + 1);
	}

	public void newBonusByLine(int line) {
		if (line == 2)
			bonusDeletePiece();
		if (line == 3)
			bonusDeleteLine();
		if (line == 4)
			bonusSlow();
		if (line == 5) {
			bonusDeletePiece();
			bonusSlow();
		}
		if (line == 6) {
			bonusDeleteLine();
			bonusSlow();
		}
		if (line > 6) {
			bonusDeletePiece();
			bonusDeleteLine();
			bonusSlow();
		}
	}
}
