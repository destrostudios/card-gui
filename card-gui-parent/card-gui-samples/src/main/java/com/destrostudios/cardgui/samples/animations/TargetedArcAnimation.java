package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.samples.transformations.ArcTargetPositionTransformation3f;
import com.jme3.math.Vector3f;

public class TargetedArcAnimation extends Animation {

    public TargetedArcAnimation(TransformedBoardObject transformedBoardObject, TransformedBoardObject targetTransformedBoardObject, float arcHeight, float duration) {
        this.transformedBoardObject = transformedBoardObject;
        this.targetTransformedBoardObject = targetTransformedBoardObject;
        this.arcHeight = arcHeight;
        this.duration = duration;
    }
    private TransformedBoardObject transformedBoardObject;
    private TransformedBoardObject targetTransformedBoardObject;
    private float arcHeight;
    private float duration;
    private boolean setArcTransformation;

    @Override
    public void start() {
        super.start();
        setArcTransformation = true;
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        Vector3f oldTargetValue = transformedBoardObject.position().getTransformation().getTargetValue();
        Vector3f newTargetValue = targetTransformedBoardObject.position().getCurrentValue();
        if (!oldTargetValue.equals(newTargetValue)) {
            setArcTransformation = true;
        }
        if (setArcTransformation) {
            transformedBoardObject.position().setTransformation(new ArcTargetPositionTransformation3f(newTargetValue.clone(), arcHeight, duration));
            setArcTransformation = false;
        }
    }

    @Override
    public boolean isFinished() {
        return transformedBoardObject.hasReachedTargetTransform();
    }
}
