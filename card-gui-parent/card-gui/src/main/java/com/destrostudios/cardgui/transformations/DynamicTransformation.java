package com.destrostudios.cardgui.transformations;

public abstract class DynamicTransformation<ValueType> extends StatefulTransformation<ValueType> implements Cloneable {

    public DynamicTransformation(ValueType value) {
        super(value);
    }

    @Override
    public void update(float lastTimePerFrame) {
        ValueType currentValue = getCurrentValue();
        ValueType newValue = getNewValue(currentValue, lastTimePerFrame);
        setCurrentValue(newValue);
    }

    public abstract ValueType getNewValue(ValueType currentValue, float lastTimePerFrame);
}
