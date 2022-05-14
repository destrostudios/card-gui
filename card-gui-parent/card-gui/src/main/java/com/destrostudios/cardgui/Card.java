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
    }
    private ZonePosition zonePosition = new ZonePosition();

    @Override
    public void onRegister(Board board, int id) {
        super.onRegister(board, id);
        position().setDefaultTransformationProvider(() -> new CardInZonePositionTransformation(zonePosition, board.getSettings().getCardInZonePositionTransformationSpeed().get()));
        rotation().setDefaultTransformationProvider(() -> new CardInZoneRotationTransformation(zonePosition, board.getSettings().getCardInZoneRotationTransformationSpeed().get()));
        scale().setDefaultTransformationProvider(() -> new CardInZoneScaleTransformation(zonePosition, board.getSettings().getCardInZoneScaleTransformationSpeed().get()));
    }

    public ZonePosition getZonePosition() {
        return zonePosition;
    }
}
