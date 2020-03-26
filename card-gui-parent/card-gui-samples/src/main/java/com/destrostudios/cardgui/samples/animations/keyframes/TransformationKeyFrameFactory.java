package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.destrostudios.cardgui.transformations.SimpleTargetPositionTransformation3f;
import com.destrostudios.cardgui.transformations.SimpleTargetRotationTransformation;
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
        SimpleTargetPositionTransformation3f positionTransformation = new SimpleTargetPositionTransformation3f(position, new TimeBasedPositionTransformationSpeed3f(duration));
        SimpleTargetRotationTransformation rotationTransformation = new SimpleTargetRotationTransformation(rotation, new TimeBasedRotationTransformationSpeed(duration));
        return new TransformationKeyFrame(positionTransformation, rotationTransformation);
    }
}
