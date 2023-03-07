package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.jme3.math.Quaternion;

public class LinearTargetRotationTransformation extends SimpleTargetRotationTransformation {

    public LinearTargetRotationTransformation(Quaternion targetRotation, TransformationSpeed<Quaternion> transformationSpeed) {
        super(new Quaternion(), targetRotation, transformationSpeed);
    }

    @Override
    protected Quaternion getNewValue(Quaternion currentValue, Quaternion targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public LinearTargetRotationTransformation clone() {
        return new LinearTargetRotationTransformation(targetValue.clone(), transformationSpeed.clone());
    }
}
