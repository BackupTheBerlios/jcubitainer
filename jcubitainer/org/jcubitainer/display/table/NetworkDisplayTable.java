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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class NetworkDisplayTable extends JPanel {

    private SimpleTableModel model = null;

    private String title = null;

    private GroupTable group_selectionne = null;

    protected static NetworkDisplayTable parties = new NetworkDisplayTable(
            "Parties", 150, 200, true);

    protected static NetworkDisplayTable joueurs = new NetworkDisplayTable(
            "Joueurs", 150, 200, false);

    public NetworkDisplayTable(String ptitle, int largeur, int hauteur,
            boolean actif) {

        super(new GridLayout(1, 0));

        title = ptitle;
        setBackground(Color.black);
        setForeground(Color.black);

        model = new SimpleTableModel();

        JTable table = new JTable(model);
        table
                .setPreferredScrollableViewportSize(new Dimension(largeur,
                        hauteur));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.black);
        table.setForeground(Color.red);
        table.setSelectionForeground(Color.yellow);
        table.setSelectionBackground(Color.darkGray);
        table.setCellSelectionEnabled(actif);
        table.setGridColor(Color.black);
        table.setShowGrid(true);
        table.getTableHeader().setFont(new Font("Courier", Font.BOLD, 13));
        table.getTableHeader().setBackground(Color.black);
        table.getTableHeader().setForeground(Color.yellow);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFont(new Font("Helvetica", Font.BOLD, 10));

        if (actif) {
            table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            ListSelectionModel rowSM = table.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {

                    if (e.getValueIsAdjusting()) return;

                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    if (lsm.isSelectionEmpty()) {

                    } else {
                        int selectedRow = lsm.getMinSelectionIndex();

                        setGroup_selectionne((GroupTable) model.getValueAt(
                                selectedRow, 0));

                        joueurs.getModel().setDatas(
                                getGroup_selectionne().getFils());

                    }
                }
            });
        } else {
            table.setRequestFocusEnabled(false);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.black);
        scrollPane.setBackground(Color.black);
        add(scrollPane);
        setOpaque(true);

    }

    protected class SimpleTableModel extends AbstractTableModel {

        private String[] columnNames = { title};

        private ArrayList data = new ArrayList();

        public void setDatas(ArrayList list) {
            data = list;
            fireTableDataChanged();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row);
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void addGroupPeer(Object value) {
            data.add(value);
            fireTableRowsInserted(data.size() - 1, 0);
        }

        public void removeGroupPeer(Object value) {
            data.remove(value);
            fireTableRowsDeleted(data.size() - 1, 0);
        }

    }

    public void addGroupTable(GroupTable o) {
        model.addGroupPeer(o);
    }

    public void removeData(Object o) {
        model.removeGroupPeer(o);
    }

    /**
     * @return
     */
    public GroupTable getGroup_selectionne() {
        return group_selectionne;
    }

    /**
     * @param object
     */
    public void setGroup_selectionne(GroupTable object) {
        group_selectionne = object;
    }

    /**
     * @return
     */
    public SimpleTableModel getModel() {
        return model;
    }

    /**
     * @return
     */
    public static NetworkDisplayTable getNetworkDisplayForParties() {
        return parties;
    }

    /**
     * @return
     */
    public static NetworkDisplayTable getNetworkDisplayForJoueurs() {
        return joueurs;
    }

}