package com.destrostudios.cardgui.samples.tools.deckbuilder.collection;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.BoardObjectVisualizer;
import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.CardZone;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderSettings;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CollectionDeckBuilderSettings<CardModelType extends BoardObjectModel> {

    private DeckBuilderSettings<CardModelType> deckBuilderSettings;
    private CardZone collectionZone;
    private BoardObjectVisualizer<CardZone> collectionZoneVisualizer;
    private BoardObjectVisualizer<Card<CardModelType>> collectionCardVisualizer;
    private BoardObjectVisualizer<CollectionDeckBuilderCardAmount> collectionCardAmountVisualizer;
    @Builder.Default
    private int collectionCardsPerRow = 5;
    @Builder.Default
    private int collectionRowsPerPage = 4;

}
