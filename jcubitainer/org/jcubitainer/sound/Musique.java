package org.jcubitainer.sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.jcubitainer.tools.Process;

/**
 *  
 */
public class Musique extends Process implements MetaEventListener {

    private Object file = null;

    private Sequencer seqer;

    public void meta(MetaMessage mes) {
        if (mes.getType() == 47) {
            pause(true);
        }
    }

    public Musique(InputStream is, String nom) throws MidiUnavailableException,
            InvalidMidiDataException, IOException {
        super(1000);
        file = nom;
        try {
            Sequence seq = MidiSystem.getSequence(is);
            /* get a default Sequencer */
            seqer = MidiSystem.getSequencer();
            if (seqer == null)
                    throw new MidiUnavailableException("No default sequencer.");
            if (!seqer.isOpen()) seqer.open();

            /* have gotten an instance of Sequence as an argument */
            /* load the sequence to the sequencer */
            seqer.setSequence(seq);
            seqer.addMetaEventListener(this);

        } catch (MidiUnavailableException mue) {
            mue.printStackTrace();
        } catch (InvalidMidiDataException imde) {
            imde.printStackTrace();
        } finally {
            //			this.finalize();
        }
    }

    private void startSound() {
        seqer.setMicrosecondPosition(0);
        seqer.start();
    }

    private void pause(boolean stop) {
        super.pause();
        seqer.stop();
        interrupt();
    }

    public void pause() {
        pause(false);
    }

    public void finalize() {
        if (seqer.isOpen()) seqer.close();
    }

    public void action() {
        startSound();
        try {
            join();
            pause(false);
        } catch (Exception e) {
        }
        synchronized (this) {
            notifyAll();
            System.out.println("#");
        }
    }

    public String toString() {
        return file.toString();
    }

}