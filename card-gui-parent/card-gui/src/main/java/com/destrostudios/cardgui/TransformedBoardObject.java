package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.handlers.PositionTransformationHandler;
import com.destrostudios.cardgui.transformations.handlers.RotationTransformationHandler;

public abstract class TransformedBoardObject<ModelType extends BoardObjectModel> extends BoardObject<ModelType> {

    protected TransformedBoardObject(ModelType model) {
        super(model);
    }
    private PositionTransformationHandler positionTransformationHandler = new PositionTransformationHandler();
    private RotationTransformationHandler rotationTransformationHandler = new RotationTransformationHandler();
    private boolean isTransformationEnabled = true;

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        if (isTransformationEnabled) {
            positionTransformationHandler.update(lastTimePerFrame);
            rotationTransformationHandler.update(lastTimePerFrame);
        }
    }

    public void resetTransformations() {
        positionTransformationHandler.reset();
        rotationTransformationHandler.reset();
    }

    public void finishTransformations() {
        positionTransformationHandler.finish();
        rotationTransformationHandler.finish();
    }

    public boolean hasReachedTargetTransform() {
        return (positionTransformationHandler.hasReachedTarget() && positionTransformationHandler.hasReachedTarget());
    }

    public void setTransformationEnabled(boolean transformationEnabled) {
        this.isTransformationEnabled = transformationEnabled;
    }

    public PositionTransformationHandler position() {
        return positionTransformationHandler;
    }

    public RotationTransformationHandler rotation() {
        return rotationTransformationHandler;
    }
}
