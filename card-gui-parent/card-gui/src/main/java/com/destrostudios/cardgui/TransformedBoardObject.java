package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.handlers.PositionTransformationHandler3f;
import com.destrostudios.cardgui.transformations.handlers.ScaleTransformationHandler3f;
import com.destrostudios.cardgui.transformations.handlers.RotationTransformationHandler;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public abstract class TransformedBoardObject<ModelType extends BoardObjectModel> extends BoardObject<ModelType> {

    protected TransformedBoardObject(ModelType model) {
        super(model);
    }
    private PositionTransformationHandler3f positionTransformationHandler3f = new PositionTransformationHandler3f(Vector3f::new);
    private RotationTransformationHandler rotationTransformationHandler = new RotationTransformationHandler(Quaternion::new);
    private ScaleTransformationHandler3f scaleTransformationHandler3f = new ScaleTransformationHandler3f(() -> new Vector3f(1, 1, 1));
    private boolean isTransformationEnabled = true;

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        if (isTransformationEnabled) {
            positionTransformationHandler3f.update(lastTimePerFrame);
            rotationTransformationHandler.update(lastTimePerFrame);
            scaleTransformationHandler3f.update(lastTimePerFrame);
        }
    }

    public void setTransformationEnabled(boolean transformationEnabled) {
        if (transformationEnabled != isTransformationEnabled){
            isTransformationEnabled = transformationEnabled;
            // Reset the transformations when enabling, since the transformation itself won'T reset the speed if the target value hasn't changed
            if (isTransformationEnabled) {
                resetTransformations();
            }
        }
    }

    public void resetTransformations() {
        positionTransformationHandler3f.reset();
        rotationTransformationHandler.reset();
        scaleTransformationHandler3f.reset();
    }

    public void finishTransformations() {
        positionTransformationHandler3f.finish();
        rotationTransformationHandler.finish();
        scaleTransformationHandler3f.finish();
    }

    public boolean hasReachedTargetTransform() {
        return (positionTransformationHandler3f.hasReachedTarget() && rotationTransformationHandler.hasReachedTarget() && scaleTransformationHandler3f.hasReachedTarget());
    }

    public PositionTransformationHandler3f position() {
        return positionTransformationHandler3f;
    }

    public RotationTransformationHandler rotation() {
        return rotationTransformationHandler;
    }

    public ScaleTransformationHandler3f scale() {
        return scaleTransformationHandler3f;
    }
}
