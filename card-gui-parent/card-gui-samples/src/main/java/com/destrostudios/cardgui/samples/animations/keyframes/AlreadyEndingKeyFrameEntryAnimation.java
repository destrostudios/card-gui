package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.destrostudios.cardgui.transformations.TargetedTransformation;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public abstract class AlreadyEndingKeyFrameEntryAnimation extends KeyFrameEntryAnimation {

    public AlreadyEndingKeyFrameEntryAnimation(TransformedBoardObject transformedBoardObject) {
        super(transformedBoardObject);
    }

    @Override
    protected TargetedTransformation<Vector3f> createFinalPositionTransformation(Vector3f defaultTargetPosition) {
        return new ConstantButTargetedTransformation<>(defaultTargetPosition);
    }

    @Override
    protected TargetedTransformation<Quaternion> createFinalRotationTransformation(Quaternion defaultTargetRotation) {
        return new ConstantButTargetedTransformation<>(defaultTargetRotation);
    }
}
