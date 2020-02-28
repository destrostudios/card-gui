package com.destrostudios.cardgui.transformations;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class ConstantRotationTransformation extends ConstantTransformation<Quaternion> {

    public ConstantRotationTransformation() {
        this(new Quaternion());
    }

    public ConstantRotationTransformation(Quaternion value) {
        super(value);
    }

    @Override
    public void setValue(Quaternion destinationValue, Quaternion sourceValue) {
        destinationValue.set(sourceValue);
    }

    // Helpers

    public static ConstantRotationTransformation x(float radian) {
        return get(Vector3f.UNIT_X, radian);
    }

    public static ConstantRotationTransformation y(float radian) {
        return get(Vector3f.UNIT_Y, radian);
    }

    public static ConstantRotationTransformation z(float radian) {
        return get(Vector3f.UNIT_Z, radian);
    }

    private static ConstantRotationTransformation get(Vector3f axis, float radian) {
        return new ConstantRotationTransformation(new Quaternion().fromAngleAxis(radian, axis));
    }
}
