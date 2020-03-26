package com.destrostudios.cardgui.transformations;

public abstract class TargetedTransformation<ValueType> extends DynamicTransformation<ValueType> implements Cloneable {

    public TargetedTransformation(ValueType value, ValueType targetValue) {
        super(value);
        this.targetValue = targetValue;
    }
    protected ValueType targetValue;

    public void finish() {
        setCurrentValue(targetValue);
    }

    protected void setTargetValue(ValueType targetValue) {
        setValue(this.targetValue, targetValue);
    }

    public ValueType getTargetValue() {
        return targetValue;
    }

    public boolean hasReachedTarget() {
        return currentValue.equals(targetValue);
    }

    @Override
    public abstract TargetedTransformation<ValueType> clone();
}
