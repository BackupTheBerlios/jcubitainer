/*
 * Created on 15 janv. 2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.move;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import org.jcubitainer.display.DisplayBoard;
import org.jcubitainer.manager.Bonus;
import org.jcubitainer.manager.Game;
import org.jcubitainer.manager.PieceFactory;
import org.jcubitainer.manager.process.ChuteProcess;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.meta.MetaPiece;

/**
 * @author rom
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MoveBoard
	extends DisplayBoard
	implements MouseListener, KeyListener {

	private MovePiece movepiece = null;
	private boolean checkMove = false;

	/**
	 * @param pmetabox
	 */
	public MoveBoard(MetaBoard pmetabox, MetaInfo pmi) {
		super(pmetabox, pmi);
		addKeyListener(this);
		movepiece = new MovePiece(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	public void keyPressed(KeyEvent arg0) {

		int keyCode = arg0.getKeyCode();

		if (keyCode == KeyEvent.VK_P && !getMetaInfo().isGame_over()) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				Game gs = Game.getGameService();
				if (gs.isPause())
					gs.start();
				else
					gs.pause();
			}
		}

		if (!checkMove)
			return; // jeu en pause;

		if (keyCode == KeyEvent.VK_R) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				Game gs = Game.getGameService();
				Bonus bonus = gs.getBonus();
				MetaBoard mb = getMetabox();
				if (bonus.canDeleteLine()) {
					bonus.deleteLine();
					mb.removeLastLines();
				}
				return;
			}
		} else if (keyCode == KeyEvent.VK_S) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				Game gs = Game.getGameService();
				Bonus bonus = gs.getBonus();
				MetaBoard mb = getMetabox();
				if (bonus.canSlow()) {
					bonus.slow();
					((ChuteProcess) gs.getTimer().getProcess()).setSlow();
				}
				return;
			}
		} else if (keyCode == KeyEvent.VK_D) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				Game gs = Game.getGameService();
				Bonus bonus = gs.getBonus();
				MetaBoard mb = getMetabox();
				if (bonus.canDeletePiece()) {
					MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
					if (mp != null) {
						bonus.deletePiece();
						mb.removePiece(mp);
					}
				}
				return;
			}
		} else if (keyCode == KeyEvent.VK_N) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				PieceFactory dps = PieceFactory.getInstance();
				if (!movepiece
					.forceAddPiece(
						dps.getDisplayPiece(getMetaInfo().getNiveau()))) {
					//System.out.println(
					//	"Impossible de forcer l'ajout d'une pièce !");
				}
			}
		} else if (keyCode == KeyEvent.VK_J) {
			getMetabox().getTexte().setTexte("J3itainer02");
		} else if (keyCode == KeyEvent.VK_L) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				// On mettre 1 car on ne veut pas que les pièces descendent.
				movepiece.downPieces(getMetabox().getPieces_mouvantes(), 1);
				getMetabox().upLines();
			}
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaBoard mb = getMetabox();
				MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
				if (mp == null)
					return;
				movepiece.right(mp);
			}
		} else if (keyCode == KeyEvent.VK_LEFT) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaBoard mb = getMetabox();
				MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
				if (mp == null)
					return;
				movepiece.left(mp);
			}
		} else if (keyCode == KeyEvent.VK_UP) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaBoard mb = getMetabox();
				MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
				if (mp == null)
					return;
				movepiece.up(mp);
			}
		} else if (keyCode == KeyEvent.VK_DOWN) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaBoard mb = getMetabox();
				MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
				if (mp == null)
					return;
				List liste = new ArrayList();
				liste.add(mp);
				movepiece.downPieces(liste, 0);
			}
		} else if (keyCode == KeyEvent.VK_ENTER) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaBoard mb = getMetabox();
				MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
				if (mp == null)
					return;
				movepiece.forceDownPiece(mp);
				return;
			}
		} else if (keyCode == KeyEvent.VK_SPACE) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaBoard mb = getMetabox();
				MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
				if (mp == null)
					return;
				movepiece.rotation(mp);
			}
		} else if (keyCode == KeyEvent.VK_C) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaPiece old_dp = (MetaPiece) getMetabox().getCurrentPiece();
				if (old_dp == null)
					return;
				MetaPiece new_dp =
					(MetaPiece) getMetabox().changeCurrentPiece(true);
			}
		} else if (keyCode == KeyEvent.VK_V) {
			synchronized (getMetabox().getPieces_mouvantes()) {
				MetaPiece old_dp = (MetaPiece) getMetabox().getCurrentPiece();
				if (old_dp == null)
					return;
				MetaPiece new_dp =
					(MetaPiece) getMetabox().changeCurrentPiece(false);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {

	}

	/**
	 * @return
	 */
	public MovePiece getMovepiece() {
		return movepiece;
	}

	/**
	 * @param b
	 */
	public void setCheckMove(boolean b) {
		checkMove = b;
	}

}
