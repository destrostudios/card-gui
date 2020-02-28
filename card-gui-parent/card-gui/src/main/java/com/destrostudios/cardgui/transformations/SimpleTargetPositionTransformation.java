package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed;
import com.jme3.math.Vector3f;

public class SimpleTargetPositionTransformation extends SimpleTargetedTransformation<Vector3f> {

    public SimpleTargetPositionTransformation() {
        this(new Vector3f());
    }

    public SimpleTargetPositionTransformation(Vector3f targetPosition) {
        this(targetPosition, new TimeBasedPositionTransformationSpeed(1.5f));
    }

    public SimpleTargetPositionTransformation(Vector3f targetPosition, TransformationSpeed<Vector3f> transformationSpeed) {
        super(new Vector3f(), targetPosition, transformationSpeed);
    }

    @Override
    public void setValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.set(sourceValue);
    }

    @Override
    protected Vector3f getNewValue(Vector3f currentValue, Vector3f targetValue, float speed, float lastTimePerFrame) {
        return FloatInterpolate.get(currentValue, targetValue, speed, lastTimePerFrame);
    }

    @Override
    public SimpleTargetPositionTransformation clone() {
        return new SimpleTargetPositionTransformation(targetValue.clone(), transformationSpeed.clone());
    }
}
