/*
 * Created on 21 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager;

import org.jcubitainer.manager.process.ChuteProcess;
import org.jcubitainer.manager.process.PieceProcess;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.meta.MetaTexte;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Level {

	MetaInfo mi = null;
	MetaTexte mt = null;
	/**
	 * 
	 */
	public Level(MetaTexte pmt, MetaInfo pdi) {
		super();
		mi = pdi;
		mt = pmt;
	}

	public void setLevel(ChuteProcess cs) {
		int score = mi.getScore();
		int level = mi.getNiveau();
		//System.out.println(score);
		PieceProcess ps = PieceProcess.getPieceService();
		if (score > mi.getNiveauSuivant()) {
			// Pas de changement de niveau la premi�re fois :
			if (mi.getNiveauSuivant() != 0)
				mi.setNiveau(mi.getNiveau() + 1);
			mi.setNiveauSuivant(mi.getNiveauSuivant() + 2000);
			mt.setTexte("Niveau " + mi.getNiveau() + " !");
			setSpeedByLevel(cs);
			return;
		}
	}

	public void setSpeedByLevel(ChuteProcess cs) {
		int level = mi.getNiveau();
		PieceProcess ps = PieceProcess.getPieceService();
		if (level == 1) {
			cs.setWait(1800);
			ps.setWait(5000);
			return;
		}
		if (level == 2) {
			cs.setWait(1500);
			ps.setWait(4500);
			return;
		}
		if (level == 3) {
			cs.setWait(1100);
			ps.setWait(4000);
			return;
		}
		if (level == 4) {
			cs.setWait(800);
			ps.setWait(3500);
			return;
		}
		if (level == 5) {
			cs.setWait(700);
			ps.setWait(3000);
			return;
		}
		if (level == 6) {
			cs.setWait(600);
			ps.setWait(2500);
			return;
		}
		if (level == 7) {
			cs.setWait(550);
			ps.setWait(2000);
			return;
		}
		if (level == 8) {
			cs.setWait(500);
			ps.setWait(1500);
			return;
		}
		if (level == 9) {
			cs.setWait(400);
			ps.setWait(1500);
			return;
		} else {
			cs.setWait(300);
			ps.setWait(1000);
			return;
		}
	}
}
