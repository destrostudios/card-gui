package com.destrostudios.cardgui.zones;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class CenteredIntervalZone extends BoundedZone {

    public CenteredIntervalZone(Vector3f position, Vector3f interval) {
        this(position, new Quaternion(), interval);
    }

    public CenteredIntervalZone(Vector3f position, Quaternion rotation, Vector3f interval) {
        super(position, rotation);
        this.interval = interval;
    }
    private Vector3f interval;
    private Vector3f halfBounds;

    @Override
    protected void updateBounds() {
        super.updateBounds();
        halfBounds = ((bounds != null) ? bounds.divide(2) : null);
    }

    @Override
    public Vector3f getLocalPosition(Vector3f zonePosition) {
        return (zonePosition.subtract(halfBounds)).mult(interval);
    }
}
