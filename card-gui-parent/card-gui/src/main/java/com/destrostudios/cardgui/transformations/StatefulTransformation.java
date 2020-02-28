package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.values.ValueSetter;

public abstract class StatefulTransformation<ValueType> extends Transformation<ValueType> implements ValueSetter<ValueType> {

    public StatefulTransformation(ValueType value) {
        this.currentValue = value;
    }
    protected ValueType currentValue;

    public void setCurrentValue(ValueType currentValue) {
        setValue(this.currentValue, currentValue);
    }

    @Override
    public ValueType getCurrentValue() {
        return currentValue;
    }
}
