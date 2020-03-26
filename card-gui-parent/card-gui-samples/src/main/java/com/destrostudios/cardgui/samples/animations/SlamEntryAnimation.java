package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.samples.animations.keyframes.SimpleKeyFrameEntryAnimation;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrame;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrameFactory;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class SlamEntryAnimation extends SimpleKeyFrameEntryAnimation {

    public SlamEntryAnimation(TransformedBoardObject transformedBoardObject, float duration) {
        super(transformedBoardObject, (duration / 2));
        this.duration = duration;
    }
    private float duration;

    @Override
    protected TransformationKeyFrame[] getKeyFrames() {
        TransformationKeyFrame[] keyFrames = new TransformationKeyFrame[2];

        Vector3f defaultTargetPosition = transformedBoardObject.position().getDefaultTargetValue();
        Quaternion defaultTargetRotation = transformedBoardObject.rotation().getDefaultTargetValue();
        keyFrames[0] = TransformationKeyFrameFactory.createConstantKeyFrame(defaultTargetPosition, defaultTargetRotation);

        Vector3f airPosition = defaultTargetPosition.add(0, 1, 0);
        keyFrames[1] = TransformationKeyFrameFactory.createSimpleKeyFrame(airPosition, defaultTargetRotation, (duration / 2));

        return keyFrames;
    }
}
