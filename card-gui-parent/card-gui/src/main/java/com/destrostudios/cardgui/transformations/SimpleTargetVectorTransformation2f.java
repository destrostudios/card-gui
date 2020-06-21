package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.JMonkeyUtil;
import com.jme3.math.Vector2f;

public abstract class SimpleTargetVectorTransformation2f extends SimpleTargetedTransformation<Vector2f> {

    public SimpleTargetVectorTransformation2f(Vector2f value, Vector2f targetValue, TransformationSpeed<Vector2f> transformationSpeed) {
        super(value, targetValue, transformationSpeed);
    }

    @Override
    public void setValue(Vector2f destinationValue, Vector2f sourceValue) {
        destinationValue.set(sourceValue);
    }

    @Override
    protected boolean isValueEquals(Vector2f value1, Vector2f value2) {
        return value1.distanceSquared(value2) < JMonkeyUtil.FLT_EPSILON_SQUARED;
    }
}
