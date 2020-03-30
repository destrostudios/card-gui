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

    @Override
    public void start() {
        super.start();
        for (TransformedBoardObject transformedBoardObject : transformedBoardObjects) {
            transformedBoardObject.resetTransformations();
        }
    }

    @Override
    public boolean isFinished() {
        return transformedBoardObjects.stream().allMatch(TransformedBoardObject::hasReachedTargetTransform);
    }
}
