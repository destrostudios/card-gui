package com.destrostudios.cardgui;

import java.util.LinkedList;

public class AnimationPlayer implements GameLoopListener {

    private LinkedList<Animation> playingAnimations = new LinkedList<>();
    private LinkedList<Animation> animationsToUpdate = new LinkedList<>();

    public void play(Animation animation) {
        playingAnimations.add(animation);
    }

    @Override
    public void update(float lastTimePerFrame) {
        animationsToUpdate.clear();
        animationsToUpdate.addAll(playingAnimations);
        for (Animation animation : animationsToUpdate) {
            animation.update(lastTimePerFrame);
            if (animation.isFinished()) {
                playingAnimations.remove(animation);
            }
        }
    }

    public boolean isPlaying() {
        return (playingAnimations.size() > 0);
    }
}
