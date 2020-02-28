package com.destrostudios.cardgui.transformations;

public class ConstantButTargetedTransformation<ValueType extends Object> extends TargetedTransformation<ValueType> {

    public ConstantButTargetedTransformation(ValueType value) {
        super(value, value);
    }

    @Override
    public ValueType getNewValue(ValueType currentValue, float lastTimePerFrame) {
        return targetValue;
    }

    @Override
    public void setValue(ValueType destinationValue, ValueType sourceValue) {
        // Nothing to do here
    }

    @Override
    public ConstantButTargetedTransformation<ValueType> clone() {
        throw new UnsupportedOperationException();
    }
}
