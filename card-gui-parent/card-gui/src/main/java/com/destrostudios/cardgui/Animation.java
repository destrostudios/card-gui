package com.destrostudios.cardgui;

public class Animation implements GameLoopListener {

    public void start() {

    }

    public boolean isBlocking() {
        return (!isFinished());
    }

    public void update(float lastTimePerFrame) {

    }

    public boolean isFinished() {
        return true;
    }
}
