package com.destrostudios.cardgui.transformations.speeds;

import com.jme3.math.Vector3f;

public class TimeBasedVectorTransformationSpeed3f extends TimeBasedTransformationSpeed<Vector3f> {

    public TimeBasedVectorTransformationSpeed3f(float duration) {
        super(duration);
    }

    @Override
    public float getSpeed(Vector3f currentValue, Vector3f targetValue) {
        float distance = currentValue.distance(targetValue);
        return (distance / (duration - passedTime));
    }

    @Override
    public TimeBasedVectorTransformationSpeed3f clone() {
        return new TimeBasedVectorTransformationSpeed3f(duration);
    }
}
