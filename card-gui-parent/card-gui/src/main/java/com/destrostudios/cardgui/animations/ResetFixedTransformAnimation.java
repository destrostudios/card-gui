package com.destrostudios.cardgui.animations;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.TransformedBoardObject;

import java.util.Collection;
import java.util.Collections;

public class ResetFixedTransformAnimation extends Animation {

    public ResetFixedTransformAnimation(TransformedBoardObject transformedBoardObject) {
        this(Collections.singletonList(transformedBoardObject));
    }

    public ResetFixedTransformAnimation(Collection<? extends TransformedBoardObject> transformedBoardObjects) {
        this.transformedBoardObjects = transformedBoardObjects;
    }
    private Collection<? extends TransformedBoardObject> transformedBoardObjects;
    private boolean allTargetTransformationsReached;

    @Override
    public void start() {
        super.start();
        for (TransformedBoardObject transformedBoardObject : transformedBoardObjects) {
            transformedBoardObject.resetTransformations();
        }
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        allTargetTransformationsReached = transformedBoardObjects.stream().allMatch(TransformedBoardObject::hasReachedTargetTransform);
    }

    @Override
    public boolean isFinished() {
        return allTargetTransformationsReached;
    }
}
