package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.ZonePosition;
import com.jme3.math.Vector3f;

public class CardInZoneScaleTransformation extends LinearTargetVectorTransformation3F {

    public CardInZoneScaleTransformation(ZonePosition zonePosition) {
        super(new Vector3f());
        this.zonePosition = zonePosition;
    }
    private ZonePosition zonePosition;

    @Override
    public void update(float lastTimePerFrame) {
        setTargetValue(zonePosition.getDefaultTargetScale(), true);
        super.update(lastTimePerFrame);
    }
}
