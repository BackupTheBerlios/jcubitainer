/*
 * Created on 16 janv. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.display;

import java.awt.Graphics2D;
import java.awt.Image;

import org.jcubitainer.manager.MatiereFactory;
import org.jcubitainer.meta.MetaPiece;

/**
 * @author rom
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DisplayPiece extends MetaPiece {

	public final static int LARGEUR_PIECE = 12;

	public final static int HAUTEUR_PIECE = 12;

	/**
	 * @param pformat
	 * @param pmatiere
	 */
	public DisplayPiece(boolean[][] pformat, int pmatiere) {
		super(pformat, pmatiere);
	}

	public void drawPiece(boolean active, Graphics2D g2) {
		Image peinture = null;
		if (!active)
			peinture = MatiereFactory.getActiveColor(getMatiere());
		else
			peinture = MatiereFactory.getColorByMatiere(getMatiere(), false);

		int pos_y =
			DisplayBoard.deb_y + getPosition_y() * DisplayPiece.HAUTEUR_PIECE;

		boolean[][] format = getFormat();
		for (int x = 0; x < format.length; x++) {
			int pos_x =
				DisplayBoard.deb_x
					+ getPosition_x() * DisplayPiece.LARGEUR_PIECE;
			boolean[] ligne = format[x];
			for (int y = 0; y < ligne.length; y++) {
				if (ligne[y])
					g2.drawImage(peinture, pos_x, pos_y, null);

				pos_x = pos_x + DisplayPiece.LARGEUR_PIECE;
			}
			pos_y = pos_y + DisplayPiece.HAUTEUR_PIECE;
		}
	}
}
