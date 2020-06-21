package com.destrostudios.cardgui.zones;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public abstract class SimpleScalingIntervalZone extends SimpleIntervalZone {

    public SimpleScalingIntervalZone(Vector3f position, Vector3f interval) {
        this(position, new Quaternion(), interval);
    }

    public SimpleScalingIntervalZone(Vector3f position, Quaternion rotation, Vector3f interval) {
        super(position, rotation, interval);
    }
    private Vector3f scaleFactor = new Vector3f();

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        float scale = getScale();
        scaleFactor.set(scale, scale, scale);
        interval.set(scaleFactor);
    }

    protected abstract float getScale();

    @Override
    protected Vector3f getLocalCardScale(Vector3f zonePosition) {
        return scaleFactor;
    }
}
