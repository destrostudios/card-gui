package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.*;
import lombok.*;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class DeckBuilderSettings<CardModelType extends BoardObjectModel> {

    private CardZone deckZone;
    private BoardObjectVisualizer<CardZone> deckZoneVisualizer;
    private BoardObjectVisualizer<Card<DeckBuilderDeckCardModel<CardModelType>>> deckCardVisualizer;
    private Comparator<CardModelType> deckCardOrder;
    private Integer deckCardsMaximumTotal;
    private Function<CardModelType, Integer> deckCardsMaximumUnique;
    private Predicate<CardModelType> isAllowedToAddCard;
    private Consumer<CardModelType> cardNotAddableCallback;
    private Consumer<CardModelType> cardAddedCallback;
    private Consumer<CardModelType> cardRemovedCallback;
    private Consumer<CardModelType> cardClearedCallback;
    private Function<Card<DeckBuilderDeckCardModel<CardModelType>>, Animation> deckCardEntryAnimation;
    @Builder.Default
    private BoardSettings boardSettings = new BoardSettings();

}
