/*
 * Created on 21 févr. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.aspect;

import org.jcubitainer.move.*;
import org.jcubitainer.meta.*;
import org.jcubitainer.display.*;


/**
 * @author MetalM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect CalculerOmbreAspect {

	pointcut refresh() : call(int MovePiece.downPieces(..))
		|| call(void MovePiece.left(..))
		|| call(void MovePiece.up(..))
		|| call(void MovePiece.right(..))
		|| call(void MovePiece.rotation(..))
		|| call(MetaPiece MetaBoard.changeCurrentPiece(..))
		|| call(boolean MovePiece.addPiece(..))
		|| call(void MetaBoard.removeLastLines(..))
		|| call(void MetaBoard.removePiece(..))
		|| call(void MetaBoard.upLines(..))
		;

	after() : refresh() {
		DisplayBoard.getThis().getMetabox().getOmbre().setFormat();
	}

}
