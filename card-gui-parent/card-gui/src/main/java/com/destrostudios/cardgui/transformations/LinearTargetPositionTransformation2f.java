package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed2f;
import com.jme3.math.Vector2f;

public class LinearTargetPositionTransformation2f extends SimpleTargetPositionTransformation2f {

    public LinearTargetPositionTransformation2f() {
        this(new Vector2f());
    }

    public LinearTargetPositionTransformation2f(Vector2f targetPosition) {
        this(targetPosition, new TimeBasedPositionTransformationSpeed2f(1));
    }

    public LinearTargetPositionTransformation2f(Vector2f targetPosition, TransformationSpeed<Vector2f> transformationSpeed) {
        super(new Vector2f(), targetPosition, transformationSpeed);
    }

    @Override
    protected Vector2f getNewValue(Vector2f currentValue, Vector2f targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public LinearTargetPositionTransformation2f clone() {
        return new LinearTargetPositionTransformation2f(targetValue.clone(), transformationSpeed.clone());
    }
}
