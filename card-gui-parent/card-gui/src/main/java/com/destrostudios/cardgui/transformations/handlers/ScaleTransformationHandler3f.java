package com.destrostudios.cardgui.transformations.handlers;

import com.jme3.math.Vector3f;

import java.util.function.Supplier;

public class ScaleTransformationHandler3f extends VectorTransformationHandler3f {

    public ScaleTransformationHandler3f(Supplier<Vector3f> initialValueSupplier) {
        super(initialValueSupplier);
    }

    @Override
    public void compositeValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.multLocal(sourceValue);
    }
}
