/*
 * Created on 16 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.tools;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableauTools {

	/**
	 * 
	 */
	public static boolean[][] rotationLeft(boolean[][] tab) {
		boolean[][] nouveau = new boolean[tab[0].length][tab.length];
		for (int x = 0; x < tab.length; x++) {
			boolean[] ligne = tab[x];
			for (int y = 0; y < ligne.length; y++) {
				nouveau[y][tab.length - x - 1] = ligne[y];
			}
		}
		return nouveau;
	}

	public static int[][] removeLine(int[][] tab, int line) {
		for (int x = line; x > 1; x--)
			for (int y = 0; y < tab[x].length; y++)
				tab[x][y] = tab[x - 1][y];
		return tab;
	}

	public static int[][] upAll(int[][] tab) {
		for (int x = 0; x < tab.length - 1; x++)
			for (int y = 0; y < tab[x].length; y++)
				tab[x][y] = tab[x + 1][y];
		// Il faut mettre des cases vides sur la dernière :		
		for (int y = 0; y < tab[tab.length - 1].length; y++)
			tab[tab.length - 1][y] = 0;
		return tab;
	}

	public static int[][] swingLast(int[][] tab, int max) {
		int total = 0;
		for (int y = 0; y < tab[tab.length - 1].length; y++) {
			tab[tab.length - 1][y] = (int) (Math.random() * (double) max);
			// On veut calculer le nombre de carré dans la ligne :
			total += (tab[tab.length - 1][y]) > 0 ? 1 : 0;
		}
		// Si le nombre de carré est trop important, on recommence :
		if (total > tab[0].length - 5)
			return swingLast(tab, max);
		return tab;
	}

}
