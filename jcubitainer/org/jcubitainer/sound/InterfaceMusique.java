package org.jcubitainer.sound;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.jcubitainer.tools.Process;
import org.jcubitainer.tools.ProcessMg;
/*
 * Created on 15 mars 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author mounes
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class InterfaceMusique {

	private static String[] files =
		{
			"/ressources/musiques/barimyst.mid",
			"/ressources/musiques/india.mid",
			"/ressources/musiques/polonais.mid" };
	private static ArrayList liste = new ArrayList();
	private Musique play = null;
	private static ProcessMg plg = null;
	private static InterfaceMusique _ = null;

	class PlaySound extends Process {

		protected PlaySound() {
			super(5000);
			for (int i = 0; i < files.length; i++) {
				try {
					liste.add(new Musique(files[i]));
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			plg = new ProcessMg(this);
		}

		public void action() throws InterruptedException {
			Collections.shuffle(liste);
			Iterator playlist = liste.iterator();
			while (playlist.hasNext() && !isPause()) {
				play = (Musique) playlist.next();
				System.out.println("on joue " + play);
				play.start();
				play.waitSound();
				try {
					play.sleep(3000);
				} catch (InterruptedException e) {
				}
			}
		}
		/* (non-Javadoc)
		 * @see org.jcubitainer.tools.Process#pause()
		 */
		public void pause() {
			super.pause();
			System.out.println("pause musique");
			play.interrupt();
			plg.getProcess().interrupt();
		}

	}

	public static void START_musique() {
		getThis().getPlg().wakeUp();
	}

	public static void STOP_musique() {
		getThis().getPlg().pause();
	}

	private synchronized static InterfaceMusique getThis() {
		if (_ == null)
			_ = new InterfaceMusique();
		return _;
	}

	/**
	 * @return
	 */
	private synchronized ProcessMg getPlg() {
		if (plg == null)
			new PlaySound();
		return plg;
	}
}
