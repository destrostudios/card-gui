package com.destrostudios.cardgui.transformations.speeds;

import com.destrostudios.cardgui.FloatInterpolate;
import com.jme3.math.Quaternion;

public class TimeBasedRotationTransformationSpeed extends TimeBasedTransformationSpeed<Quaternion> {

    public TimeBasedRotationTransformationSpeed(float duration) {
        super(duration);
    }

    @Override
    public float getSpeed(Quaternion currentValue, Quaternion targetValue) {
        float distance = FloatInterpolate.getDistanceLikeNumber(currentValue, targetValue);
        return (distance / (duration - passedTime));
    }

    @Override
    public TimeBasedRotationTransformationSpeed clone() {
        return new TimeBasedRotationTransformationSpeed(duration);
    }
}
