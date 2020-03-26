package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.transformations.TargetedTransformation;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class TargetedTransformationAnimation extends Animation {

    public TargetedTransformationAnimation(TransformedBoardObject transformedBoardObject, TargetedTransformation<Vector3f> positionTransformation, TargetedTransformation<Quaternion> rotationTransformation) {
        this.transformedBoardObject = transformedBoardObject;
        this.positionTransformation = positionTransformation;
        this.rotationTransformation = rotationTransformation;
    }
    private TransformedBoardObject transformedBoardObject;
    private TargetedTransformation<Vector3f> positionTransformation;
    private TargetedTransformation<Quaternion> rotationTransformation;

    @Override
    public void start() {
        super.start();
        transformedBoardObject.position().setTransformation(positionTransformation);
        transformedBoardObject.rotation().setTransformation(rotationTransformation);
    }

    @Override
    public boolean isFinished() {
        return transformedBoardObject.hasReachedTargetTransform();
    }
}
