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