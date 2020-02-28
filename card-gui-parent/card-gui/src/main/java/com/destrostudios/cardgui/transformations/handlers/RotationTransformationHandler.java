package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.transformations.StatefulTransformation;
import com.destrostudios.cardgui.transformations.relative.ConditionalRelativeRotationTransformation;
import com.jme3.math.Quaternion;

import java.util.function.BooleanSupplier;

public class RotationTransformationHandler extends TargetedTransformationHandler<Quaternion> {

    public RotationTransformationHandler() {
        super(new Quaternion());
    }

    public void addRelativeTransformation(StatefulTransformation<Quaternion> transformation, BooleanSupplier condition) {
        addRelativeTransformation(new ConditionalRelativeRotationTransformation(transformation, condition));
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
