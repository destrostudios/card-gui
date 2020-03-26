package com.destrostudios.cardgui.animations;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.transformations.TargetedTransformation;
import com.destrostudios.cardgui.TransformedBoardObject;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import java.util.Collection;

public abstract class FixedTransformAnimation<PositionTransformationType extends TargetedTransformation<Vector3f>, RotationTransformationType extends TargetedTransformation<Quaternion>> extends Animation {

    public FixedTransformAnimation(Collection<? extends TransformedBoardObject> transformedBoardObjects) {
        this(transformedBoardObjects, false);
    }

    public FixedTransformAnimation(Collection<? extends TransformedBoardObject> transformedBoardObjects, boolean reevaluateEveryFrame) {
        this.transformedBoardObjects = transformedBoardObjects;
        this.reevaluateEveryFrame = reevaluateEveryFrame;
    }
    private Collection<? extends TransformedBoardObject> transformedBoardObjects;
    private boolean reevaluateEveryFrame;
    private boolean allTargetTransformationsReached;

    @Override
    public void start() {
        super.start();
        PositionTransformationType positionTransformation = createPositionTransform();
        RotationTransformationType rotationTransformation = createRotationTransform();
        int index = 0;
        for (TransformedBoardObject transformedBoardObject : transformedBoardObjects) {
            if (positionTransformation != null) {
                PositionTransformationType clonedPositionTransformation = (PositionTransformationType) positionTransformation.clone();
                updatePositionTransform(index, transformedBoardObject, clonedPositionTransformation);
                transformedBoardObject.position().setTransformation(clonedPositionTransformation);
            }
            if (rotationTransformation != null) {
                RotationTransformationType clonedRotationTransformation = (RotationTransformationType) rotationTransformation.clone();
                updateRotationTransform(index, transformedBoardObject, clonedRotationTransformation);
                transformedBoardObject.rotation().setTransformation(clonedRotationTransformation);
            }
            index++;
        }
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        if (reevaluateEveryFrame) {
            int index = 0;
            for (TransformedBoardObject transformedBoardObject : transformedBoardObjects) {
                if (!transformedBoardObject.position().hasReachedTarget()) {
                    updatePositionTransform(index, transformedBoardObject, (PositionTransformationType) transformedBoardObject.position().getTransformation());
                }
                if (!transformedBoardObject.rotation().hasReachedTarget()) {
                    updateRotationTransform(index, transformedBoardObject, (RotationTransformationType) transformedBoardObject.rotation().getTransformation());
                }
                index++;
            }
        }
        allTargetTransformationsReached = transformedBoardObjects.stream().allMatch(transformedBoardObject -> transformedBoardObject.hasReachedTargetTransform());
    }

    @Override
    public boolean isFinished() {
        return allTargetTransformationsReached;
    }

    protected abstract PositionTransformationType createPositionTransform();

    protected abstract void updatePositionTransform(int index, TransformedBoardObject transformedBoardObject, PositionTransformationType positionTransformation);

    protected abstract RotationTransformationType createRotationTransform();

    protected abstract void updateRotationTransform(int index, TransformedBoardObject transformedBoardObject, RotationTransformationType rotationTransformation);
}
