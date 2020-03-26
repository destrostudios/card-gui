package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.transformations.SimpleTargetPositionTransformation3f;
import com.destrostudios.cardgui.transformations.SimpleTargetRotationTransformation;
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
    public TargetedTransformation<Vector3f> createFinalPositionTransformation(Vector3f defaultTargetPosition) {
        return new SimpleTargetPositionTransformation3f(defaultTargetPosition, new TimeBasedPositionTransformationSpeed3f(finalTransformationDuration));
    }

    @Override
    public TargetedTransformation<Quaternion> createFinalRotationTransformation(Quaternion defaultTargetRotation) {
        return new SimpleTargetRotationTransformation(defaultTargetRotation, new TimeBasedRotationTransformationSpeed(finalTransformationDuration));
    }
}
