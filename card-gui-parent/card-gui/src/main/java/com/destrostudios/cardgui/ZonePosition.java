package com.destrostudios.cardgui;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class ZonePosition {
    
    private CardZone zone;
    private Vector3f position;

    public void setZone(CardZone zone) {
        this.zone = zone;
    }

    public CardZone getZone() {
        return zone;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getDefaultTargetPosition() {
        return zone.position().getCurrentValue().add(zone.getLocalPosition(position));
    }

    public Quaternion getDefaultTargetRotation() {
        return zone.rotation().getCurrentValue();
    }
}
