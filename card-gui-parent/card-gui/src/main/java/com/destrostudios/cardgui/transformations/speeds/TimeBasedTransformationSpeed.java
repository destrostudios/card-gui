package com.destrostudios.cardgui.transformations.speeds;

import com.destrostudios.cardgui.transformations.TransformationSpeed;

public abstract class TimeBasedTransformationSpeed<T> extends TransformationSpeed<T> {

    public TimeBasedTransformationSpeed(float duration) {
        this.duration = duration;
    }
    protected final float duration;
    protected float passedTime;

    @Override
    public void reset() {
        super.reset();
        passedTime = 0;
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        passedTime += lastTimePerFrame;
    }
}
