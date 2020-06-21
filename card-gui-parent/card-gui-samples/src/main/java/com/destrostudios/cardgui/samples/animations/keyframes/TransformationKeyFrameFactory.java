package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.destrostudios.cardgui.transformations.LinearTargetVectorTransformation3F;
import com.destrostudios.cardgui.transformations.LinearTargetRotationTransformation;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed3f;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedRotationTransformationSpeed;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class TransformationKeyFrameFactory {

    public static TransformationKeyFrame createConstantKeyFrame(Vector3f position, Quaternion rotation) {
        ConstantButTargetedTransformation<Vector3f> positionTransformation = new ConstantButTargetedTransformation<>(position);
        ConstantButTargetedTransformation<Quaternion> rotationTransformation = new ConstantButTargetedTransformation<>(rotation);
        return new TransformationKeyFrame(positionTransformation, rotationTransformation);
    }

    public static TransformationKeyFrame createLinearKeyFrame(Vector3f position, Quaternion rotation, float duration) {
        LinearTargetVectorTransformation3F positionTransformation = new LinearTargetVectorTransformation3F(position, new TimeBasedVectorTransformationSpeed3f(duration));
        LinearTargetRotationTransformation rotationTransformation = new LinearTargetRotationTransformation(rotation, new TimeBasedRotationTransformationSpeed(duration));
        return new TransformationKeyFrame(positionTransformation, rotationTransformation);
    }
}
