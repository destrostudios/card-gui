package com.destrostudios.cardgui.samples.tools.deckbuilder.draft;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.InteractivitySource;
import com.destrostudios.cardgui.events.MoveCardEvent;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import java.util.Collection;
import java.util.HashMap;

public class DraftDeckBuilderAppState<CardModelType extends BoardObjectModel> extends DeckBuilderAppState<CardModelType> {

    public DraftDeckBuilderAppState(Node rootNode, DraftDeckBuilderSettings<CardModelType> settings) {
        super(rootNode, settings.getDeckBuilderSettings());
        this.settings = settings;
        init();
    }
    private DraftDeckBuilderSettings<CardModelType> settings;
    private HashMap<CardModelType, Card<CardModelType>> draftCards = new HashMap<>();

    private void init() {
        addZone(settings.getDraftZone(), settings.getDraftZoneVisualizer(), settings.getDraftCardVisualizer());
    }

    public void setDraftCards(Collection<CardModelType> draftCards) {
        // Remove old
        clearZone(settings.getDraftZone());
        this.draftCards.clear();

        // Add new
        int index = 0;
        for (CardModelType cardModel : draftCards) {
            Card<CardModelType> card = new Card<>(cardModel);
            card.setInteractivity(InteractivitySource.MOUSE_LEFT, clickToAddInteractivity);
            this.draftCards.put(cardModel, card);
            Vector3f position = settings.getDraftPositionInterval().mult(index);
            board.triggerEvent(new MoveCardEvent(card, settings.getDraftZone(), position));
            index++;
        }

        board.finishAllTransformations();
    }

    @Override
    protected boolean canRemoveCardFromDeck(CardModelType cardModel) {
        return false;
    }
}
