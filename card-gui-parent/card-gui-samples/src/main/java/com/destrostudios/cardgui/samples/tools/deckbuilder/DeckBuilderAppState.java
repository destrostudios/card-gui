package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.events.MoveCardEvent;
import com.destrostudios.cardgui.interactivities.ClickInteractivity;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeckBuilderAppState<CardModelType extends BoardObjectModel> extends BaseAppState {

    public DeckBuilderAppState(Node rootNode, DeckBuilderSettings<CardModelType> settings) {
        this.rootNode = rootNode;
        this.settings = settings;
        initBoard();
    }
    private Node rootNode;
    private DeckBuilderSettings<CardModelType> settings;
    @Getter
    protected Board board;
    private BoardAppState boardAppState;
    private HashMap<CardModelType, Card<DeckBuilderDeckCardModel<CardModelType>>> deckCards = new HashMap<>();
    protected ClickInteractivity clickToAddInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<CardModelType> card = (Card<CardModelType>) source;
            CardModelType cardModel = card.getModel();
            if (isAllowedToAddCard(cardModel)) {
                if (checkInterceptorIfExisting(settings.getCardAddedInterceptor(), cardModel)) {
                    changeDeckCardAmount(cardModel, 1);
                    onDeckChanged();
                    callbackIfExisting(settings.getCardAddedCallback(), cardModel);
                }
            } else {
                callbackIfExisting(settings.getCardNotAddableCallback(), cardModel);
            }
        }
    };
    private ClickInteractivity clickToRemoveInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<DeckBuilderDeckCardModel<CardModelType>> card = (Card<DeckBuilderDeckCardModel<CardModelType>>) source;
            CardModelType cardModel = card.getModel().getCardModel();
            if (checkInterceptorIfExisting(settings.getCardRemovedInterceptor(), cardModel)) {
                changeDeckCardAmount(cardModel, -1);
                onDeckChanged();
                callbackIfExisting(settings.getCardRemovedCallback(), cardModel);
            }
        }
    };
    private ClickInteractivity clickToClearInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<DeckBuilderDeckCardModel<CardModelType>> card = (Card<DeckBuilderDeckCardModel<CardModelType>>) source;
            DeckBuilderDeckCardModel<CardModelType> deckBuilderCardModel = card.getModel();
            if (checkInterceptorIfExisting(settings.getCardClearedInterceptor(), deckBuilderCardModel.getCardModel())) {
                changeDeckCardAmount(deckBuilderCardModel.getCardModel(), -1 * deckBuilderCardModel.getAmount());
                onDeckChanged();
                callbackIfExisting(settings.getCardClearedCallback(), deckBuilderCardModel.getCardModel());
            }
        }
    };

    private void initBoard() {
        board = new Board(settings.getBoardSettings());
        addZone(settings.getDeckZone(), settings.getDeckZoneVisualizer(), settings.getDeckCardVisualizer());
        boardAppState = new BoardAppState(board, rootNode);
    }

    protected void addZone(CardZone zone, BoardObjectVisualizer<CardZone> zoneVisualizer, BoardObjectVisualizer cardVisualizer) {
        board.addZone(zone);
        if (zoneVisualizer != null) {
            board.registerVisualizer(zone, zoneVisualizer);
        }
        if (cardVisualizer != null) {
            board.registerVisualizer_ZonePosition(zonePosition -> zonePosition.getZone() == zone, cardVisualizer);
        }
    }

    public void setDeck(Map<CardModelType, Integer> deck) {
        clearDeck();
        for (Map.Entry<CardModelType, Integer> entry : deck.entrySet()) {
            changeDeckCardAmount(entry.getKey(), entry.getValue());
        }
        onDeckChanged();
    }

    public void clearDeck() {
        clearZone(settings.getDeckZone());
        deckCards.clear();
        onDeckChanged();
    }

    protected void clearZone(CardZone zone) {
        for (Card card : zone.getCards().toArray(Card[]::new)) {
            board.unregister(card);
        }
    }

    protected boolean isAllowedToAddCard(CardModelType cardModel) {
        Integer deckCardsMaximumTotal = settings.getDeckCardsMaximumTotal();
        if ((deckCardsMaximumTotal != null) && (getDeckSize() >= deckCardsMaximumTotal)) {
            return false;
        }
        Integer maximumOfCard = getMaximumAmountInDeck(cardModel);
        if ((maximumOfCard != null) && (getAmountInDeck(cardModel) >= maximumOfCard)) {
            return false;
        }
        if ((settings.getIsAllowedToAddCard() != null) && !settings.getIsAllowedToAddCard().test(cardModel)) {
            return false;
        }
        return true;
    }

    protected Integer getMaximumAmountInDeck(CardModelType cardModel) {
        Function<CardModelType, Integer> deckCardsMaximumUnique = settings.getDeckCardsMaximumUnique();
        if (deckCardsMaximumUnique != null) {
            return deckCardsMaximumUnique.apply(cardModel);
        }
        return null;
    }

    protected int getAmountInDeck(CardModelType cardModel) {
        Card<DeckBuilderDeckCardModel<CardModelType>> deckCard = deckCards.get(cardModel);
        return ((deckCard != null) ? deckCard.getModel().getAmount() : 0);
    }

    protected int changeDeckCardAmount(CardModelType cardModel, int amountChange) {
        Card<DeckBuilderDeckCardModel<CardModelType>> deckCard = deckCards.computeIfAbsent(cardModel, cm -> {
            DeckBuilderDeckCardModel<CardModelType> deckCardModel = new DeckBuilderDeckCardModel<>();
            deckCardModel.setCardModel(cardModel);
            deckCardModel.setAmount(0);
            boolean removable = canRemoveCardFromDeck(cardModel);
            deckCardModel.setRemovable(removable);
            Card<DeckBuilderDeckCardModel<CardModelType>> newDeckCard = new Card<>(deckCardModel);
            if (removable) {
                newDeckCard.setInteractivity(InteractivitySource.MOUSE_LEFT, clickToRemoveInteractivity);
                newDeckCard.setInteractivity(InteractivitySource.MOUSE_RIGHT, clickToClearInteractivity);
            }
            if (settings.getDeckCardEntryAnimation() != null) {
                Animation entryAnimation = settings.getDeckCardEntryAnimation().apply(newDeckCard);
                board.playAnimation(entryAnimation);
            }
            return newDeckCard;
        });
        DeckBuilderDeckCardModel<CardModelType> deckCardModel = deckCard.getModel();
        int newAmount = deckCardModel.getAmount() + amountChange;
        deckCardModel.setAmount(newAmount);
        if (deckCardModel.getAmount() <= 0) {
            deckCards.remove(cardModel);
            board.unregister(deckCard);
        }
        return newAmount;
    }

    protected boolean canRemoveCardFromDeck(CardModelType cardModel) {
        return true;
    }

    private void onDeckChanged() {
        int i = 0;
        List<CardModelType> sortedCardModels = deckCards.keySet().stream().sorted(settings.getDeckCardOrder()).collect(Collectors.toList());
        for (CardModelType cardModel : sortedCardModels) {
            Card<DeckBuilderDeckCardModel<CardModelType>> deckCard = deckCards.get(cardModel);
            board.triggerEvent(new MoveCardEvent(deckCard, settings.getDeckZone(), new Vector3f(0, 0, i)));
            if (settings.getDeckCardEntryAnimation() == null) {
                deckCard.finishTransformations();
            }
            i++;
        }
        callbackIfExisting(settings.getDeckSizeChangedCallback(), getDeckSize());
    }

    public Map<CardModelType, Integer> getDeck() {
        return deckCards.values().stream()
            .map(Card::getModel)
            .collect(Collectors.toMap(
                DeckBuilderDeckCardModel::getCardModel,
                DeckBuilderDeckCardModel::getAmount
            ));
    }

    public int getDeckSize() {
        return deckCards.values().stream().reduce(0, (sum, deckCard) -> sum + deckCard.getModel().getAmount(), Integer::sum);
    }

    private <T> void callbackIfExisting(Consumer<T> callback, T data) {
        if (callback != null) {
            callback.accept(data);
        }
    }

    private <T> boolean checkInterceptorIfExisting(Predicate<T> interceptor, T data) {
        return (interceptor == null) || interceptor.test(data);
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
