package com.destrostudios.cardgui;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Carl
 */
@Setter
@Getter
public class ZonePosition {

    private CardZone zone;
    private Vector3f position;

    public Vector3f getDefaultTargetPosition() {
        return zone.getCardPosition(position);
    }

    public Quaternion getDefaultTargetRotation() {
        return zone.getCardRotation(position);
    }

    public Vector3f getDefaultTargetScale() {
        return zone.getCardScale(position);
    }
}
