package com.destrostudios.cardgui;

import java.util.LinkedList;

public class AnimationQueue implements GameLoopListener {

    private LinkedList<Animation> animationsQueue = new LinkedList<>();
    private LinkedList<Animation> playingAnimations = new LinkedList<>();
    private LinkedList<Animation> animationsToUpdate = new LinkedList<>();
    private Animation blockingAnimation;

    public void addAnimation(Animation animation) {
        animationsQueue.add(animation);
        startNextAnimationIfPossible();
    }

    @Override
    public void update(float lastTimePerFrame) {
        animationsToUpdate.clear();
        animationsToUpdate.addAll(playingAnimations);
        while (animationsToUpdate.size() > 0) {
            Animation animation = animationsToUpdate.pop();
            boolean isFinished = updateAnimation(animation, lastTimePerFrame);
            if (isFinished) {
                playingAnimations.remove(animation);
                startNextAnimationIfPossible();
            }
        }
    }

    private boolean updateAnimation(Animation animation, float lastTimePerFrame) {
        animation.update(lastTimePerFrame);
        if (animation.isFinished()) {
            if (animation == blockingAnimation) {
                blockingAnimation = null;
            }
            return true;
        }
        return false;
    }

    private void startNextAnimationIfPossible() {
        if (animationsQueue.size() > 0) {
            if (playingAnimations.isEmpty() || (blockingAnimation == null)) {
                Animation nextAnimation = animationsQueue.removeFirst();
                playingAnimations.add(nextAnimation);
                if (nextAnimation.isBlocking()) {
                    blockingAnimation = nextAnimation;
                }
            }
        }
    }

    public boolean isBlocking() {
        return (blockingAnimation != null);
    }
}
