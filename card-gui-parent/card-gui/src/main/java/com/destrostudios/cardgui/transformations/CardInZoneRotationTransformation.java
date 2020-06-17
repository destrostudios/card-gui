package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.ZonePosition;
import com.jme3.math.Quaternion;

public class CardInZoneRotationTransformation extends LinearTargetRotationTransformation {

    public CardInZoneRotationTransformation(ZonePosition zonePosition) {
        super(new Quaternion());
        this.zonePosition = zonePosition;
    }
    private ZonePosition zonePosition;

    @Override
    public void update(float lastTimePerFrame) {
        setTargetValue(zonePosition.getDefaultTargetRotation(), true);
        super.update(lastTimePerFrame);
    }
}
