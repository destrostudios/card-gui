package com.destrostudios.cardgui.zones;

import com.destrostudios.cardgui.CardZone;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class SimpleIntervalZone extends CardZone {

    public SimpleIntervalZone(Vector3f position, Vector3f interval) {
        this(position, new Quaternion(), interval);
    }

    public SimpleIntervalZone(Vector3f position, Quaternion rotation, Vector3f interval) {
        this(position, rotation, new Vector3f(1, 1, 1), interval);
    }

    public SimpleIntervalZone(Vector3f position, Quaternion rotation, Vector3f scale, Vector3f interval) {
        super(position, rotation, scale);
        this.interval = interval;
    }
    protected Vector3f interval;

    @Override
    protected Vector3f getLocalCardPosition(Vector3f zonePosition) {
        return zonePosition.mult(interval);
    }
}
