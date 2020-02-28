package com.destrostudios.cardgui.animations;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.TransformedBoardObject;

import java.util.Collection;

public class ResetFixedTransformAnimation extends Animation {

    public ResetFixedTransformAnimation(Collection<? extends TransformedBoardObject> transformedBoardObjects) {
        this.transformedBoardObjects = transformedBoardObjects;
    }
    private Collection<? extends TransformedBoardObject> transformedBoardObjects;
    private boolean allTargetTransformationsReached;

    @Override
    public void start() {
        for (TransformedBoardObject transformedBoardObject : transformedBoardObjects) {
            transformedBoardObject.resetTransformations();
        }
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        allTargetTransformationsReached = transformedBoardObjects.stream().allMatch(transformedBoardObject -> transformedBoardObject.hasReachedTargetTransform());
    }

    @Override
    public boolean isFinished() {
        return allTargetTransformationsReached;
    }
}
