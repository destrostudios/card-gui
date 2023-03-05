package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.*;
import com.jme3.math.Vector3f;
import lombok.*;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class DeckBuilderSettings<CardModelType extends BoardObjectModel> {

    private Map<CardModelType, Integer> collectionCards;
    private CardZone collectionZone;
    private CardZone deckZone;
    private BoardObjectVisualizer<CardZone> collectionZoneVisualizer;
    private BoardObjectVisualizer<CardZone> deckZoneVisualizer;
    private BoardObjectVisualizer<Card<CardModelType>> collectionCardVisualizer;
    private BoardObjectVisualizer<DeckBuilderCollectionCardAmount> collectionCardAmountVisualizer;
    private BoardObjectVisualizer<Card<DeckBuilderDeckCardModel<CardModelType>>> deckCardVisualizer;
    private Comparator<CardModelType> deckCardOrder;
    private Integer deckCardsMaximumTotal;
    private Map<CardModelType, Integer> deckCardsMaximumUnique;
    private Consumer<CardModelType> cardNotAddableCallback;
    private Function<Card<DeckBuilderDeckCardModel<CardModelType>>, Animation> deckCardEntryAnimation;
    @Builder.Default
    private Vector3f collectionPositionInterval = new Vector3f(0, 0, 1);
    @Builder.Default
    private int collectionCardsPerRow = 5;
    @Builder.Default
    private int collectionRowsPerPage = 4;
    @Builder.Default
    private BoardSettings boardSettings = new BoardSettings();

}
