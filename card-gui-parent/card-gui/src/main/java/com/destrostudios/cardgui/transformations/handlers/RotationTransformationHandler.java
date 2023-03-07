package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.jme3.math.Quaternion;

import java.util.function.Supplier;

public class RotationTransformationHandler extends TargetedTransformationHandler<Quaternion> {

    public RotationTransformationHandler(Supplier<Quaternion> initialValueSupplier) {
        super(initialValueSupplier.get(), () -> new ConstantButTargetedTransformation<>(initialValueSupplier.get()));
    }

    @Override
    public void setValue(Quaternion destinationValue, Quaternion sourceValue) {
        destinationValue.set(sourceValue);
    }

    @Override
    public void compositeValue(Quaternion destinationValue, Quaternion sourceValue) {
        destinationValue.multLocal(sourceValue);
    }
}
