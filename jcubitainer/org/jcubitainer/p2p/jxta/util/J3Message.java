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
 ******** December 12, 2004 ********************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.p2p.jxta.util;

public class J3Message {

    private String who = null;

    private String what = null;

    public J3Message(String pwho, String pwhat) {
        who = pwho;
        what = pwhat;
    }

    public String getWhat() {
        return what;
    }
    
    public String getWho() {
        return who;
    }
}