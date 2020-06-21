package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.CardInZonePositionTransformation;
import com.destrostudios.cardgui.transformations.CardInZoneRotationTransformation;
import com.destrostudios.cardgui.transformations.CardInZoneScaleTransformation;

/**
 *
 * @author Carl
 */
public class Card<ModelType extends BoardObjectModel> extends TransformedBoardObject<ModelType> {

    public Card(ModelType model) {
        super(model);
        position().setDefaultTransformationProvider(() -> new CardInZonePositionTransformation(zonePosition));
        rotation().setDefaultTransformationProvider(() -> new CardInZoneRotationTransformation(zonePosition));
        scale().setDefaultTransformationProvider(() -> new CardInZoneScaleTransformation(zonePosition));
    }
    private ZonePosition zonePosition = new ZonePosition();

    public ZonePosition getZonePosition() {
        return zonePosition;
    }
}
