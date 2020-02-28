package com.destrostudios.cardgui.transformations;

import com.jme3.math.Vector3f;

public class ConstantPositionTransformation extends ConstantTransformation<Vector3f> {

    public ConstantPositionTransformation() {
        this(new Vector3f());
    }

    public ConstantPositionTransformation(Vector3f value) {
        super(value);
    }

    @Override
    public void setValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.set(sourceValue);
    }
}
