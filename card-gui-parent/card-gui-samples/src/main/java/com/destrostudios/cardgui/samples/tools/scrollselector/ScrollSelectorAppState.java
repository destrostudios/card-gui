package com.destrostudios.cardgui.samples.tools.scrollselector;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.events.MoveCardEvent;
import com.destrostudios.cardgui.interactivities.ClickInteractivity;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScrollSelectorAppState<CardModelType extends BoardObjectModel> extends BaseAppState {

    public ScrollSelectorAppState(Node rootNode, ScrollSelectorSettings<CardModelType> settings) {
        this.rootNode = rootNode;
        this.settings = settings;
        initBoard();
    }
    private Node rootNode;
    private ScrollSelectorSettings<CardModelType> settings;
    private Board board;
    private BoardAppState boardAppState;
    private LinkedHashMap<CardModelType, Card<CardModelType>> cards;
    private LinkedHashMap<CardModelType, Boolean> cardsSelected;
    @Getter
    private int focusedCardIndex;

    private void initBoard() {
        board = new Board(settings.getBoardSettings());
        board.addZone(settings.getCardZone());
        if (settings.getCardZoneVisualizer() != null) {
            board.registerVisualizer(settings.getCardZone(), settings.getCardZoneVisualizer());
        }
        board.registerVisualizer_ZonePosition(zonePosition -> zonePosition.getZone() == settings.getCardZone(), settings.getCardVisualizer());
        boardAppState = new BoardAppState(board, rootNode);
        initCards();
    }

    private void initCards() {
        cards = new LinkedHashMap<>();
        cardsSelected = new LinkedHashMap<>();
        for (CardModelType cardModel : settings.getCards()) {
            Card<CardModelType> card = new Card<>(cardModel);
            card.setInteractivity(InteractivitySource.MOUSE_LEFT, new ClickInteractivity() {

                @Override
                public void trigger(BoardObject source, BoardObject target) {
                    toggleCardSelected(card.getModel());
                }
            });
            cards.put(cardModel, card);
            cardsSelected.put(cardModel, false);
        }
        updateCards();
    }

    private void updateCards() {
        int i = 0;
        int x = -1 * focusedCardIndex;
        int y = 0;
        int deltaY = 1;
        for (Card<CardModelType> card : cards.values()) {
            if (i == focusedCardIndex) {
                y = cards.size() - 1;
                deltaY = -1;
            }
            board.triggerEvent(new MoveCardEvent(card, settings.getCardZone(), new Vector3f(x, y, 0)));
            i++;
            x++;
            y += deltaY;
        }
    }

    public void selectAllCards() {
        cards.keySet().forEach(this::selectCard);
    }

    public void unselectAllCards() {
        cards.keySet().forEach(this::unselectCard);
    }

    public void selectCard(CardModelType cardModel) {
        setCardSelected(cardModel, true);
    }

    public void unselectCard(CardModelType cardModel) {
        setCardSelected(cardModel, false);
    }

    public void toggleCardSelected(CardModelType cardModel) {
        setCardSelected(cardModel, !isCardSelected(cardModel));
    }

    public void setCardSelected(CardModelType cardModel, boolean selected) {
        if (selected && (settings.getMaximumSelectedCards() != null) && (getSelectedCards().size() >= settings.getMaximumSelectedCards())) {
            return;
        }
        cardsSelected.put(cardModel, selected);
        if (settings.getCardSelectionChangeCallback() != null) {
            settings.getCardSelectionChangeCallback().accept(cardModel, selected);
        }
    }

    public List<CardModelType> getSelectedCards() {
        return cardsSelected.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public boolean isCardSelected(CardModelType cardModel) {
        if (!cardsSelected.containsKey(cardModel)) {
            System.out.println("wat");
        }
        return cardsSelected.get(cardModel);
    }

    public boolean canFocusPreviousCard() {
        return focusedCardIndex > 0;
    }

    public void focusPreviousCard() {
        if (canFocusPreviousCard()) {
            setFocusedCardIndex(focusedCardIndex - 1);
        }
    }

    public boolean canFocusNextCard() {
        return focusedCardIndex < (settings.getCards().size() - 1);
    }

    public void focusNextCard() {
        if (canFocusNextCard()) {
            setFocusedCardIndex(focusedCardIndex + 1);
        }
    }

    public void setFocusedCardIndex(int focussedCardIndex) {
        this.focusedCardIndex = focussedCardIndex;
        updateCards();
    }

    @Override
    protected void initialize(Application app) {
        getStateManager().attach(boardAppState);
    }

    @Override
    protected void cleanup(Application app) {
        getStateManager().detach(boardAppState);
    }

    // TODO: Maybe one day

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
