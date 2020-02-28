package com.destrostudios.cardgui.transformations.speeds;

import com.destrostudios.cardgui.transformations.TransformationSpeed;
import com.jme3.math.Vector3f;

public class EaseOutDistanceBasedPositionTransformationSpeed extends TransformationSpeed<Vector3f> {

    public EaseOutDistanceBasedPositionTransformationSpeed(float minimumSpeed) {
        this.minimumSpeed = minimumSpeed;
    }
    private float minimumSpeed;

    @Override
    public float getSpeed(Vector3f currentValue, Vector3f targetValue) {
        float distance = currentValue.distance(targetValue);
        return Math.max(minimumSpeed, distance);
    }

    @Override
    public EaseOutDistanceBasedPositionTransformationSpeed clone() {
        return new EaseOutDistanceBasedPositionTransformationSpeed(minimumSpeed);
    }
}
