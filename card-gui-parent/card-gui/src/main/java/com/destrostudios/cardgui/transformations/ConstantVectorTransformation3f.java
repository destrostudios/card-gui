package com.destrostudios.cardgui.transformations;

import com.jme3.math.Vector3f;

public class ConstantVectorTransformation3f extends ConstantTransformation<Vector3f> {

    public ConstantVectorTransformation3f() {
        this(new Vector3f());
    }

    public ConstantVectorTransformation3f(Vector3f value) {
        super(value);
    }

    @Override
    public void setValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.set(sourceValue);
    }
}
