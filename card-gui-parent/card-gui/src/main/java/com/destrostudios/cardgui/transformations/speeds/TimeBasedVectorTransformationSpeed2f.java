package com.destrostudios.cardgui.transformations.speeds;

import com.jme3.math.Vector2f;

public class TimeBasedVectorTransformationSpeed2f extends TimeBasedTransformationSpeed<Vector2f> {

    public TimeBasedVectorTransformationSpeed2f(float duration) {
        super(duration);
    }

    @Override
    public Float getSpeed(Vector2f currentValue, Vector2f targetValue) {
        if (passedTime < duration) {
            float distance = currentValue.distance(targetValue);
            return (distance / (duration - passedTime));
        }
        return null;
    }

    @Override
    public TimeBasedVectorTransformationSpeed2f clone() {
        return new TimeBasedVectorTransformationSpeed2f(duration);
    }
}
