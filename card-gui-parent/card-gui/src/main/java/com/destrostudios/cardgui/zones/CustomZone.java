package com.destrostudios.cardgui.zones;

import com.destrostudios.cardgui.CardZone;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class CustomZone extends CardZone {

    public CustomZone(Vector3f position) {
        this(position, new Quaternion());
    }

    public CustomZone(Vector3f position, Quaternion rotation) {
        super(position, rotation);
    }

    @Override
    public Vector3f getLocalPosition(Vector3f zonePosition) {
        return zonePosition;
    }
}
