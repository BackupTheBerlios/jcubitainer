/*
 * Created on 22 févr. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jcubitainer.meta;

/**
 * @author MetalM
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaTexte {

	StringBuffer texte = new StringBuffer(20);
	boolean display = false;
	float alpha = .3f;

	public void setTexte(String s) {
		texte.delete(0, texte.length());
		texte.append(s);
	}

	public String getTexte() {
		return texte.toString();
	}
	
	
	/**
	 * @return
	 */
	public boolean isDisplay() {
		return display;
	}

	/**
	 * @param b
	 */
	public void setDisplay(boolean b) {
		display = b;
	}

	/**
	 * @return
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * @param f
	 */
	public void setAlpha(float f) {
		alpha = f;
	}

}
