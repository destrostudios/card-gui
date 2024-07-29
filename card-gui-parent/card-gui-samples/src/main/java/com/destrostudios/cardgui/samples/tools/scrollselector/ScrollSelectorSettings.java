package com.destrostudios.cardgui.samples.tools.scrollselector;

import com.destrostudios.cardgui.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.function.BiConsumer;

@SuperBuilder
@Getter
public class ScrollSelectorSettings<CardModelType extends BoardObjectModel> {

    private List<CardModelType> cards;
    private CardZone cardZone;
    private BoardObjectVisualizer<CardZone> cardZoneVisualizer;
    private BoardObjectVisualizer<Card<CardModelType>> cardVisualizer;
    private BiConsumer<CardModelType, Boolean> cardSelectionChangeCallback;
    private Integer maximumSelectedCards;
    @Builder.Default
    private BoardSettings boardSettings = new BoardSettings();

}
