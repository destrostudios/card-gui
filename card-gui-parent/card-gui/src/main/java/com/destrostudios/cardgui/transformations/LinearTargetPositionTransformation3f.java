package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed3f;
import com.jme3.math.Vector3f;

public class LinearTargetPositionTransformation3f extends SimpleTargetPositionTransformation3f {

    public LinearTargetPositionTransformation3f() {
        this(new Vector3f());
    }

    public LinearTargetPositionTransformation3f(Vector3f targetPosition) {
        this(targetPosition, new TimeBasedPositionTransformationSpeed3f(1.5f));
    }

    public LinearTargetPositionTransformation3f(Vector3f targetPosition, TransformationSpeed<Vector3f> transformationSpeed) {
        super(new Vector3f(), targetPosition, transformationSpeed);
    }

    @Override
    protected Vector3f getNewValue(Vector3f currentValue, Vector3f targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public LinearTargetPositionTransformation3f clone() {
        return new LinearTargetPositionTransformation3f(targetValue.clone(), transformationSpeed.clone());
    }
}
