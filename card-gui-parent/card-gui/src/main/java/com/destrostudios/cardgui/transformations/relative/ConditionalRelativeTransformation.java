package com.destrostudios.cardgui.transformations.relative;

import com.destrostudios.cardgui.transformations.SimpleTargetedTransformation;
import com.destrostudios.cardgui.transformations.StatefulTransformation;

import java.util.function.BooleanSupplier;

public class ConditionalRelativeTransformation<ValueType> extends RelativeTransformation<ValueType> {

    public ConditionalRelativeTransformation(StatefulTransformation<ValueType> transformation, SimpleTargetedTransformation<ValueType> resetTransformation, BooleanSupplier condition) {
        super(transformation, resetTransformation);
        this.condition = condition;
    }
    private BooleanSupplier condition;

    @Override
    public void update(float lastTimePerFrame) {
        setEnabled(condition.getAsBoolean());
        super.update(lastTimePerFrame);
    }
}
