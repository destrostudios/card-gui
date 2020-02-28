package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.transformations.TargetedTransformation;

public abstract class TargetedTransformationHandler<ValueType> extends StatePreservingTransformationHandler<ValueType, TargetedTransformation<ValueType>> {

    public TargetedTransformationHandler(ValueType value) {
        super(value);
    }
    private boolean finishNextFrame;

    @Override
    protected void updateTransformation(float lastTimePerFrame) {
        super.updateTransformation(lastTimePerFrame);
        if (finishNextFrame) {
            transformation.finish();
            finishNextFrame = false;
        }
    }

    public void finish() {
        finishNextFrame = true;
    }

    public boolean hasReachedTarget() {
        return ((transformation == null) || transformation.hasReachedTarget());
    }
}
