package org.jcubitainer.sound;

import java.util.List;

import org.jcubitainer.display.theme.Theme;
import org.jcubitainer.manager.Configuration;
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

    private ProcessMg play = null;

    private static ProcessMg plg = null;

    private static InterfaceMusique this_ = null;

    private static boolean playMusic = true;

    static {
        playMusic = "true".equals(Configuration.getProperties("musique"));
        this_ = new InterfaceMusique();
    }

    class PlaySound extends Process {

        protected PlaySound() {
            super(1000);
            plg = new ProcessMg(this);
        }

        public void action() throws InterruptedException {
            try {
                List playlist = Theme.getCurrent().getMusiques();
                int pos = 0;
                if (play != null) {
                    pos = playlist.indexOf(play) + 1;
                    if (pos > 0 && pos < playlist.size()) {
                    } else
                        pos = 0;
                }
                play = (ProcessMg) playlist.get(pos);
                System.out.println("on joue \"" + play.getProcess() + "\"");
                play.wakeUp();
                synchronized (((Musique) play.getProcess())) {
                    System.out.println("action !");
                    while (true)
                        if (!((Musique) play.getProcess()).isPause())
                            ((Musique) play.getProcess()).wait(500);
                        else
                            break;
                    System.out.println("fin action !");
                }
            } catch (InterruptedException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        } /*
           * (non-Javadoc)
           * 
           * @see org.jcubitainer.tools.Process#pause()
           */

        public void pause() {
            super.pause();
            Musique m = (Musique) play.getProcess();
            m.pause();
            //            plg.getProcess().interrupted();
        }

    }

    public static void START_musique() {
        if (InterfaceMusique.playMusic) this_.getPlg().wakeUp();
    }

    public static boolean STOP_musique() {
        if (!this_.getPlg().isStop()) {
            this_.getPlg().pause();
            return true;
        } else
            return false;
    }

    /**
     * @return
     */
    private synchronized ProcessMg getPlg() {
        if (plg == null) new PlaySound();
        return plg;
    }

    /**
     * @param playMusic
     *            The playMusic to set.
     */
    public static boolean switchPlayMusic() {
        synchronized (this_) {
            playMusic = !playMusic;
            // on veut éteindre et la musique tourne :
            if (!playMusic) STOP_musique();
            // on veut la musique et c'était arrêté :
            if (playMusic) START_musique();
        }
        Configuration.setPropertie("musique", String.valueOf(playMusic));
        return playMusic;
    }
}