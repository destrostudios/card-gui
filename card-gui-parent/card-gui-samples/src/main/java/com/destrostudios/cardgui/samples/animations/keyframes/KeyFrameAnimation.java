package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.animations.StagedAnimation;

public class KeyFrameAnimation extends StagedAnimation {

    public KeyFrameAnimation(TransformedBoardObject transformedBoardObject, TransformationKeyFrame[] keyFrames) {
        this.stages = new Animation[keyFrames.length];
        for (int i = 0; i < keyFrames.length; i++) {
            TransformationKeyFrame keyFrame = keyFrames[i];
            stages[i] = new TargetedTransformationAnimation(transformedBoardObject, keyFrame.getPositionTransformation(), keyFrame.getRotationTransformation());
        }
    }
}
