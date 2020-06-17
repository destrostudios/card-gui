package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.transformations.LinearTargetPositionTransformation3f;
import com.destrostudios.cardgui.transformations.LinearTargetRotationTransformation;
import com.destrostudios.cardgui.transformations.TargetedTransformation;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed3f;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedRotationTransformationSpeed;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public abstract class SimpleKeyFrameEntryAnimation extends KeyFrameEntryAnimation {

    public SimpleKeyFrameEntryAnimation(TransformedBoardObject transformedBoardObject, float finalTransformationDuration) {
        super(transformedBoardObject);
        this.finalTransformationDuration = finalTransformationDuration;
    }
    private float finalTransformationDuration;

    @Override
    protected TargetedTransformation<Vector3f> createFinalPositionTransformation(Vector3f defaultTargetPosition) {
        return new LinearTargetPositionTransformation3f(defaultTargetPosition, new TimeBasedPositionTransformationSpeed3f(finalTransformationDuration));
    }

    @Override
    protected TargetedTransformation<Quaternion> createFinalRotationTransformation(Quaternion defaultTargetRotation) {
        return new LinearTargetRotationTransformation(defaultTargetRotation, new TimeBasedRotationTransformationSpeed(finalTransformationDuration));
    }
}
