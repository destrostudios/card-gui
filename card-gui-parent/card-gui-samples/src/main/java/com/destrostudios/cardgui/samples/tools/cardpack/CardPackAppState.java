package com.destrostudios.cardgui.samples.tools.cardpack;

import com.destrostudios.cardgui.*;
import com.destrostudios.cardgui.events.MoveCardEvent;
import com.destrostudios.cardgui.inspectors.ScaleInspector;
import com.destrostudios.cardgui.interactivities.ClickInteractivity;
import com.destrostudios.cardgui.transformations.LinearTargetRotationTransformation;
import com.destrostudios.cardgui.transformations.LinearTargetVectorTransformation3f;
import com.destrostudios.cardgui.transformations.relative.ConditionalRelativeTransformation;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedRotationTransformationSpeed;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed3f;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import lombok.Getter;

import java.util.HashMap;

public class CardPackAppState<CardModelType extends BoardObjectModel> extends BaseAppState {

    public CardPackAppState(Node rootNode, CardPackSettings<CardModelType> settings) {
        this.rootNode = rootNode;
        this.settings = settings;
        initBoard();
    }
    private Node rootNode;
    private CardPackSettings<CardModelType> settings;
    private Board board;
    private BoardAppState boardAppState;
    private HashMap<CardModelType, Card<CardModelType>> cards;
    @Getter
    private boolean packOpen;
    private HashMap<CardModelType, Boolean> cardsRevealed;

    private void initBoard() {
        board = new Board(buildBoardSettings());
        board.addZone(settings.getCardZone());
        if (settings.getCardZoneVisualizer() != null) {
            board.registerVisualizer(settings.getCardZone(), settings.getCardZoneVisualizer());
        }
        board.registerVisualizer_ZonePosition(zonePosition -> zonePosition.getZone() == settings.getCardZone(), settings.getCardVisualizer());
        boardAppState = new BoardAppState(board, rootNode);
        initCards();
    }

    private BoardSettings buildBoardSettings() {
        BoardSettings.BoardSettingsBuilder builder = BoardSettings.builder()
                .cardInZonePositionTransformationSpeed(() -> new TimeBasedVectorTransformationSpeed3f(settings.getPackOpenDuration()))
                .hoverInspectionDelay(0f)
                .inspector(new ScaleInspector(new Vector3f(1.25f, 1.25f, 1.25f), 0.2f));
        settings.getBoardSettings().accept(builder);
        return builder.build();
    }

    private void initCards() {
        cards = new HashMap<>();
        cardsRevealed = new HashMap<>();
        float openAndCloseScaleDuration = (settings.getPackOpenDuration() * settings.getPackOpenDurationFactorScale());
        for (CardModelType cardModel : settings.getCards()) {
            Card<CardModelType> card = new Card<>(cardModel);
            card.scale().addRelativeTransformation(new ConditionalRelativeTransformation<>(
                new LinearTargetVectorTransformation3f(new Vector3f(0, 0, 0), new TimeBasedVectorTransformationSpeed3f(openAndCloseScaleDuration)),
                new LinearTargetVectorTransformation3f(new Vector3f(1, 1, 1), new TimeBasedVectorTransformationSpeed3f(openAndCloseScaleDuration)),
                () -> !packOpen
            ));
            card.rotation().addRelativeTransformation(new ConditionalRelativeTransformation<>(
                new LinearTargetRotationTransformation(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Z), new TimeBasedRotationTransformationSpeed(settings.getCardRevealDuration())),
                new LinearTargetRotationTransformation(new Quaternion(), new TimeBasedRotationTransformationSpeed(settings.getCardRevealDuration())),
                () -> !isCardRevealed(cardModel)
            ));
            if (settings.isCardRevealOnClick()) {
                card.setInteractivity(InteractivitySource.MOUSE_LEFT, new ClickInteractivity() {

                    @Override
                    public void trigger(BoardObject source, BoardObject target) {
                        revealCard(cardModel);
                    }
                });
            }
            board.triggerEvent(new MoveCardEvent(card, settings.getCardZone(), new Vector3f()));
            cards.put(cardModel, card);
            cardsRevealed.put(cardModel, false);
        }
    }

    public void openPack() {
        setPackOpen(true);
    }

    public void closePack() {
        setPackOpen(false);
    }

    public void setPackOpen(boolean packOpen) {
        this.packOpen = packOpen;
        int i = 0;
        for (Card<CardModelType> card : cards.values()) {
            Vector3f position;
            if (packOpen) {
                position = settings.getCardPackPattern().getCardPosition(cards.size(), i);
            } else {
                position = new Vector3f();
            }
            board.triggerEvent(new MoveCardEvent(card, settings.getCardZone(), position));
            i++;
        }
    }

    public void revealAllCards() {
        settings.getCards().forEach(this::revealCard);
    }

    public void hideAllCards() {
        settings.getCards().forEach(this::hideCard);
    }

    public void revealCard(CardModelType cardModel) {
        setCardRevealed(cardModel, true);
    }

    public void hideCard(CardModelType cardModel) {
        setCardRevealed(cardModel, false);
    }

    public void setCardRevealed(CardModelType cardModel, boolean revealed) {
        cardsRevealed.put(cardModel, revealed);
    }

    public boolean areAllCardsRevealed() {
        return cardsRevealed.values().stream().allMatch(revealed -> revealed);
    }

    public boolean isCardRevealed(CardModelType cardModel) {
        return cardsRevealed.get(cardModel);
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
