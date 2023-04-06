package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.GameLoopListener;

public abstract class TransformationSpeed<T> implements Cloneable, GameLoopListener {

    public void reset() {

    }

    @Override
    public void update(float lastTimePerFrame) {

    }

    // A speed of null means the transformation should be immediately finished
    public abstract Float getSpeed(T currentValue, T targetValue);

    @Override
    public abstract TransformationSpeed<T> clone();
}
