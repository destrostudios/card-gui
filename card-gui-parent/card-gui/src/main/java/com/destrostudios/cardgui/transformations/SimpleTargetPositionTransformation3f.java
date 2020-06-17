package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.JMonkeyUtil;
import com.jme3.math.Vector3f;

public abstract class SimpleTargetPositionTransformation3f extends SimpleTargetedTransformation<Vector3f> {

    public SimpleTargetPositionTransformation3f(Vector3f value, Vector3f targetValue, TransformationSpeed<Vector3f> transformationSpeed) {
        super(value, targetValue, transformationSpeed);
    }

    @Override
    public void setValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.set(sourceValue);
    }

    @Override
    protected boolean isValueEquals(Vector3f value1, Vector3f value2) {
        return value1.distanceSquared(value2) < JMonkeyUtil.FLT_EPSILON_SQUARED;
    }
}
