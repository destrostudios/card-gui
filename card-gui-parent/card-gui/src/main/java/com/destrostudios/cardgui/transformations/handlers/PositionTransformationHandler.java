package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.transformations.StatefulTransformation;
import com.destrostudios.cardgui.transformations.relative.ConditionalRelativePositionTransformation;
import com.jme3.math.Vector3f;

import java.util.function.BooleanSupplier;

public class PositionTransformationHandler extends TargetedTransformationHandler<Vector3f> {

    public PositionTransformationHandler() {
        super(new Vector3f());
    }

    public void addRelativeTransformation(StatefulTransformation<Vector3f> transformation, BooleanSupplier condition) {
        addRelativeTransformation(new ConditionalRelativePositionTransformation(transformation, condition));
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
