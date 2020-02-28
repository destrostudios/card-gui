package com.destrostudios.cardgui.values;

public interface ValueCompositor<ValueType> {
    void compositeValue(ValueType destinationValue, ValueType sourceValue);
}
