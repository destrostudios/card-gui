package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.handlers.VectorTransformationHandler3f;
import com.destrostudios.cardgui.transformations.handlers.RotationTransformationHandler;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public abstract class TransformedBoardObject<ModelType extends BoardObjectModel> extends BoardObject<ModelType> {

    protected TransformedBoardObject(ModelType model) {
        super(model);
    }
    private VectorTransformationHandler3f positionTransformationHandler3f = new VectorTransformationHandler3f(Vector3f::new);
    private RotationTransformationHandler rotationTransformationHandler = new RotationTransformationHandler(Quaternion::new);
    private VectorTransformationHandler3f scaleTransformationHandler3f = new VectorTransformationHandler3f(() -> new Vector3f(1, 1, 1));
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

    public void resetTransformations() {
        positionTransformationHandler3f.reset();
        rotationTransformationHandler.reset();
        scaleTransformationHandler3f.reset();
    }

    public void finishTransformations() {
        positionTransformationHandler3f.finish();
        rotationTransformationHandler.finish();
        scaleTransformationHandler3f.reset();
    }

    public boolean hasReachedTargetTransform() {
        return (positionTransformationHandler3f.hasReachedTarget() && rotationTransformationHandler.hasReachedTarget() && scaleTransformationHandler3f.hasReachedTarget());
    }

    public void setTransformationEnabled(boolean transformationEnabled) {
        this.isTransformationEnabled = transformationEnabled;
    }

    public VectorTransformationHandler3f position() {
        return positionTransformationHandler3f;
    }

    public RotationTransformationHandler rotation() {
        return rotationTransformationHandler;
    }

    public VectorTransformationHandler3f scale() {
        return scaleTransformationHandler3f;
    }
}
