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


package org.jcubitainer.display.table;

public class DisplayPlayer implements Comparable {

	private String name = null;

	private int hit = 0;

	public DisplayPlayer(String pname) {
		super();
		name = pname;
	}

	public int compareTo(Object o) {
		int temp = ((DisplayPlayer) o).getHit();
		if (temp == getHit())
			return 0;
		return temp > getHit() ? 1 : -1;
	}

	public String toString() {
		return getName() + " ( " + hit + " )";
	}

	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getHit() {
		return hit;
	}

	/**
	 * @param i
	 */
	public void setHit(int i) {
		hit = i;
	}

}
