package com.destrostudios.cardgui.values;

public interface ValueSetter<ValueType> {
    void setValue(ValueType destinationValue, ValueType sourceValue);
}
