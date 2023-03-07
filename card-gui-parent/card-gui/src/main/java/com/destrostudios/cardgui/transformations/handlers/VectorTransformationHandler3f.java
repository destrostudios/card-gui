package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.jme3.math.Vector3f;

import java.util.function.Supplier;

public abstract class VectorTransformationHandler3f extends TargetedTransformationHandler<Vector3f> {

    public VectorTransformationHandler3f(Supplier<Vector3f> initialValueSupplier) {
        super(initialValueSupplier.get(), () -> new ConstantButTargetedTransformation<>(initialValueSupplier.get()));
    }

    @Override
    public void setValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.set(sourceValue);
    }
}
