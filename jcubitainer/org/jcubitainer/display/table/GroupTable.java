/*
 * Created on 11 mars 04
 *
 */
package org.jcubitainer.display.table;

import java.util.ArrayList;

/**
 * @author mounes
 *
 */
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
