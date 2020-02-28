package com.destrostudios.cardgui.transformations.relative;

import com.destrostudios.cardgui.transformations.SimpleTargetRotationTransformation;
import com.destrostudios.cardgui.transformations.StatefulTransformation;
import com.jme3.math.Quaternion;

import java.util.function.BooleanSupplier;

public class ConditionalRelativeRotationTransformation extends ConditionalRelativeTransformation<Quaternion> {

    public ConditionalRelativeRotationTransformation(StatefulTransformation<Quaternion> transformation, BooleanSupplier condition) {
        super(transformation, new SimpleTargetRotationTransformation(), condition);
    }
}
