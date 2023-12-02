package com.destrostudios.cardgui.samples.tools.deckbuilder.collection;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.events.MoveCardEvent;
import com.destrostudios.cardgui.samples.tools.deckbuilder.DeckBuilderAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import lombok.Getter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionDeckBuilderAppState<CardModelType extends BoardObjectModel> extends DeckBuilderAppState<CardModelType> {

    public CollectionDeckBuilderAppState(Node rootNode, CollectionDeckBuilderSettings<CardModelType> settings) {
        super(rootNode, settings.getDeckBuilderSettings());
        this.settings = settings;
    }
    private CollectionDeckBuilderSettings<CardModelType> settings;
    private HashMap<CardModelType, Card<CardModelType>> collectionCards = new HashMap<>();
    private HashMap<CardModelType, CollectionDeckBuilderCardAmount> collectionAmounts = new HashMap<>();
    private LinkedList<CollectionDeckBuilderCardAmount> displayedCollectionCardAmounts = new LinkedList<>();
    @Getter
    private Predicate<CardModelType> collectionCardFilter;
    @Getter
    private Comparator<CardModelType> collectionCardOrder;
    @Getter
    private int collectionPage;

    @Override
    protected void initBoard() {
        super.initBoard();
        addZone(settings.getCollectionZone(), settings.getCollectionZoneVisualizer(), settings.getCollectionCardVisualizer());
        if (settings.getCollectionCardAmountVisualizer() != null) {
            board.registerVisualizer_Class(CollectionDeckBuilderCardAmount.class, settings.getCollectionCardAmountVisualizer());
        }
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
            CollectionDeckBuilderCardAmount amountBoardObject = new CollectionDeckBuilderCardAmount();
            amountBoardObject.getModel().setMaximumAmountDeck(getMaximumAmountInDeck(cardModel));
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
        for (Card card : settings.getCollectionZone().getCards().toArray(Card[]::new)) {
            board.unregister(card);
        }
        for (CollectionDeckBuilderCardAmount amount : displayedCollectionCardAmounts) {
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
                CollectionDeckBuilderCardAmount amount = collectionAmounts.get(cardModel);
                amount.getModel().setX(x);
                amount.getModel().setY(y);
                board.register(amount);
                displayedCollectionCardAmounts.add(amount);
            }

            index++;
        }
        board.finishAllTransformations();
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

    @Override
    protected boolean isAllowedToAdd(CardModelType cardModel) {
        if (!super.isAllowedToAdd(cardModel)) {
            return false;
        }
        int amountInDeck = getAmountInDeck(cardModel);
        int amountInCollection = settings.getCollectionCards().get(cardModel);
        return (amountInDeck < amountInCollection);
    }

    @Override
    protected int changeDeckCardAmount(CardModelType cardModel, int amountChange) {
        int newAmount = super.changeDeckCardAmount(cardModel, amountChange);
        collectionAmounts.get(cardModel).getModel().setAmountDeck(newAmount);
        return newAmount;
    }
}
