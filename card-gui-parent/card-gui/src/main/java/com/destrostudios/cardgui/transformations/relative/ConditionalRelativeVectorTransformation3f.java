package com.destrostudios.cardgui.transformations.relative;

import com.destrostudios.cardgui.transformations.LinearTargetVectorTransformation3F;
import com.destrostudios.cardgui.transformations.StatefulTransformation;
import com.jme3.math.Vector3f;

import java.util.function.BooleanSupplier;

public class ConditionalRelativeVectorTransformation3f extends ConditionalRelativeTransformation<Vector3f> {

    public ConditionalRelativeVectorTransformation3f(StatefulTransformation<Vector3f> transformation, BooleanSupplier condition) {
        super(transformation, new LinearTargetVectorTransformation3F(), condition);
    }
}
