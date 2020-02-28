package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedRotationTransformationSpeed;
import com.jme3.math.Quaternion;

public class SimpleTargetRotationTransformation extends SimpleTargetedTransformation<Quaternion> {

    public SimpleTargetRotationTransformation() {
        this(new Quaternion());
    }

    public SimpleTargetRotationTransformation(Quaternion targetRotation) {
        this(targetRotation, new TimeBasedRotationTransformationSpeed(1));
    }

    public SimpleTargetRotationTransformation(Quaternion targetPosition, TransformationSpeed<Quaternion> transformationSpeed) {
        super(new Quaternion(), targetPosition, transformationSpeed);
    }

    @Override
    public void setValue(Quaternion destinationValue, Quaternion sourceValue) {
        destinationValue.set(sourceValue);
    }

    @Override
    protected Quaternion getNewValue(Quaternion currentValue, Quaternion targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public SimpleTargetRotationTransformation clone() {
        return new SimpleTargetRotationTransformation(targetValue.clone(), transformationSpeed.clone());
    }
}
