/*
 * Created on 17 mars 04
 *  
 */
package org.jcubitainer.sound;

import java.io.ByteArrayInputStream;
import java.util.Hashtable;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;

import org.jcubitainer.display.theme.ThemeManager;

/**
 * 
 * http://forum.java.sun.com/thread.jsp?forum=31&thread=420688
 * 
 * @author mounes 
 * 
 */
public class Sons {

    private static Hashtable hs = new Hashtable();

    private static void jouerSon(byte[] s) {
        try {
            Clip m_clip;
            AudioInputStream m_stream;

            AudioFileFormat audioFileFormat = AudioSystem
                    .getAudioFileFormat(new ByteArrayInputStream(s));

            m_stream = AudioSystem
                    .getAudioInputStream(new ByteArrayInputStream(s));
            AudioFormat format = m_stream.getFormat();
            Info info = new Info(Clip.class, format, ((int) m_stream
                    .getFrameLength() * format.getFrameSize()));
            m_clip = (Clip) AudioSystem.getLine(info);
            m_clip.open(m_stream);
            m_clip.setFramePosition(0);
            m_clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void son1() {
        jouerSon(ThemeManager.getCurrent().getSon("son1"));
    }

    public static void son2() {
        jouerSon(ThemeManager.getCurrent().getSon("son2"));
    }

}