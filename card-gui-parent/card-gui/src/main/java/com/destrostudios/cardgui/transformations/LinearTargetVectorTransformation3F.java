package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed3f;
import com.jme3.math.Vector3f;

public class LinearTargetVectorTransformation3F extends SimpleTargetVectorTransformation3f {

    public LinearTargetVectorTransformation3F() {
        this(new Vector3f());
    }

    public LinearTargetVectorTransformation3F(Vector3f targetPosition) {
        this(targetPosition, new TimeBasedVectorTransformationSpeed3f(1.5f));
    }

    public LinearTargetVectorTransformation3F(Vector3f targetPosition, TransformationSpeed<Vector3f> transformationSpeed) {
        super(new Vector3f(), targetPosition, transformationSpeed);
    }

    @Override
    protected Vector3f getNewValue(Vector3f currentValue, Vector3f targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public LinearTargetVectorTransformation3F clone() {
        return new LinearTargetVectorTransformation3F(targetValue.clone(), transformationSpeed.clone());
    }
}
