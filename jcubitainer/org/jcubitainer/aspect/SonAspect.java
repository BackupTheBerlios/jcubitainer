/*
 * Created on 17 mars 04
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.aspect;

import org.jcubitainer.display.infopanel.*;
import org.jcubitainer.meta.*;
import org.jcubitainer.manager.*;
import org.jcubitainer.sound.InterfaceMusique;
import org.jcubitainer.sound.Sons;
import org.jcubitainer.display.theme.*;

/**
 * @author mounes
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public aspect SonAspect {

	// Son quand une pièce se fixe
	pointcut sonfixPiece() : call(void MetaBoard.fixPiece(..));

	after() : sonfixPiece() {
		Sons.son1();
	}

	// Son quand une ligne disparaît
	pointcut sonLigne() : call(void Bonus.newBonusByLine(..));

	after() : sonLigne() {
		Sons.son2();
	}

	pointcut sonrefreshGame_over() : call(void MetaInfo.setGame_over(..));

	after() : sonrefreshGame_over() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		if (mi.isGame_over())
			InterfaceMusique.STOP_musique();
		else
			InterfaceMusique.START_musique();
	}
	
	pointcut refreshTheme() : call(void ThemeManager.swithTheme(..));

	after() : refreshTheme() {
        if (InterfaceMusique.STOP_musique())
            InterfaceMusique.START_musique();
	}
	
}
