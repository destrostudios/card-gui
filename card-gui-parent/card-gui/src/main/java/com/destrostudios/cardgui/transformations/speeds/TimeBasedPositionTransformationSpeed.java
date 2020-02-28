package com.destrostudios.cardgui.transformations.speeds;

import com.jme3.math.Vector3f;

public class TimeBasedPositionTransformationSpeed extends TimeBasedTransformationSpeed<Vector3f> {

    public TimeBasedPositionTransformationSpeed(float duration) {
        super(duration);
    }

    @Override
    public float getSpeed(Vector3f currentValue, Vector3f targetValue) {
        float distance = currentValue.distance(targetValue);
        return (distance / (duration - passedTime));
    }

    @Override
    public TimeBasedPositionTransformationSpeed clone() {
        return new TimeBasedPositionTransformationSpeed(duration);
    }
}
