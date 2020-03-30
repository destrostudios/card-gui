package com.destrostudios.cardgui;

public class Animation implements GameLoopListener {

    private boolean isStarted;

    public void update(float lastTimePerFrame) {
        if (!isStarted) {
            start();
            isStarted = true;
        }
    }

    public void start() {

    }

    public boolean isFinished() {
        return true;
    }
}
