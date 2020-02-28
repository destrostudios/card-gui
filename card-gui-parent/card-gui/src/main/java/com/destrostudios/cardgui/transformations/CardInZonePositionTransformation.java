package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.ZonePosition;
import com.jme3.math.Vector3f;

public class CardInZonePositionTransformation extends SimpleTargetPositionTransformation {

    public CardInZonePositionTransformation(ZonePosition zonePosition) {
        super(new Vector3f());
        this.zonePosition = zonePosition;
    }
    private ZonePosition zonePosition;

    @Override
    public void update(float lastTimePerFrame) {
        setTargetValue(zonePosition.getDefaultTargetPosition(), true);
        super.update(lastTimePerFrame);
    }
}
