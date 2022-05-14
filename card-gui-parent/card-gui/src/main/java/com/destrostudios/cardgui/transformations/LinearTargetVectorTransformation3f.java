package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed3f;
import com.jme3.math.Vector3f;

public class LinearTargetVectorTransformation3f extends SimpleTargetVectorTransformation3f {

    public LinearTargetVectorTransformation3f() {
        this(new Vector3f());
    }

    public LinearTargetVectorTransformation3f(Vector3f targetPosition) {
        this(targetPosition, new TimeBasedVectorTransformationSpeed3f(1.5f));
    }

    public LinearTargetVectorTransformation3f(Vector3f targetPosition, TransformationSpeed<Vector3f> transformationSpeed) {
        super(new Vector3f(), targetPosition, transformationSpeed);
    }

    @Override
    protected Vector3f getNewValue(Vector3f currentValue, Vector3f targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public LinearTargetVectorTransformation3f clone() {
        return new LinearTargetVectorTransformation3f(targetValue.clone(), transformationSpeed.clone());
    }
}
