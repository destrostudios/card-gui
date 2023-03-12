package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.events.MoveCardEvent;
import com.destrostudios.cardgui.interactivities.ClickInteractivity;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import java.util.*;
import java.util.function.Consumer;
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
    private Board board;
    private BoardAppState boardAppState;
    private HashMap<CardModelType, Card<CardModelType>> collectionCards = new HashMap<>();
    private HashMap<CardModelType, DeckBuilderCollectionCardAmount> collectionAmounts = new HashMap<>();
    private LinkedList<DeckBuilderCollectionCardAmount> displayedCollectionCardAmounts = new LinkedList<>();
    private HashMap<CardModelType, Card<DeckBuilderDeckCardModel<CardModelType>>> deckCards = new HashMap<>();
    private Predicate<CardModelType> collectionCardFilter;
    private Comparator<CardModelType> collectionCardOrder;
    private int collectionPage;
    private ClickInteractivity clickToAddInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<CardModelType> card = (Card<CardModelType>) source;
            CardModelType cardModel = card.getModel();
            if (isAllowedToAdd(cardModel)) {
                changeDeckCardAmount(cardModel, 1);
                updateDeck();
            } else {
                Consumer<CardModelType> cardNotAddableCallback = settings.getCardNotAddableCallback();
                if (cardNotAddableCallback != null) {
                    cardNotAddableCallback.accept(cardModel);
                }
            }
        }
    };
    private ClickInteractivity clickToRemoveInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<DeckBuilderDeckCardModel<CardModelType>> card = (Card<DeckBuilderDeckCardModel<CardModelType>>) source;
            changeDeckCardAmount(card.getModel().getCardModel(), -1);
            updateDeck();
        }
    };
    private ClickInteractivity clickToClearInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<DeckBuilderDeckCardModel<CardModelType>> card = (Card<DeckBuilderDeckCardModel<CardModelType>>) source;
            DeckBuilderDeckCardModel<CardModelType> cardModel = card.getModel();
            changeDeckCardAmount(cardModel.getCardModel(), -1 * cardModel.getAmount());
            updateDeck();
        }
    };

    private void initBoard() {
        board = new Board(settings.getBoardSettings());
        board.addZone(settings.getCollectionZone());
        board.addZone(settings.getDeckZone());
        board.registerVisualizer(settings.getCollectionZone(), settings.getCollectionZoneVisualizer());
        board.registerVisualizer(settings.getDeckZone(), settings.getDeckZoneVisualizer());
        board.registerVisualizer_ZonePosition(zonePosition -> zonePosition.getZone() == settings.getCollectionZone(), settings.getCollectionCardVisualizer());
        board.registerVisualizer_ZonePosition(zonePosition -> zonePosition.getZone() == settings.getDeckZone(), settings.getDeckCardVisualizer());
        if (settings.getCollectionCardAmountVisualizer() != null) {
            board.registerVisualizer_Class(DeckBuilderCollectionCardAmount.class, settings.getCollectionCardAmountVisualizer());
        }
        boardAppState = new BoardAppState(board, rootNode);
        initCollection();
        updateCollection();
    }

    private void initCollection() {
        for (Map.Entry<CardModelType, Integer> entry : settings.getCollectionCards().entrySet()) {
            CardModelType cardModel = entry.getKey();
            int amount = entry.getValue();
            // Card
            Card<CardModelType> card = new Card<>(cardModel);
            card.setInteractivity(InteractivitySource.MOUSE_LEFT, clickToAddInteractivity);
            collectionCards.put(cardModel, card);
            // Amount
            DeckBuilderCollectionCardAmount amountBoardObject = new DeckBuilderCollectionCardAmount();
            amountBoardObject.getModel().setAmountCollection(amount);
            collectionAmounts.put(cardModel, amountBoardObject);
        }
    }

    public void setCollectionCardFilter(Predicate<CardModelType> collectionCardFilter) {
        this.collectionCardFilter = collectionCardFilter;
        goToCollectionPage(0);
    }

    public void setCollectionCardOrder(Comparator<CardModelType> collectionCardOrder) {
        this.collectionCardOrder = collectionCardOrder;
        goToCollectionPage(0);
    }

    public int getCollectionPagesCount() {
        return (int) Math.ceil(((float) getFilteredCollectionCardModels().size()) / getCollectionCardsPerPage());
    }

    public void goToPreviousCollectionPage() {
        collectionPage--;
        updateCollection();
    }

    public void goToNextCollectionPage() {
        collectionPage++;
        updateCollection();
    }

    public void goToCollectionPage(int page) {
        collectionPage = page;
        updateCollection();
    }

    private void updateCollection() {
        // Remove old
        for (Card card : settings.getCollectionZone().getCards()) {
            board.unregister(card);
        }
        for (DeckBuilderCollectionCardAmount amount : displayedCollectionCardAmounts) {
            board.unregister(amount);
        }
        displayedCollectionCardAmounts.clear();

        // Add new
        float offsetX = ((settings.getCollectionCardsPerRow() - 1) / -2f);
        float offsetY = ((settings.getCollectionRowsPerPage() - 1) / -2f);
        int index = 0;
        int x;
        int y;
        for (CardModelType cardModel : getDisplayedCollectionCardModels()) {
            // Card
            Card<CardModelType> card = collectionCards.get(cardModel);
            x = (index % settings.getCollectionCardsPerRow());
            y = (index / settings.getCollectionCardsPerRow());
            Vector3f position = Vector3f.UNIT_X.mult(offsetX + x).addLocal(Vector3f.UNIT_Z.mult(offsetY + y));
            board.triggerEvent(new MoveCardEvent(card, settings.getCollectionZone(), position));

            // Amount
            if (settings.getCollectionCardAmountVisualizer() != null) {
                DeckBuilderCollectionCardAmount amount = collectionAmounts.get(cardModel);
                amount.getModel().setX(x);
                amount.getModel().setY(y);
                board.register(amount);
                displayedCollectionCardAmounts.add(amount);
            }

            index++;
        }
    }

    private List<CardModelType> getDisplayedCollectionCardModels() {
        List<CardModelType> cardModels = getFilteredCollectionCardModels();
        if (collectionCardOrder != null) {
            cardModels = cardModels.stream().sorted(collectionCardOrder).collect(Collectors.toList());;
        }
        int cardsPerPage = getCollectionCardsPerPage();
        int start = (collectionPage * cardsPerPage);
        int end = ((collectionPage + 1) * cardsPerPage);
        if (end > cardModels.size()) {
            end = cardModels.size();
        }
        return cardModels.subList(start, end);
    }

    private List<CardModelType> getFilteredCollectionCardModels() {
        List<CardModelType> cardModels = new LinkedList<>(settings.getCollectionCards().keySet());
        if (collectionCardFilter != null) {
            cardModels = cardModels.stream().filter(cardModel -> collectionCardFilter.test(cardModel)).collect(Collectors.toList());;
        }
        return cardModels;
    }

    public int getCollectionCardsPerPage() {
        return settings.getCollectionCardsPerRow() * settings.getCollectionRowsPerPage();
    }

    public void setDeck(Map<CardModelType, Integer> deck) {
        clearDeck();
        for (Map.Entry<CardModelType, Integer> entry : deck.entrySet()) {
            changeDeckCardAmount(entry.getKey(), entry.getValue());
        }
        updateDeck();
    }

    public void clearDeck() {
        for (Card<DeckBuilderDeckCardModel<CardModelType>> deckCard : deckCards.values()) {
            board.unregister(deckCard);
        }
        deckCards.clear();
        updateDeck();
    }

    private boolean isAllowedToAdd(CardModelType cardModel) {
        Integer deckCardsMaximumTotal = settings.getDeckCardsMaximumTotal();
        if ((deckCardsMaximumTotal != null) && (getDeckSize() >= deckCardsMaximumTotal)) {
            return false;
        }
        Card<DeckBuilderDeckCardModel<CardModelType>> deckCard = deckCards.get(cardModel);
        int amountInCollection = settings.getCollectionCards().get(cardModel);
        int amountInDeck = ((deckCard != null) ? deckCard.getModel().getAmount() : 0);
        if (amountInDeck >= amountInCollection) {
            return false;
        }
        Map<CardModelType, Integer> deckCardsMaximumUnique = settings.getDeckCardsMaximumUnique();
        if (deckCardsMaximumUnique != null) {
            Integer maximum = deckCardsMaximumUnique.get(cardModel);
            if ((maximum != null) && (amountInDeck >= maximum)) {
                return false;
            }
        }
        return true;
    }

    private void changeDeckCardAmount(CardModelType cardModel, int amountChange) {
        Card<DeckBuilderDeckCardModel<CardModelType>> deckCard = deckCards.computeIfAbsent(cardModel, cm -> {
            DeckBuilderDeckCardModel<CardModelType> deckCardModel = new DeckBuilderDeckCardModel<>();
            deckCardModel.setCardModel(cardModel);
            deckCardModel.setAmount(0);
            Card<DeckBuilderDeckCardModel<CardModelType>> newDeckCard = new Card<>(deckCardModel);
            newDeckCard.setInteractivity(InteractivitySource.MOUSE_LEFT, clickToRemoveInteractivity);
            newDeckCard.setInteractivity(InteractivitySource.MOUSE_RIGHT, clickToClearInteractivity);
            if (settings.getDeckCardEntryAnimation() != null) {
                Animation entryAnimation = settings.getDeckCardEntryAnimation().apply(newDeckCard);
                board.playAnimation(entryAnimation);
            }
            return newDeckCard;
        });
        DeckBuilderDeckCardModel<CardModelType> deckCardModel = deckCard.getModel();
        int newAmount = deckCardModel.getAmount() + amountChange;
        deckCardModel.setAmount(newAmount);
        collectionAmounts.get(cardModel).getModel().setAmountDeck(newAmount);
        if (deckCardModel.getAmount() <= 0) {
            deckCards.remove(cardModel);
            board.unregister(deckCard);
        }
    }

    private void updateDeck() {
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

    public Predicate<CardModelType> getCollectionCardFilter() {
        return collectionCardFilter;
    }

    public Comparator<CardModelType> getCollectionCardOrder() {
        return collectionCardOrder;
    }

    public int getCollectionPage() {
        return collectionPage;
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
