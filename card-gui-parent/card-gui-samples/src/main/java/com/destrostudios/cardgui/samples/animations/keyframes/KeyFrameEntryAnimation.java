package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.animations.ResetFixedTransformAnimation;
import com.destrostudios.cardgui.animations.StagedAnimation;
import com.destrostudios.cardgui.samples.animations.TargetedTransformationAnimation;
import com.destrostudios.cardgui.transformations.TargetedTransformation;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public abstract class KeyFrameEntryAnimation extends StagedAnimation {

    public KeyFrameEntryAnimation(TransformedBoardObject transformedBoardObject) {
        this.transformedBoardObject = transformedBoardObject;
    }
    protected TransformedBoardObject transformedBoardObject;

    @Override
    public void start() {
        super.start();
        this.stages = new Animation[3];
        stages[0] = new KeyFrameAnimation(transformedBoardObject, getKeyFrames());
        TargetedTransformation<Vector3f> finalPositionTransformation = createFinalPositionTransformation(transformedBoardObject.position().getDefaultTargetValue());
        TargetedTransformation<Quaternion> finalRotationTransformation = createFinalRotationTransformation(transformedBoardObject.rotation().getDefaultTargetValue());
        stages[1] = new TargetedTransformationAnimation(transformedBoardObject, finalPositionTransformation, finalRotationTransformation);
        stages[2] = new ResetFixedTransformAnimation(transformedBoardObject);
    }

    protected abstract TransformationKeyFrame[] getKeyFrames();

    protected abstract TargetedTransformation<Vector3f> createFinalPositionTransformation(Vector3f defaultTargetPosition);

    protected abstract TargetedTransformation<Quaternion> createFinalRotationTransformation(Quaternion defaultTargetRotation);
}
