/*
 * Created on 14 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jcubitainer.tools.PieceComparator;
import org.jcubitainer.tools.TableauTools;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaBoard {

	int largeur = 0;
	int hauteur = 0;
	List pieces_mouvantes = new ArrayList();
	int[][] pieces_mortes = null;
	MetaPiece current = null;
	MetaOmbre ombre = null;
	MetaTexte texte = new MetaTexte();

	/**
	 * 
	 */
	public MetaBoard(int plargeur, int phauteur) {
		super();
		largeur = plargeur;
		hauteur = phauteur;
		createEmptyBoard();
		ombre = new MetaOmbre(this);
	}

	/**
	 * 
	 */
	public void createEmptyBoard() {
		pieces_mortes = new int[hauteur][largeur];
		pieces_mouvantes.clear();
	}

	/**
	 * @return
	 */
	public int getLargeur() {
		return largeur;
	}

	/**
	 * @return
	 */
	public int getHauteur() {
		return hauteur;
	}

	public void addPiece(MetaPiece mp) {
		pieces_mouvantes.add(mp);
		Collections.sort(pieces_mouvantes, new PieceComparator());
		if (current == null)
			setCurrent(mp);
	}

	/**
	 * @return
	 */
	public List getPieces_mouvantes() {
		return pieces_mouvantes;
	}

	/**
	 * @return
	 */
	public MetaPiece getCurrentPiece() {
		return current;
	}

	public MetaPiece changeCurrentPiece(boolean sens) {
		synchronized (this) {
			if (pieces_mouvantes.size() > 0) {
				int pos = pieces_mouvantes.indexOf(current);
				int size = pieces_mouvantes.size();
				if (sens) {
					if ((pos + 1) < size)
						setCurrent((MetaPiece) pieces_mouvantes.get(pos + 1));
					else
						setCurrent((MetaPiece) pieces_mouvantes.get(0));
				} else {
					if ((pos - 1) > -1)
						setCurrent((MetaPiece) pieces_mouvantes.get(pos - 1));
					else
						setCurrent((MetaPiece) pieces_mouvantes.get(size - 1));
				}
			} else
				setCurrent(null);
		}
		return current;
	}

	public void removePiece(MetaPiece mp) {
		pieces_mouvantes.remove(mp);
		if (mp == current)
			changeCurrentPiece(true);
	}

	public void fixPiece(MetaPiece mp) {
		int matiere = mp.getMatiere();
		boolean[][] format = mp.getFormat();
		for (int y = 0; y < format.length; y++) {
			boolean[] ligne = format[y];
			for (int x = 0; x < ligne.length; x++) {
				if (ligne[x])
					pieces_mortes[y
						+ mp.getPosition_y()][x
						+ mp.getPosition_x()] =
						matiere;
			}
		}
	}

	/**
	 * @return
	 */
	public int[][] getPieces_mortes() {
		return pieces_mortes;
	}

	public int removeLines() {
		int total = 0;
		int piece_par_ligne = 0;
		for (int x = 0; x < pieces_mortes.length; x++) {
			int[] ligne = pieces_mortes[x];
			piece_par_ligne = 0;
			for (int y = 0; y < ligne.length; y++) {
				piece_par_ligne = piece_par_ligne + (ligne[y] > 0 ? 1 : 0);
			}
			if (largeur == piece_par_ligne) {
				pieces_mortes = TableauTools.removeLine(pieces_mortes, x);
				total++;
			}

		}
		return total;
	}

	public void removeLastLines() {
		pieces_mortes =
			TableauTools.removeLine(pieces_mortes, pieces_mortes.length - 1);
		pieces_mortes =
			TableauTools.removeLine(pieces_mortes, pieces_mortes.length - 1);
	}

	public void upLines() {
		pieces_mortes = TableauTools.upAll(pieces_mortes);
		pieces_mortes = TableauTools.swingLast(pieces_mortes, 7);
	}

	public void fixAll() {
		synchronized (getPieces_mouvantes()) {
			List liste = getPieces_mouvantes();
			for (; liste.size() > 0;) {
				MetaPiece mp = (MetaPiece) liste.remove(0);
				removePiece(mp);
				fixPiece(mp);
			}
		}
	}

	/**
	 * @return
	 */
	public MetaOmbre getOmbre() {
		return ombre;
	}

	/**
	 * @param piece
	 */
	private void setCurrent(MetaPiece piece) {
		current = piece;
		//if (current != null)
		//	ombre.setFormat(current);
	}

	/**
	 * @return
	 */
	public MetaTexte getTexte() {
		return texte;
	}

}