package com.destrostudios.cardgui.boardobjects;

import com.destrostudios.cardgui.BoardObjectModel;
import com.jme3.math.Vector3f;

public class TargetArrowModel extends BoardObjectModel {

    private Vector3f sourceLocation;
    private Vector3f targetLocation;

    public Vector3f getSourceLocation() {
        return sourceLocation;
    }

    public Vector3f getTargetLocation() {
        return targetLocation;
    }

    public void setSourceLocation(Vector3f sourceLocation) {
        updateIfNotEquals(this.sourceLocation, sourceLocation, () -> this.sourceLocation = sourceLocation);
    }

    public void setTargetLocation(Vector3f targetLocation) {
        updateIfNotEquals(this.targetLocation, targetLocation, () -> this.targetLocation = targetLocation);
    }
}
