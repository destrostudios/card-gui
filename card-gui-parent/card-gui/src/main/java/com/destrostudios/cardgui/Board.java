package com.destrostudios.cardgui;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class Board<CardModelType extends BoardObjectModel> implements GameLoopListener {

    public Board(BoardObjectVisualizer<CardZone> zoneVisualizer, BoardObjectVisualizer<Card<CardModelType>> cardVisualizer) {
        this.zoneVisualizer = zoneVisualizer;
        this.cardVisualizer = cardVisualizer;
    }
    private int nextId;
    private HashMap<Integer, BoardObject> boardObjects = new HashMap<>();
    private BoardObjectVisualizer<CardZone> zoneVisualizer;
    private BoardObjectVisualizer<Card<CardModelType>> cardVisualizer;
    private AnimationQueue animationQueue = new AnimationQueue();

    public void addZone(CardZone zone) {
        zone.setBoard(this);
        register(zone);
    }

    public void register(BoardObject boardObject) {
        if (boardObject.getId() == -1) {
            boardObject.setId(nextId);
            boardObjects.put(nextId, boardObject);
            nextId++;
        }
    }

    public Collection<BoardObject> getBoardObjects() {
        return boardObjects.values();
    }

    public BoardObject getBoardObject(Integer id) {
        return boardObjects.get(id);
    }

    public void triggerEvent(GameEvent event) {
        event.trigger(this);
    }

    public void playAnimation(Animation animation) {
        animationQueue.addAnimation(animation);
    }

    @Override
    public void update(float lastTimePerFrame) {
        for (BoardObject boardObject : boardObjects.values()) {
            boardObject.update(lastTimePerFrame);
        }
        animationQueue.update(lastTimePerFrame);
    }

    public void finishAllTransformations() {
        for (BoardObject boardObject : boardObjects.values()) {
            if (boardObject instanceof TransformedBoardObject) {
                TransformedBoardObject transformedBoardObject = (TransformedBoardObject) boardObject;
                transformedBoardObject.position().finish();
                transformedBoardObject.rotation().finish();
            }
        }
    }

    public BoardObjectVisualizer getVisualizer(BoardObject boardObject) {
        if (boardObject instanceof Card) {
            return cardVisualizer;
        } else if (boardObject instanceof CardZone) {
            return zoneVisualizer;
        }
        return null;
    }

    public boolean isAnimationQueueBlocking() {
        return animationQueue.isBlocking();
    }
}
