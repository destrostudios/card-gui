package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.transformations.StatefulTransformation;

public abstract class StatePreservingTransformationHandler<ValueType, TransformationType extends StatefulTransformation<ValueType>> extends TransformationHandler<ValueType, TransformationType> {

    public StatePreservingTransformationHandler(ValueType value) {
        super(value);
    }

    public void setTransformation(TransformationType transformation) {
        super.setTransformation(transformation);
        if (transformation != null) {
            transformation.setCurrentValue(getCurrentValue());
        }
    }

    public void setCurrentValue(ValueType currentValue) {
        super.setCurrentValue(currentValue);
        transformation.setCurrentValue(currentValue);
    }
}
