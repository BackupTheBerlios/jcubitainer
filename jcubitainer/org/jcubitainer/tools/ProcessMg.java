package org.jcubitainer.tools;

public class ProcessMg {

    Process process = null;

    public ProcessMg(Process p) {
        process = p;
    }

    private void start() {
        if (!process.isStart())
            process.start();
        else
            process.reStart();
    }

    public void pause() {
        process.pause();
    }

    public boolean isStop() {
        return process.isPause() || !process.isStart();
    }

    public Process getProcess() {
        return process;
    }

    public void wakeUp() {
        if (isStop()) start();
    }

}