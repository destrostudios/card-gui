package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.destrostudios.cardgui.transformations.StatefulTransformation;
import com.destrostudios.cardgui.transformations.relative.ConditionalRelativeVectorTransformation3f;
import com.jme3.math.Vector3f;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class VectorTransformationHandler3f extends TargetedTransformationHandler<Vector3f> {

    public VectorTransformationHandler3f(Supplier<Vector3f> initialValueSupplier) {
        super(initialValueSupplier.get(), () -> new ConstantButTargetedTransformation<>(initialValueSupplier.get()));
    }

    public void addRelativeTransformation(StatefulTransformation<Vector3f> transformation, BooleanSupplier condition) {
        addRelativeTransformation(new ConditionalRelativeVectorTransformation3f(transformation, condition));
    }

    @Override
    public void setValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.set(sourceValue);
    }

    @Override
    public void compositeValue(Vector3f destinationValue, Vector3f sourceValue) {
        destinationValue.addLocal(sourceValue);
    }
}
