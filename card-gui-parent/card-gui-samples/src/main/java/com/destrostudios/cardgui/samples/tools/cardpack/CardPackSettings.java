package com.destrostudios.cardgui.samples.tools.cardpack;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.samples.tools.cardpack.patterns.CircularCardPackPattern;
import lombok.*;

import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CardPackSettings<CardModelType extends BoardObjectModel> {

    private List<CardModelType> cards;
    private CardZone cardZone;
    private BoardObjectVisualizer<CardZone> cardZoneVisualizer;
    private BoardObjectVisualizer<Card<CardModelType>> cardVisualizer;
    @Builder.Default
    private CardPackPattern cardPackPattern = new CircularCardPackPattern();
    @Builder.Default
    private float packOpenDuration = 0.3f;
    @Builder.Default
    private float packOpenDurationFactorScale = 0.5f;
    @Builder.Default
    private float cardRevealDuration = 0.3f;
    @Builder.Default
    private boolean cardRevealOnClick = true;
    @Builder.Default
    private Consumer<BoardSettings.BoardSettingsBuilder> boardSettings = boardSettingsBuilder -> {};

}
