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

package org.jcubitainer.manager.process;

import java.util.List;

import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.Game;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.tools.Process;

public class ChuteProcess extends Process {

    MetaBoard metabox = null;

    MoveBoard dbox = null;

    boolean slow = false;

    int boucle = 0;

    /**
     *  
     */
    public ChuteProcess(MoveBoard pb) {
        super(1800);
        metabox = pb.getMetabox();
        dbox = pb;
        setPriority(MAX_PRIORITY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jcubitainer.manager.tools.Process#action()
     */
    public void action() throws InterruptedException {

        if (slow) {
            boucle--;
            if (boucle < 0) {
                slow = false;
                Game.getGameService().getBonus().stopSlow();
                boucle = 0;
            }
        }
        dbox.getMetaInfo().addScore(1);

        synchronized (metabox.getPieces_mouvantes()) {
            List liste = metabox.getPieces_mouvantes();
            ((MoveBoard) dbox).getMovepiece().downPieces(liste, boucle);
        }
        //dbox.repaint();
    }

    public void setSlow() {
        slow = true;
        boucle = boucle + 60;
    }

}