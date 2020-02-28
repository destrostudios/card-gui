package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.GameLoopListener;

public abstract class Transformation<ValueType> implements GameLoopListener {
    public abstract ValueType getCurrentValue();
}
