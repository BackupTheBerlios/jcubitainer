/*
 * Created on 14 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.meta;

import org.jcubitainer.tools.TableauTools;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaPiece {

	private int position_x;
	private int position_y;
	private boolean[][] format = null;
	private int matiere = 0;
	private int largeur = 0;
	private int hauteur = 0;

	/**
	 * 
	 */
	public MetaPiece(boolean[][] pformat, int pmatiere) {
		super();
		setFormat(pformat);
		matiere = pmatiere;

	}

	/**
	 * @return
	 */
	public int getPosition_x() {
		return position_x;
	}

	/**
	 * @return
	 */
	public int getPosition_y() {
		return position_y;
	}

	/**
	 * @param i
	 */
	public void setPosition_x(int i) {
		position_x = i;
	}

	/**
	 * @param i
	 */
	public void setPosition_y(int i) {
		position_y = i;
	}

	/**
	 * @return
	 */
	public boolean[][] getFormat() {
		return format;
	}

	/**
	 * @return
	 */
	public int getMatiere() {
		return matiere;
	}

	/**
	 * @return
	 */
	public int getHauteur() {
		return hauteur;
	}

	/**
	 * @return
	 */
	public int getLargeur() {
		return largeur;
	}

	public void rotationLeft() {
		setFormat(TableauTools.rotationLeft(format));
	}

	/**
	 * @param bs
	 */
	public void setFormat(boolean[][] bs) {
		format = bs;
		largeur = format[0].length;
		hauteur = format.length;
	}

}
