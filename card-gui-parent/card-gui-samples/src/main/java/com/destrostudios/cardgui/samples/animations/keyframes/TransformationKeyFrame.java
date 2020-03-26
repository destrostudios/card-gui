package com.destrostudios.cardgui.samples.animations.keyframes;

import com.destrostudios.cardgui.transformations.TargetedTransformation;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TransformationKeyFrame {

    private TargetedTransformation<Vector3f> positionTransformation;
    private TargetedTransformation<Quaternion> rotationTransformation;

}
