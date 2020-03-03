package com.destrostudios.cardgui.transformations.speeds;

import com.jme3.math.Vector2f;

public class TimeBasedPositionTransformationSpeed2f extends TimeBasedTransformationSpeed<Vector2f> {

    public TimeBasedPositionTransformationSpeed2f(float duration) {
        super(duration);
    }

    @Override
    public float getSpeed(Vector2f currentValue, Vector2f targetValue) {
        float distance = currentValue.distance(targetValue);
        return (distance / (duration - passedTime));
    }

    @Override
    public TimeBasedPositionTransformationSpeed2f clone() {
        return new TimeBasedPositionTransformationSpeed2f(duration);
    }
}
