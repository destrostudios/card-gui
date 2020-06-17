package com.destrostudios.cardgui.transformations;

import com.jme3.math.Quaternion;

public abstract class SimpleTargetRotationTransformation extends SimpleTargetedTransformation<Quaternion> {

    public SimpleTargetRotationTransformation(Quaternion value, Quaternion targetValue, TransformationSpeed<Quaternion> transformationSpeed) {
        super(value, targetValue, transformationSpeed);
    }

    @Override
    public void setValue(Quaternion destinationValue, Quaternion sourceValue) {
        destinationValue.set(sourceValue);
    }
}
