package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderSettings;
import com.destrostudios.cardgui.samples.tools.deckbuilder.collection.CollectionDeckBuilderAppState;
import com.destrostudios.cardgui.samples.tools.deckbuilder.collection.CollectionDeckBuilderSettings;
import com.destrostudios.cardgui.samples.visualization.DebugZoneVisualizer;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.destrostudios.cardgui.test.game.MyCard;
import com.destrostudios.cardgui.zones.SimpleIntervalZone;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import java.util.*;
import java.util.function.Predicate;

public class CollectionDeckBuilderTestApplication extends DeckBuilderTestApplication<CollectionDeckBuilderAppState<MyCardModel>> {

    public static void main(String[] args) {
        FileAssets.readRootFile();

        new CollectionDeckBuilderTestApplication().start();
    }

    private HashMap<MyCardModel, Integer> collectionCards;

    @Override
    protected CollectionDeckBuilderAppState<MyCardModel> createDeckBuilder(DeckBuilderSettings<MyCardModel> deckBuilderSettings) {
        collectionCards = new HashMap<>();
        for (int i = 0; i < 300; i++) {
            int amount = (int) (Math.random() * 4);
            collectionCards.put(TestCards.getRandomCardModel(), amount);
        }
        CardZone collectionZone = new SimpleIntervalZone(new Vector3f(-2, 0, 0), new Vector3f(1, 1, 1.4f));
        BoardObjectVisualizer<CardZone> collectionZoneVisualizer = new DebugZoneVisualizer() {

            @Override
            protected Vector2f getSize(CardZone zone) {
                return new Vector2f(16.5f, 10);
            }
        };
        CollectionDeckBuilderSettings<MyCardModel> settings = CollectionDeckBuilderSettings.<MyCardModel>builder()
                .deckBuilderSettings(deckBuilderSettings)
                .collectionCards(collectionCards)
                .collectionZone(collectionZone)
                .collectionZoneVisualizer(collectionZoneVisualizer)
                .collectionCardVisualizer(new MyCardVisualizer(false))
                .collectionCardAmountVisualizer(new MyDeckBuilderCollectionCardAmountVisualizer())
                .collectionCardsPerRow(16)
                .collectionRowsPerPage(7)
                .build();
        return new CollectionDeckBuilderAppState<>(rootNode, settings);
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        super.onAction(name, isPressed, lastTimePerFrame);
        if ("3".equals(name) && isPressed) {
            Map<MyCardModel, Integer> deck = new HashMap<>();
            LinkedList<MyCardModel> collectionCardModels = new LinkedList<>(collectionCards.keySet());
            for (int i = 0; i < 15; i++) {
                MyCardModel cardModel = collectionCardModels.get((int) (Math.random() * collectionCardModels.size()));
                deck.put(cardModel, 2);
            }
            deckBuilderAppState.setDeck(deck);
        }  else if ("4".equals(name) && isPressed) {
            Predicate<MyCardModel> collectionCardFilter = deckBuilderAppState.getCollectionCardFilter();
            deckBuilderAppState.setCollectionCardFilter((collectionCardFilter == null) ? cardModel -> cardModel.getColor() == MyCard.Color.RED : null);
        }   else if ("5".equals(name) && isPressed) {
            Comparator<MyCardModel> collectionCardOrder = deckBuilderAppState.getCollectionCardOrder();
            deckBuilderAppState.setCollectionCardOrder((collectionCardOrder == null) ? Comparator.comparing(MyCardModel::getName) : null);
        } else if ("left".equals(name) && isPressed) {
            deckBuilderAppState.goToPreviousCollectionPage();
        } else if ("right".equals(name) && isPressed) {
            deckBuilderAppState.goToNextCollectionPage();
        }
    }
}
