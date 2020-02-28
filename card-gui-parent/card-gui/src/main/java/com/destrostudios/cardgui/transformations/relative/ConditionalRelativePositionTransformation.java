package com.destrostudios.cardgui.transformations.relative;

import com.destrostudios.cardgui.transformations.SimpleTargetPositionTransformation;
import com.destrostudios.cardgui.transformations.StatefulTransformation;
import com.jme3.math.Vector3f;

import java.util.function.BooleanSupplier;

public class ConditionalRelativePositionTransformation extends ConditionalRelativeTransformation<Vector3f> {

    public ConditionalRelativePositionTransformation(StatefulTransformation<Vector3f> transformation, BooleanSupplier condition) {
        super(transformation, new SimpleTargetPositionTransformation(), condition);
    }
}
