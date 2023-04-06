package com.destrostudios.cardgui.transformations.speeds;

import com.destrostudios.cardgui.transformations.TransformationSpeed;
import com.jme3.math.Vector3f;

public class EaseOutDistanceBasedVectorTransformationSpeed3f extends TransformationSpeed<Vector3f> {

    public EaseOutDistanceBasedVectorTransformationSpeed3f(float minimumSpeed) {
        this.minimumSpeed = minimumSpeed;
    }
    private float minimumSpeed;

    @Override
    public Float getSpeed(Vector3f currentValue, Vector3f targetValue) {
        float distance = currentValue.distance(targetValue);
        return Math.max(minimumSpeed, distance);
    }

    @Override
    public EaseOutDistanceBasedVectorTransformationSpeed3f clone() {
        return new EaseOutDistanceBasedVectorTransformationSpeed3f(minimumSpeed);
    }
}
