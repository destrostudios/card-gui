package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.events.MoveCardEvent;
import com.destrostudios.cardgui.interactivities.ClickInteractivity;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private int collectionPage;
    private HashMap<CardModelType, Card<DeckBuilderDeckCardModel<CardModelType>>> deckCards = new HashMap<>();
    private ClickInteractivity clickToAddInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<CardModelType> card = (Card<CardModelType>) source;
            changeDeckCardAmount(card.getModel(), 1);
            updateDeckZone();
        }
    };
    private ClickInteractivity clickToRemoveInteractivity = new ClickInteractivity() {

        @Override
        public void trigger(BoardObject source, BoardObject target) {
            Card<DeckBuilderDeckCardModel<CardModelType>> card = (Card<DeckBuilderDeckCardModel<CardModelType>>) source;
            changeDeckCardAmount(card.getModel().getCardModel(), -1);
            updateDeckZone();
        }
    };

    private void initBoard() {
        board = new Board();
        board.addZone(settings.getCollectionZone());
        board.addZone(settings.getDeckZone());
        board.registerVisualizer(settings.getCollectionZone(), settings.getCollectionZoneVisualizer());
        board.registerVisualizer(settings.getDeckZone(), settings.getDeckZoneVisualizer());
        board.registerVisualizer_ZonePosition(zonePosition -> zonePosition.getZone() == settings.getCollectionZone(), settings.getCollectionCardVisualizer());
        board.registerVisualizer_ZonePosition(zonePosition -> zonePosition.getZone() == settings.getDeckZone(), settings.getDeckCardVisualizer());
        boardAppState = new BoardAppState(board, rootNode, settings.getBoardSettings());
        updateCollectionZoneCards();
    }

    public int getCollectionPagesCount() {
        return (int) Math.ceil(((float) settings.getAllCardModels().size()) / getCollectionCardsPerPage());
    }

    public void goToPreviousCollectionPage() {
        collectionPage--;
        updateCollectionZoneCards();
    }

    public void goToNextColletionPage() {
        collectionPage++;
        updateCollectionZoneCards();
    }

    public void goToCollectionPage(int page) {
        collectionPage = page;
        updateCollectionZoneCards();
    }

    private void updateCollectionZoneCards() {
        for (Card card : settings.getCollectionZone().getCards()) {
            board.unregister(card);
        }
        float offsetX = ((settings.getCollectionCardsPerRow() - 1) / -2f);
        float offsetY = ((settings.getCollectionRowsPerPage() - 1) / -2f);
        int index = 0;
        int x;
        int y;
        for (CardModelType cardModel : getCollectionCardModels()) {
            x = (index % settings.getCollectionCardsPerRow());
            y = (index / settings.getCollectionCardsPerRow());
            Card card = new Card<>(cardModel);
            card.setInteractivity(clickToAddInteractivity);
            Vector3f position = Vector3f.UNIT_X.mult(offsetX + x).addLocal(Vector3f.UNIT_Z.mult(offsetY + y));
            board.triggerEvent(new MoveCardEvent(card, settings.getCollectionZone(), position));
            index++;
        }
        board.finishAllTransformations();
    }

    private List<CardModelType> getCollectionCardModels() {
        int cardsPerPage = getCollectionCardsPerPage();
        int start = (collectionPage * cardsPerPage);
        int end = ((collectionPage + 1) * cardsPerPage);
        List<CardModelType> allCardModels = settings.getAllCardModels();
        if (end > allCardModels.size()) {
            end = allCardModels.size();
        }
        return allCardModels.subList(start, end);
    }

    public int getCollectionCardsPerPage() {
        return settings.getCollectionCardsPerRow() * settings.getCollectionRowsPerPage();
    }

    public void setDeck(Map<CardModelType, Integer> deck) {
        clearDeck();
        for (Map.Entry<CardModelType, Integer> entry : deck.entrySet()) {
            changeDeckCardAmount(entry.getKey(), entry.getValue());
        }
        updateDeckZone();
    }

    public void clearDeck() {
        for (Card<DeckBuilderDeckCardModel<CardModelType>> deckCard : deckCards.values()) {
            board.unregister(deckCard);
        }
        deckCards.clear();
        updateDeckZone();
    }

    private void changeDeckCardAmount(CardModelType cardModel, int amountChange) {
        Card<DeckBuilderDeckCardModel<CardModelType>> deckCard = deckCards.computeIfAbsent(cardModel, cm -> {
            DeckBuilderDeckCardModel<CardModelType> deckCardModel = new DeckBuilderDeckCardModel<>();
            deckCardModel.setCardModel(cardModel);
            deckCardModel.setAmount(0);
            Card<DeckBuilderDeckCardModel<CardModelType>> newDeckCard = new Card<>(deckCardModel);
            newDeckCard.setInteractivity(clickToRemoveInteractivity);
            if (settings.getDeckCardEntryAnimation() != null) {
                Animation entryAnimation = settings.getDeckCardEntryAnimation().apply(newDeckCard);
                board.playAnimation(entryAnimation);
            }
            return newDeckCard;
        });
        DeckBuilderDeckCardModel<CardModelType> deckCardModel = deckCard.getModel();
        deckCardModel.setAmount(deckCardModel.getAmount() + amountChange);
        if (deckCardModel.getAmount() <= 0) {
            deckCards.remove(cardModel);
            board.unregister(deckCard);
        }
    }

    private void updateDeckZone() {
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
