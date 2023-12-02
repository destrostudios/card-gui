package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.BoardObjectVisualizer;
import com.destrostudios.cardgui.CardZone;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderSettings;
import com.destrostudios.cardgui.samples.tools.deckbuilder.draft.DraftDeckBuilderAppState;
import com.destrostudios.cardgui.samples.tools.deckbuilder.draft.DraftDeckBuilderSettings;
import com.destrostudios.cardgui.samples.visualization.DebugZoneVisualizer;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.destrostudios.cardgui.zones.CenteredIntervalZone;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import java.util.*;

public class DraftDeckBuilderTestApplication extends DeckBuilderTestApplication<DraftDeckBuilderAppState<MyCardModel>> {

    public static void main(String[] args) {
        FileAssets.readRootFile();

        new DraftDeckBuilderTestApplication().start();
    }

    @Override
    protected DraftDeckBuilderAppState<MyCardModel> createDeckBuilder(DeckBuilderSettings<MyCardModel> deckBuilderSettings) {
        CardZone draftZone = new CenteredIntervalZone(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        BoardObjectVisualizer<CardZone> draftZoneVisualizer = new DebugZoneVisualizer() {

            @Override
            protected Vector2f getSize(CardZone zone) {
                return new Vector2f(4.7f, 2);
            }
        };
        DraftDeckBuilderSettings<MyCardModel> settings = DraftDeckBuilderSettings.<MyCardModel>builder()
                .deckBuilderSettings(deckBuilderSettings)
                .draftZone(draftZone)
                .draftZoneVisualizer(draftZoneVisualizer)
                .draftCardVisualizer(new MyCardVisualizer(false))
                .build();
        return new DraftDeckBuilderAppState<>(rootNode, settings);
    }

    @Override
    protected void onCallback(String key, MyCardModel cardModel) {
        super.onCallback(key, cardModel);
        if (key.equals("cardAdded")) {
            setRandomDraftCards();
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        super.onAction(name, isPressed, lastTimePerFrame);
        if ("3".equals(name) && isPressed) {
            setRandomDraftCards();
        }
    }

    private void setRandomDraftCards() {
        int draftCardsCount = (2 + ((int) (Math.random() * 3)));
        ArrayList<MyCardModel> draftCards = new ArrayList<>(draftCardsCount);
        for (int i = 0; i < draftCardsCount; i++) {
            draftCards.add(TestCards.getRandomCardModel());
        }
        deckBuilderAppState.setDraftCards(draftCards);
    }
}
