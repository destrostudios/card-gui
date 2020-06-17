package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.destrostudios.cardgui.transformations.LinearTargetPositionTransformation3f;
import com.destrostudios.cardgui.transformations.LinearTargetRotationTransformation;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed3f;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedRotationTransformationSpeed;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class TransformationKeyFrameFactory {

    public static TransformationKeyFrame createConstantKeyFrame(Vector3f position, Quaternion rotation) {
        ConstantButTargetedTransformation<Vector3f> positionTransformation = new ConstantButTargetedTransformation<>(position);
        ConstantButTargetedTransformation<Quaternion> rotationTransformation = new ConstantButTargetedTransformation<>(rotation);
        return new TransformationKeyFrame(positionTransformation, rotationTransformation);
    }

    public static TransformationKeyFrame createSimpleKeyFrame(Vector3f position, Quaternion rotation, float duration) {
        LinearTargetPositionTransformation3f positionTransformation = new LinearTargetPositionTransformation3f(position, new TimeBasedPositionTransformationSpeed3f(duration));
        LinearTargetRotationTransformation rotationTransformation = new LinearTargetRotationTransformation(rotation, new TimeBasedRotationTransformationSpeed(duration));
        return new TransformationKeyFrame(positionTransformation, rotationTransformation);
    }
}
