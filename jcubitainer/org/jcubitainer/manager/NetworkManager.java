/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : May 5, 2004                                  *
 * Author : Mounès Ronan metalm@users.berlios.de                       *
 *                                                                     *
 *     http://jcubitainer.berlios.de/                                  *
 *                                                                     *
 * This code is released under the GNU GPL license, version 2 or       *
 * later, for educational and non-commercial purposes only.            *
 * If any part of the code is to be included in a commercial           *
 * software, please contact us first for a clearance at                *
 * metalm@users.berlios.de                                             *
 *                                                                     *
 *   This notice must remain intact in all copies of this code.        *
 *   This code is distributed WITHOUT ANY WARRANTY OF ANY KIND.        *
 *   The GNU GPL license can be found at :                             *
 *           http://www.gnu.org/copyleft/gpl.html                      *
 *                                                                     *
 ***********************************************************************/

/* History & changes **************************************************
 *                                                                     *
 ******** May 5, 2004 **************************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.manager;

import org.jcubitainer.display.DisplayBoard;
import org.jcubitainer.display.infopanel.DisplayInfo;
import org.jcubitainer.meta.MetaInfo;

public class NetworkManager {

    private static boolean network = false;

    public synchronized static void startGame() {
        if (!network || Game.getGameService().isPause()) {
            network = true;
    		DisplayInfo di = DisplayInfo.getThis();
    		MetaInfo mi = di.getMetaInfo();
            mi.setNiveau(2);
            DisplayBoard.getThis().getMetabox().getTexte().setTexte("Go !");
            Game.getGameService().start();
        }
    }

    public synchronized static void endGame() {
        Game.getGameService().endGame();
        network = false;
        DisplayBoard.getThis().getMetabox().getTexte().setTexte("Perdu !");
    }

    public static boolean isNetworkOn() {
        return network;
    }
}