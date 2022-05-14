package com.destrostudios.cardgui.transformations;

import com.destrostudios.cardgui.ZonePosition;
import com.jme3.math.Vector3f;

public class CardInZoneScaleTransformation extends LinearTargetVectorTransformation3f {

    public CardInZoneScaleTransformation(ZonePosition zonePosition, TransformationSpeed<Vector3f> transformationSpeed) {
        super(new Vector3f(), transformationSpeed);
        this.zonePosition = zonePosition;
    }
    private ZonePosition zonePosition;

    @Override
    public void update(float lastTimePerFrame) {
        setTargetValue(zonePosition.getDefaultTargetScale(), true);
        super.update(lastTimePerFrame);
    }
}
