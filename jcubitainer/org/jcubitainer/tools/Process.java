package org.jcubitainer.tools;

public abstract class Process extends Thread {

    boolean pause = false;

    boolean start = false;

    long wait;

    public Process(long pwait) {
        super();
        wait = pwait;
    }

    public void start() {
        start = true;
        super.start();
    }

    public void setWait(long p) {
        wait = p;
    }

    public abstract void action() throws InterruptedException;

    public void run() {
        while (true) {
            try {
                if (!pause) action();
                sleep(wait);

            } catch (InterruptedException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        pause = true;
    }

    public void reStart() {
        pause = false;
    }

    public boolean isPause() {
        return pause;
    }

    protected boolean isStart() {
        return start;
    }

}