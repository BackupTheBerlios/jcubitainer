/*
 * Créé le 11 févr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.jcubitainer.aspect;

import org.jcubitainer.move.*;
import org.jcubitainer.meta.*;
import org.jcubitainer.display.*;
import org.jcubitainer.manager.*;
import org.jcubitainer.display.theme.*;

/**
 * @author mounes
 *
 */
public aspect RefreshDisplayAspect {

	pointcut refresh() : call(int MovePiece.downPieces(..))
		|| call(void MovePiece.up(..))
		|| call(void MovePiece.left(..))
		|| call(void MovePiece.right(..))
		|| call(boolean MovePiece.forceAddPiece(..))
		|| call(void MovePiece.rotation(..))
		|| call(MetaPiece MetaBoard.changeCurrentPiece(..))
		|| call(boolean MovePiece.addPiece(..))
		|| call(void MetaBoard.removeLastLines(..))
		|| call(void MetaBoard.removePiece(..))
		|| call(void MetaBoard.upLines(..))
		|| call(void Game.pause(..))
		|| call(void Game.start(..))
		|| call(void MetaTexte.setDisplay(..))
		|| call(void ThemeManager.swithTheme(..))
		;

	after() : refresh() {
		DisplayBoard.getThis().repaint();
		//System.out.println("RefreshDisplayAspect from : " + thisJoinPoint);
	}

}
