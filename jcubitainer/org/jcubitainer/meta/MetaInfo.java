/*
 * Created on 14 févr. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.meta;

/**
 * @author MetalM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaInfo {

	private int score = 0;

	private int ligne = 0;

	private int niveau = 0;

	private int bonus_des = 0;
	private int bonus_sup = 0;
	private int bonus_slow = 0;

	private int niveauSuivant = 0;

	boolean game_over = true;

	boolean pause = false;

	/**
	 * @return
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * @return
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param i
	 */
	public void setLigne(int i) {
		ligne = i;
	}

	/**
	 * @param i
	 */
	public void setNiveau(int i) {
		niveau = i;
	}

	/**
	 * @param i
	 */
	public void setScore(int i) {
		score = i;
	}

	/**
	 * @return
	 */
	public int getBonus_des() {
		return bonus_des;
	}

	/**
	 * @return
	 */
	public int getBonus_slow() {
		return bonus_slow;
	}

	/**
	 * @return
	 */
	public int getBonus_sup() {
		return bonus_sup;
	}

	/**
	 * @return
	 */
	public boolean isGame_over() {
		return game_over;
	}

	/**
	 * @param i
	 */
	public void setBonus_des(int i) {
		bonus_des = i;
	}

	/**
	 * @param i
	 */
	public void setBonus_slow(int i) {
		bonus_slow = i;
	}

	/**
	 * @param i
	 */
	public void setBonus_sup(int i) {
		bonus_sup = i;
	}

	/**
	 * @param b
	 */
	public void setGame_over(boolean b) {
		game_over = b;
	}

	public void addLines(int l) {
		setLigne(ligne + l);
	}

	public void addScore(int l) {
		setScore(score + l);
	}

	public void clean() {
		setLigne(0);
		setScore(0);
		setNiveauSuivant(0);
	}
	/**
	 * @return
	 */
	public boolean isPause() {
		return pause;
	}

	/**
	 * @param b
	 */
	public void setPause(boolean b) {
		pause = b;
	}

	/**
	 * @return
	 */
	public int getNiveauSuivant() {
		return niveauSuivant;
	}

	/**
	 * @param i
	 */
	public void setNiveauSuivant(int i) {
		niveauSuivant = i;
	}

}
