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

package org.jcubitainer.display.table;

import java.util.ArrayList;

public class GroupTable {

    Object group = null;

    ArrayList fils = new ArrayList();

    NetworkDisplayTable.SimpleTableModel model = null;

    /**
     * 
     */
    public GroupTable(Object name, NetworkDisplayTable.SimpleTableModel pmodel) {
        super();
        group = name;
        model = pmodel;
    }

    protected ArrayList getFils() {
        return fils;
    }

    public String toString() {
        return group.toString();
    }

    /**
     * @param list
     */
    public void setFils(ArrayList list) {
        fils = list;
        model.fireTableDataChanged();
    }

}