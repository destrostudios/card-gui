package com.destrostudios.cardgui.samples.transformations;

import com.destrostudios.cardgui.FloatInterpolate;
import com.destrostudios.cardgui.transformations.SimpleTargetPositionTransformation3f;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed3f;
import com.jme3.math.Vector3f;

public class ArcTargetPositionTransformation3f extends SimpleTargetPositionTransformation3f {

    public ArcTargetPositionTransformation3f(Vector3f targetPosition, float arcHeight, float duration) {
        super(new Vector3f(), targetPosition, new TimeBasedPositionTransformationSpeed3f(duration));
        this.arcHeight = arcHeight;
        this.duration = duration;
        this.resetArc = true;
    }
    private float arcHeight;
    private float duration;
    private boolean resetArc;
    private Vector3f arcFlatPosition = new Vector3f();
    private float arcLength;

    @Override
    protected void setTargetValue(Vector3f targetValue) {
        super.setTargetValue(targetValue);
        resetArc = true;
    }

    @Override
    protected Vector3f getNewValue(Vector3f currentValue, Vector3f targetValue, float speed, float lastTimePerFrame) {
        if (resetArc) {
            arcFlatPosition.set(currentValue);
            arcLength = targetValue.distance(currentValue);
            resetArc = false;
        }

        arcFlatPosition.set(FloatInterpolate.get(arcFlatPosition, targetValue, speed, lastTimePerFrame));

        // Parabole with x (0 to 1) and y (0 to arcHeight)
        float arcX = (1 - (arcFlatPosition.distance(targetValue) / arcLength));
        float arcY = (((-4 * arcHeight) * (arcX * arcX)) + ((4 * arcHeight) * arcX));

        return arcFlatPosition.add(0, arcY, 0);
    }

    @Override
    public ArcTargetPositionTransformation3f clone() {
        return new ArcTargetPositionTransformation3f(targetValue.clone(), arcHeight, duration);
    }
}
