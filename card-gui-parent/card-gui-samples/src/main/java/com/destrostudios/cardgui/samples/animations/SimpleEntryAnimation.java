package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.samples.animations.keyframes.SimpleKeyFrameEntryAnimation;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrame;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrameFactory;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public abstract class SimpleEntryAnimation extends SimpleKeyFrameEntryAnimation {

    public SimpleEntryAnimation(TransformedBoardObject transformedBoardObject, float duration) {
        super(transformedBoardObject, duration);
    }

    @Override
    protected TransformationKeyFrame[] getKeyFrames() {
        return new TransformationKeyFrame[] {
            TransformationKeyFrameFactory.createConstantKeyFrame(getStartPosition(), getStartRotation())
        };
    }

    protected abstract Vector3f getStartPosition();

    protected abstract Quaternion getStartRotation();
}
