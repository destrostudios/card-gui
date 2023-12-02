package com.destrostudios.cardgui.samples.tools.deckbuilder.draft;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.BoardObjectVisualizer;
import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.CardZone;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderSettings;
import com.jme3.math.Vector3f;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class DraftDeckBuilderSettings<CardModelType extends BoardObjectModel> {

    private DeckBuilderSettings<CardModelType> deckBuilderSettings;
    private CardZone draftZone;
    private BoardObjectVisualizer<CardZone> draftZoneVisualizer;
    private BoardObjectVisualizer<Card<CardModelType>> draftCardVisualizer;
    @Builder.Default
    private Vector3f draftPositionInterval = new Vector3f(1, 0, 0);

}
