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

package org.jcubitainer.tools.logs;

import java.io.PrintStream;

import javax.swing.JOptionPane;

public class ErrorPrintStream extends PrintStream {

    // Pour avoir une seule fenêtre ouverte :
    boolean open = false;

    /**
     * @param out
     */
    public ErrorPrintStream() {
        super(new ErrorOutputStream());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.PrintStream#print(java.lang.Object)
     */
    public void print(Object obj) {
        super.print(obj);

        if (!open) {
            open = true;
            String message = obj.toString();
            Object[] options = { "Continuer", "Ne plus prévenir", "Quitter"};

            int n = JOptionPane.showOptionDialog(null,
                    "Une erreur est survenue :\n" + message, "Erreur !",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.ERROR_MESSAGE, null, options, options[0]);

            open = false;
            if (n == 1) open = true;
            if (n == 2) System.exit(1);

        }
    }

}