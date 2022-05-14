package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed2f;
import com.jme3.math.Vector2f;

public class LinearTargetVectorTransformation2f extends SimpleTargetVectorTransformation2f {

    public LinearTargetVectorTransformation2f() {
        this(new Vector2f(), new TimeBasedVectorTransformationSpeed2f(1));
    }

    public LinearTargetVectorTransformation2f(Vector2f targetPosition, TransformationSpeed<Vector2f> transformationSpeed) {
        super(new Vector2f(), targetPosition, transformationSpeed);
    }

    @Override
    protected Vector2f getNewValue(Vector2f currentValue, Vector2f targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public LinearTargetVectorTransformation2f clone() {
        return new LinearTargetVectorTransformation2f(targetValue.clone(), transformationSpeed.clone());
    }
}
