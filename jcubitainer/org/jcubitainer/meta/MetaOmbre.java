/*
 * Créé le 20 févr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.jcubitainer.meta;

/**
 * @author mounes
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class MetaOmbre {

	private int[] format = null;
	private MetaBoard mb = null;

	/**
	 * 
	 */
	public MetaOmbre(MetaBoard pmb) {
		super();
		mb = pmb;
		format = new int[mb.getLargeur()];
	}

	public int[] getFormat() {
		return format;
	}

	public int[] setFormat() {
		// Supprimer l'ombre :
		for (int i = 0; i < format.length; i++)
			format[i] = 0;
		MetaPiece mp = mb.getCurrentPiece();
		if (mp == null)
			return null;
		// On recherche les positions de l'ombre :
		int[][] tab = mb.getPieces_mortes();

		int deb = mp.getPosition_x();
		int fin = mp.getPosition_x() + mp.getLargeur();
		if (fin > tab[0].length)
			fin = tab[0].length;
		//tab[x].length

		for (int x = mp.getPosition_y() +1; x < tab.length; x++) {
			for (int y = deb; y < fin; y++)
				if (tab[x][y] != 0 && format[y] == 0)
					format[y] = x - 1;
				else if (x == (tab.length - 1) && format[y] == 0)
					format[y] = tab.length - 1;
		}
		return format;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(format.length);
		for (int i = 0; i < format.length; i++)
			sb.append(format[i]);
		return sb.toString();
	}
}
