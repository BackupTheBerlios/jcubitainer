package org.jcubitainer.sound;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
 
import org.jcubitainer.tools.Ressources;

/**
 * @author haruri
 * @link http://www.graco.c.u-tokyo.ac.jp/~haruri/prog/java_midi.xhtml#usingapi
 *
 */
public class Musique extends Thread implements MetaEventListener {

	private boolean play;
	private Object file = null;

	private Sequencer seqer;
	private Musique current = null;

	public void meta(MetaMessage mes) {
		if (mes.getType() == 47) {
			stopSound();
		}
	}

	public Musique(String f)
		throws MidiUnavailableException, InvalidMidiDataException, IOException {
		super("play : " + f);
		file = f;
		try {
			Sequence seq = MidiSystem.getSequence(Ressources.getInputStream(f));
			/* get a default Sequencer */
			seqer = MidiSystem.getSequencer();
			if (seqer == null)
				throw new MidiUnavailableException("No default sequencer.");
			if (!seqer.isOpen())
				seqer.open();

			/* have gotten an instance of Sequence as an argument */
			/* load the sequence to the sequencer */
			seqer.setSequence(seq);
			seqer.addMetaEventListener(this);

			current = this;

		} catch (MidiUnavailableException mue) {
			mue.printStackTrace();
		} catch (InvalidMidiDataException imde) {
			imde.printStackTrace();
		} finally {
			//			this.finalize();
		}
	}

	private void startSound() {
		play = true;
		seqer.start();
	}

	private void stopSound() {
		play = false;
		seqer.stop();
		current.interrupt();
	}

	public void finalize() {
		if (seqer.isOpen())
			seqer.close();
	}

	public void run() {
		if (current == this) {
			startSound();
			try {
				join();
			} catch (InterruptedException e) {
			}
			stopSound();
		}
	}

	/**
	 * 
	 */
	public void waitSound() {
		try {
			current.join();
		} catch (InterruptedException e) {
		}
	}

	public String toString() {
		return "musique[" + file + "]";
	}

}