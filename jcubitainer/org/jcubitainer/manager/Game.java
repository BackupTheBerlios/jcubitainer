/*
 * Created on 19 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.manager;

import org.jcubitainer.manager.process.ChuteProcess;
import org.jcubitainer.manager.process.PieceProcess;
import org.jcubitainer.manager.process.TexteProcess;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.move.MoveBoard;
import org.jcubitainer.tools.ProcessMg;

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Game {

	static ProcessMg timer = null;
	static ProcessMg timer_new_piece = null;
	static ProcessMg timer_texte = null;

	private static Game _this = null;
	private static Bonus bonus = null;
	private MoveBoard db = null;
	private Level levelmanager = null;
	private MetaInfo dinfo = null;

	/**
	 *  
	 */
	public Game(MoveBoard pdb, MetaInfo di) {
		super();
		db = pdb;
		timer = new ProcessMg(new ChuteProcess(db));
		timer_new_piece = new ProcessMg(new PieceProcess(db));
		timer_texte = new ProcessMg(new TexteProcess(db.getMetabox()));
		_this = this;
		dinfo = di;
		bonus = new Bonus(di);
		levelmanager = new Level(db.getMetabox().getTexte(), di);
	}

	public static Game getGameService() {
		return _this;
	}

	public void start() {
		if (dinfo.isGame_over()) {
			bonus.clean();
			dinfo.clean();
			((MoveBoard) db).getMetabox().createEmptyBoard();
			//dinfo.repaint();
		}
		dinfo.setPause(false);
		((MoveBoard) db).setCheckMove(true);
		levelmanager.setLevel((ChuteProcess) timer.getProcess());
		timer.wakeUp();
		timer_new_piece.wakeUp();
	}

	public void pause() {
		timer.pause();
		dinfo.setPause(true);
		timer_new_piece.pause();
		((MoveBoard) db).setCheckMove(false);
	}

	public boolean isPause() {
		return timer.isStop();
	}

	/**
	 * @return
	 */
	public Bonus getBonus() {
		return bonus;
	}

	public void endGame() {
		pause();
		db.getMetabox().fixAll();
		dinfo.setGame_over(true);
		dinfo.setPause(false);
		db.getMetabox().getTexte().setTexte("Game Over !");
	}

	/**
	 * @return
	 */
	public ProcessMg getTimer() {
		return timer;
	}

	public ProcessMg getTextTimer() {
		return timer_texte;
	}

	/**
	 * @return
	 */
	public Level getLevelmanager() {
		return levelmanager;
	}
}
