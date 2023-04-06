package com.destrostudios.cardgui.transformations.speeds;

import com.destrostudios.cardgui.FloatInterpolate;
import com.jme3.math.Quaternion;

public class TimeBasedRotationTransformationSpeed extends TimeBasedTransformationSpeed<Quaternion> {

    public TimeBasedRotationTransformationSpeed(float duration) {
        super(duration);
    }

    @Override
    public Float getSpeed(Quaternion currentValue, Quaternion targetValue) {
        if (passedTime < duration) {
            float distance = FloatInterpolate.getDistanceLikeNumber(currentValue, targetValue);
            return (distance / (duration - passedTime));
        }
        return null;
    }

    @Override
    public TimeBasedRotationTransformationSpeed clone() {
        return new TimeBasedRotationTransformationSpeed(duration);
    }
}
