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
        this(position, rotation, new Vector3f(1, 1, 1), interval);
    }

    public CenteredIntervalZone(Vector3f position, Quaternion rotation, Vector3f scale, Vector3f interval) {
        super(position, rotation, scale);
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
    protected Vector3f getLocalCardPosition(Vector3f zonePosition) {
        return zonePosition.subtract(halfBounds).multLocal(interval);
    }
}
