/*
 * Created on 17 mars 04
 *
 */
package org.jcubitainer.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.Hashtable;

import org.jcubitainer.tools.Ressources;

/**
 * @author mounes
 *
 */
public class Sons {

	private static String SOUND1 = "/ressources/sons/mechanical.wav";
	private static String SOUND2 = "/ressources/sons/ton.wav";
	private static Hashtable hs = new Hashtable();

	private static void jouerSon(String s) {
//		System.out.println("Play : " + s);
		AudioClip ac = (AudioClip) hs.get(s);
		if (ac == null) {
			ac = Applet.newAudioClip(Ressources.getURL(s));
			hs.put(s, ac);
		}
		ac.play();
	}

	public static void son1() {
		jouerSon(SOUND1);
	}

	public static void son2() {
		jouerSon(SOUND2);
	}

}
